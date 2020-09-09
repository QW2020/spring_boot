package com.qw.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: RequestViewInterceptor <br/>
 * Description: <br/>
 * date: 2020/8/17 12:24<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 *
 * 创建拦截器
 */
@Component
public class RequestViewInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestViewInterceptor.class);

    //拦截点之前切入
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("==== Pre interceptor ====");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //拦截点一起
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.debug("==== Post interceptor ====");

        if(modelAndView == null || modelAndView.getViewName().startsWith("redirect")){
            return;
        }

        // 获取path路径
        String path = request.getServletPath();
        //查看ModelMap里面是否有template属性
        String template = (String) modelAndView.getModelMap().get("template");
        //做判断 template为空时设置为path路径  toLowerCase（）全部转为小写
        if (StringUtils.isBlank(template)){
            //如果path路径以 "/" 开始，去掉首位 "/"
            if (path.startsWith("/")){
                path = path.substring(1);
            }
            modelAndView.getModelMap().addAttribute("template",path.toLowerCase());
    }
        HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //拦截点之后切入
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.debug("==== After interceptor ====");
        HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
