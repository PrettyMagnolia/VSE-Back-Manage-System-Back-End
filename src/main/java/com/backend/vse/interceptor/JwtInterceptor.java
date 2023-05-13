package com.backend.vse.interceptor;
import com.alibaba.fastjson.JSONObject;
import com.backend.vse.common.Result;
import com.backend.vse.interceptor.util.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    // 定义一个线程域，存放登录的用户index
    private static final ThreadLocal<Long> userIndex = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //从 http 请求头中取出 token
        String BearerToken = request.getHeader("Authorization");

        // 判断请求是否带有token
        if (BearerToken == null) {
            Result<Object> result = Result.fail(400,"无token，请重新登录");
            String jsonObjectStr = JSONObject.toJSONString(result);
            returnJson(response,jsonObjectStr);
            return false;
        }
        String token = BearerToken.replace("Bearer ","");
        System.out.println("此处测试是否拿到了token：" + token);

        //验证 token
        if (!JwtUtil.checkSign(token)) {
//            throw new RuntimeException("无 token ，请重新登陆");
            Result<Object> result = Result.fail(400,"无token，请重新登录");
            String jsonObjectStr = JSONObject.toJSONString(result);
            returnJson(response,jsonObjectStr);
            return false;
        }


        JwtUtil.checkSign(token);

        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId(index)
        String userId = JwtUtil.getUserId(token);
        System.out.println("id : " + userId);

        assert userId != null;
        userIndex.set(Long.parseLong(userId));

        return true;
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);

        } catch (IOException e) {
            ;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //程序运行结束之后，删除线程
        userIndex.remove();
    }

    // 对外提供了静态的方法：getLoginUser()来获取User信息
    public static Long  getLoginUser() {
        return userIndex.get();
    }

}
