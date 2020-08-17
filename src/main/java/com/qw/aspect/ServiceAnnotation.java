package com.qw.aspect;

import java.lang.annotation.*;

/**
 * ClassName: ServiceAnnotation <br/>
 * Description: <br/>
 * date: 2020/8/17 17:07<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 * 自定义注解   切面编程
 * 范围广 可以随意写在方法上面
 */
//设定注解写到方法上面
@Target(ElementType.METHOD)
//文档型
@Documented
//设置在运行时起到作用
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceAnnotation {
    //设置属性值
    String value() default "aaa";
}
