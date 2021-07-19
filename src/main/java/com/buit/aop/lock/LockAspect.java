package com.buit.aop.lock;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.buit.commons.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * @Description
 * @Author yueyu
 * @Date 2020/7/10 13:20
 */
@Aspect
@Component
public class LockAspect {

    @Autowired
    RedissonClient redissonClient;

    ThreadLocal<Locked> firstLocked = new ThreadLocal<>();
    ThreadLocal<List<RLock>> waitUnlockList = new ThreadLocal<>();
    ThreadLocal<List<RLock>> allLockList = new ThreadLocal<>();

    @Around("@annotation(com.buit.aop.lock.Locked)&&@annotation(lock)")
    public Object lockAround(ProceedingJoinPoint joinPoint,Locked lock) throws Throwable {
        Map<String, Object> args = getParams(joinPoint);
        List<String> keyList = Arrays.stream(lock.value())
                .map(k->transferKey(k,args))
                .flatMap(l->l.stream())
                .collect(Collectors.toList());
        RLock[] rLocks = keyList.stream()
                .map(k->redissonClient.getFairLock(k))
                .toArray(RLock[]::new);
        RLock rLock = redissonClient.getMultiLock(rLocks);
        boolean isFirst = false;
        boolean isException = false;
        try {
            if (tryLock(rLock, lock)) {
                if(waitUnlockList.get()==null||firstLocked.get()==null||allLockList.get()==null){
                    firstLocked.set(lock);
                    allLockList.set(new ArrayList<>());
                    waitUnlockList.set(new ArrayList<>());
                    isFirst=true;
                }
                allLockList.get().add(rLock);
                if(lock.expire()>0) {
                    waitUnlockList.get().add(rLock);
                }
                return joinPoint.proceed(joinPoint.getArgs());
            }else{
                FailedHandler failedHandler = lock.failed().getDeclaredConstructor().newInstance();
                return failedHandler.handler(keyList,args);
            }
        }catch (Throwable throwable) {
            isException=true;
            throw throwable;
        }finally {
            if(isFirst){
                if(isException){
                    allLockList.get().forEach(r->r.unlock());
                }else{
                    waitUnlockList.get().forEach(r->r.unlock());
                }
                firstLocked.remove();
                allLockList.remove();
                waitUnlockList.remove();
            }
        }
    }

    private boolean tryLock(RLock rLock,Locked lock) throws InterruptedException {
        Locked firstLock = firstLocked.get();
        long block = lock.block();
        long expire = Math.abs(lock.expire());
        if(block==-1){
            block=Long.MAX_VALUE;
        }
        if(firstLock!=null){
            if(firstLock.block()!=-1){
                block=1;
            }
            expire=Math.max(expire,Math.abs(firstLock.expire()));
        }
        return rLock.tryLock(block,expire,TimeUnit.SECONDS);
    }

    private Map<String,Object> getParams(ProceedingJoinPoint joinPoint){
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = method.getParameterNames();
        Map<String,Object> param = new HashMap<>();
        for (int i=0;i<joinPoint.getArgs().length;i++){
            param.put(paramNames[i],joinPoint.getArgs()[i]);
        }
        return param;
    }

//    private List<String> transferKey(String key,Map<String,Object> args){
//        Pattern pattern = Pattern.compile("#\\[\\S+?]");
//        Matcher matcher = pattern.matcher(key);
//        List<String> keys = Arrays.asList(key);
//        while(matcher.find()){
//            String var = matcher.group();
//            String kPath = var.replaceAll("^#|\\[|]$","");
//            List<String> values = getListValue(kPath,args);
//            keys = keys.stream().flatMap(k->
//                            values.stream().map(v->k.replace(var,v))
//                    ).collect(Collectors.toList());
//        }
//        return keys;
//
//    }

    /**
     * 转换key中变量为参数值
     * 例：key=lock_#[a.b]_#[a.*c.d]_#[a.*c.e]_#[a.*c.*g]_#[a.*f]
     *    args={
     *     "b":"b",
     *     "c":[
     *         {
     *             "d":"d1",
     *             "e":"c1",
     *             "g":["g1","g2"]
     *         },
     *         {
     *             "d":"d2",
     *             "e":"c2",
     *             "g":["g3","g4"]
     *         }
     *     ],
     *     "f":["f1","f2"]
     * }
     * return [
     *     "lock_b_d1_c1_g1_f1",
     *     "lock_b_d1_c1_g1_f2",
     *     "lock_b_d1_c1_g2_f1",
     *     "lock_b_d1_c1_g2_f2",
     *     "lock_b_d2_c2_g3_f1",
     *     "lock_b_d2_c2_g3_f2",
     *     "lock_b_d2_c2_g4_f1",
     *     "lock_b_d2_c2_g4_f2"
     * ]
     * */
    private List<String> transferKey(String key,Map<String,Object> args){
        Pattern pattern = Pattern.compile("#\\[\\S+?]");
        Matcher matcher = pattern.matcher(key);
        List<String> keys = new ArrayList<>();
        while(matcher.find()){
            String var = matcher.group();
            String kPath = var.replaceAll("^#|\\[|]$","");
            keys.add(kPath);
        }
        Map<String,Object> group = recursionGroup(keys);
        keys = replaceVarToValue(null,key,group,args);
        return keys;

    }

