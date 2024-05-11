package com.example.clothing.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurerNew implements WebMvcConfigurer {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        System.out.println("filters are added");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(jwtTokenFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/checkout", "/logout", "/forgotPassword/verifyAccount",
                "/forgotPassword/sendOTP", "/forgotPassword/verifyOTP", "/forgotPassword/changePassword");
        return registrationBean;
    }

}
