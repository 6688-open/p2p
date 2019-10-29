package com.dj.boot.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /**
     * 注入自定义拦截器
     * @return
     */
    @Bean
    public InterceptorConfig interceptorConfig(){
        return new InterceptorConfig();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径 注意->先 移除路径 再添加拦截所有路径
        registry.addInterceptor(interceptorConfig()).excludePathPatterns("/loan/**","/user/login", "/swagger**/**", "/user/register").addPathPatterns("/**");
        super.addInterceptors(registry);
    }

//    /**
//     * 添加静态资源文件，外部可以直接访问地址
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
//        //第一个方法设置访问路径前缀，第二个方法设置资源路径
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
//    }

}