    private List<String> replaceVarToValue(String parentPath,String value,Map<String,Object> keys,Map<String,Object> args){
        List<String> results = Arrays.asList(value);
        for (String k : keys.keySet()) {
            Object v = keys.get(k);
            boolean isStar = k.startsWith("*");
            String var = StringUtils.isNotBlank(parentPath)?String.format("%s.%s", parentPath, k):k;
            if(isStar){
                List<Object> list = (List<Object>) args.get(k.substring(1));
                if(list==null){
                    throw BaseException.create("ERROR_LOCK_0002",new String[]{var});
                }
                if(v==null) {
                    results = results.stream().flatMap(rs->
                            list.stream().map(s -> rs.replaceAll(String.format("#\\[%s]",var), s.toString()))
                            .collect(Collectors.toList()).stream()
                    ).collect(Collectors.toList());
                }else{
                    results = results.stream().flatMap(rs->
                            list.stream().flatMap(o->{
                                if(o instanceof Map){
                                    return replaceVarToValue(var,rs,(Map<String,Object>)v,(Map<String,Object>)o).stream();
                                }else{
                                    return replaceVarToValue(var,rs,(Map<String,Object>)v,BeanUtil.beanToMap(o)).stream();
                                }
                            })
                    ).collect(Collectors.toList());
                }
            }else {
                Object obj = args.get(k);
                if(obj==null){
                    throw BaseException.create("ERROR_LOCK_0002",new String[]{var});
                }
                if(v==null) {
                    results = results.stream().map(rs->rs.replaceAll(String.format("#\\[%s]",var), obj.toString())).collect(Collectors.toList());
                }else{
                    results = results.stream().flatMap(rs->{
                                if(obj instanceof Map){
                                    return replaceVarToValue(var,rs,(Map<String,Object>)v,(Map<String,Object>)obj).stream();
                                }else{
                                    return replaceVarToValue(var,rs,(Map<String,Object>)v,BeanUtil.beanToMap(obj)).stream();
                                }
                            }
                        ).collect(Collectors.toList());

                }
            }
        }
        return results;
    }

    /**
     * 将变量按照相同的引用分组
     * 例：
     * parameter -> ["a.b.c","a.b.d","a.e"]
     * return -> {
     *     "a":{
     *         "b":{
     *             "c":null,
     *             "d":null
     *         },
     *         "e":null
     *     }
     * }
     * */
    private Map<String,Object> recursionGroup(List<String> list){
        Map<String,List<String>> stringListMap = list.stream().collect(Collectors.groupingBy(
                s->s.contains(".")?s.substring(0,s.indexOf(".")):s,
                Collector.of(
                        ArrayList::new,
                        (array,s)->{
                                if(s.contains(".")){
                                    array.add(s.substring(s.indexOf(".") + 1));
                                }
                            },
                        (left,r)->{
                            left.addAll(r);
                            return left;
                        }
                ))
        );
        Map<String,Object> result = new HashMap<>();
        for (String s : stringListMap.keySet()) {
            if(stringListMap.get(s).isEmpty()){
                result.put(s,null);
            }else {
                result.put(s, recursionGroup(stringListMap.get(s)));
            }
        }
        return result;
    }


    private List<String> getListValue(String kPath,Map<String,Object> args){
        if(kPath.contains("*")){
            if(kPath.contains(".")){
                int index = kPath.indexOf(".");
                String kPre = kPath.substring(0,index);
                String kSuf = kPath.substring(index+1);

                if(kPath.startsWith("*")){
                    kPre = kPre.substring(1);
                    List<Object> list = (List) args.get(kPre);
                    return list.stream().flatMap(o->{
                        if(o instanceof Map){
                            return getListValue(kSuf,(Map<String,Object>)o).stream();
                        }else{
                            return getListValue(kSuf,BeanUtil.beanToMap(o)).stream();
                        }
                    }).collect(Collectors.toList());
                }else{
                    Object obj = args.get(kPre);
                    if(obj instanceof Map){
                        return getListValue(kSuf,(Map<String,Object>)obj);
                    }else{
                        return getListValue(kSuf,BeanUtil.beanToMap(obj));
                    }
                }
            }else{
                kPath = kPath.substring(1);
                List<Object> list = (List) args.get(kPath);
                return list.stream().map(o->o.toString()).collect(Collectors.toList());
            }

        }else{
            return Arrays.asList(getValue(kPath,args));
        }

    }

    private String getValue(String kPath,Map<String,Object> args){
        if(kPath.contains(".")){
            int index = kPath.indexOf(".");
            String k = kPath.substring(0,index);
            Object obj = args.get(k);
            if(obj instanceof Map) {
                return getValue(kPath.substring(index + 1), (Map<String, Object>) obj);
            }else{
                return getValue(kPath.substring(index + 1), BeanUtil.beanToMap(obj));
            }
        }else{
            if(args.get(kPath)==null){
                throw BaseException.create("ERROR_LOCK_0002",new String[]{kPath});
            }
            return args.get(kPath).toString();
        }
    }

}
