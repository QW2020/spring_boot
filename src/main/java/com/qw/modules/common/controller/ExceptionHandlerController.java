package com.qw.modules.common.controller;

import com.qw.modules.common.vo.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: ExceptionHandlerController <br/>
 * Description: <br/>
 * date: 2020/8/26 16:34<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 * 针对不同的Exception 跳转到不同的页面，如：UnauthorizedException，跳到403页面
 * @ControllerAdvice：全局，处理所有控制器中的异常
 * @Exception：局部，只针对某个异常
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController {

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result<String> handler403(){
        return new Result<>(
                Result.ResultStatus.FAILD.status,
                "","/common/403");
    }
}
