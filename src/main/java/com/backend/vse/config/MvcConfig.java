package com.backend.vse.config;
import com.backend.vse.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 添加jwt拦截器，并指定拦截路径
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截除了登录login的所有资源
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/account")
                .addPathPatterns("/menu/list")
                .addPathPatterns("/review/score")
                .addPathPatterns("/review/change-userinfo")
                .addPathPatterns("/report/submit")
                .addPathPatterns("/user/change-avatar")
                .addPathPatterns("/change-password");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**") //过滤策略
                .addResourceLocations("classpath:/static/");  // 静态资源路径
    }

    /**
     * jwt拦截器
     * */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}