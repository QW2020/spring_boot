package com.qw.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

/**
 * ClassName: RequestParamsFilter <br/>
 * Description: <br/>
 * date: 2020/8/17 9:23<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
//创建过滤器
//urlPatterns = "/**" 所有请求参数
@WebFilter(filterName = "requestParamsFilter",urlPatterns = "/**")
public class RequestParamsFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestParamsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("====== Init Request Param Filter ======");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("====== Do Request Param Filter ======");
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
//        Map<String,String[]> map = httpServletRequest.getParameterMap();
//        map.put("paramKey",new String[]{"***"});
        //加上大括号{}重写 getParameter()方法 然后完成替换
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpServletRequest){
            @Override
            public String getParameter(String name) {
                String value = httpServletRequest.getParameter(name);
                //如果value不为空 即 fuck 替换为 ***
                if(StringUtils.isNotBlank(value)){
                    return value.replaceAll("fuck","***");
                }
                return super.getParameter(name);
            }

            //重写 getParameterValues()方法 然后完成替换
            @Override
            public String[] getParameterValues(String name) {
                String[] values = httpServletRequest.getParameterValues(name);
                if (values!=null && values.length>0){
                    for (int i1 = 0; i1 < values.length; i1++) {
                        values[i1] = values[i1].replaceAll("fuck","***");
                    }
                    return values;
                }
                return super.getParameterValues(name);
            }
        };

        //将wrapper,servletResponse交出去
        filterChain.doFilter(wrapper,servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.debug("====== Destroy Request Param Filter ======");
    }
}
