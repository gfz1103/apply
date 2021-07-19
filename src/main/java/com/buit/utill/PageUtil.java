package com.buit.utill;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName PageUtil
 * @Description 类描述
 * @Author 老花生
 * @Date 2020/6/11 11:03
 */
public class PageUtil {
    /**
     * 开始分页
     * @param list
     * @param pageNum 页码
     * @param pageSize 每页多少条数据
     * @return
     */
    static <T> List startPage(List<T> list, Integer pageNum,
                                 Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }

        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }

        List pageList = list.subList(fromIndex, toIndex);

        return pageList;
    }

    /**
     * 给list分页转化成pageinfo
     * @param list
     * @param pageNum
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> PageInfo getPage(List<T> list, Integer pageNum,
                                   Integer pageSize){
    	if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        List<T> startList = startPage(list, pageNum, pageSize);
        PageInfo startPage = new PageInfo();
        startPage.setList(list);
        startPage.setPageNum(pageNum);
        startPage.setPageSize(pageSize);
        startPage.setTotal(list.size());
        startPage.setSize(startList.size());
        return startPage;
    }

    /**
     * pagehelper手动分页
     * @param currentPage 当前页
     * @param pageSize
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        int total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * currentPage;
            if (toIndex > total) {
                toIndex = total;
            }
            list = list.subList(pageSize * (currentPage - 1), toIndex);
        }
        Page<T> page = new Page<>(currentPage, pageSize);
        page.addAll(list);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);

        PageInfo<T> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }



}
