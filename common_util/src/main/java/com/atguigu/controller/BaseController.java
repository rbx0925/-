package com.atguigu.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author chenxin
 * @date 2022/11/26
 * @Version 1.0
 */
public class BaseController {
    /**
     * 封装页面提交的分页参数及搜索条件
     * @param request
     * @return Map集合
     * name=username&password=root&hobby=java&hobby=mysql
     * Map:
     *  name=username
     *  password=root
     *  hobby=[java,mysql]   这种dao的映射文件就需要用foreach遍历
     */
    public Map<String, Object> getFilters(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while(paramNames != null && paramNames.hasMoreElements()) {//迭代 (request位置讲过)
            String paramName = (String)paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(paramName, values);
                } else {
                    filters.put(paramName, values[0]);
                }
            }
        }
        //如果请求参数内没有pageNum和pageSize的话，初始值设置为1和3
        if(!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if(!filters.containsKey("pageSize")) {
            filters.put("pageSize", 3);
        }

        return filters;
    }
}
