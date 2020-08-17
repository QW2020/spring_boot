package com.qw.aspect;

import com.qw.modules.test.controller.TestController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * ClassName: ControllerAspect <br/>
 * Description: <br/>
 * date: 2020/8/17 16:12<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 *
 * aop 控制器切片程序
 * 比较局限，需要指定包
 */
@Component
@Aspect
public class ControllerAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    /**
     * 关联在方法上的切点
     * 第一个*代表返回类型不限
     * 第二个*代表module下所有子包
     * 第三个*代表所有类
     * 第四个*代表所有方法
     * (..) 代表参数不限
     * Order 代表优先级，数字越小优先级越高
     */
    //设置切片方法，绑定切入位置
    //调用任意controller中任意方法时均会调用此方法
    @Pointcut("execution(public * com.qw.modules.*.controller.*.*(..))")
    @Order(1)
    public void controllerPointCut(){}

    //前置通知方法
    @Before(value = "com.qw.aspect.ControllerAspect.controllerPointCut()")
    public void beforeController(JoinPoint joinPoint){
        LOGGER.debug("==== This is before controller ====");
        //获取到request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOGGER.debug("请求来源：" + request.getRemoteAddr());
        LOGGER.debug("请求URL：" + request.getRequestURL().toString());
        LOGGER.debug("请求方式：" + request.getMethod());
        LOGGER.debug("响应方法：" +
                joinPoint.getSignature().getDeclaringTypeName() + "." +
                joinPoint.getSignature().getName());
        LOGGER.debug("请求参数：" + Arrays.toString(joinPoint.getArgs()));
    }

    //环绕通知方法  包括前置、后置
    @Around(value = "com.qw.aspect.ControllerAspect.controllerPointCut()")
    public Object aroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LOGGER.debug("==== This is around controller ==== ");
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    //后置通知方法
    @After(value = "com.qw.aspect.ControllerAspect.controllerPointCut()")
    public void afterController(){
        LOGGER.debug("==== This is after controller ====");
    }
}
