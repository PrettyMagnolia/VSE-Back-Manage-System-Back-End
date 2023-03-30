package com.backend.tjtablepartyspringboot.interceptor;
import com.backend.tjtablepartyspringboot.interceptor.util.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("333");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //从 http 请求头中取出 token
        String token = request.getHeader("token");
        System.out.println("此处测试是否拿到了token：" + token);

        if (token == null) {
            throw new RuntimeException("无 token ，请重新登陆");
        }

        //验证 token
        JwtUtil.checkSign(token);

        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId
        String userId = JwtUtil.getUserId(token);
        System.out.println("id : " + userId);

        return true;
    }
}
