package org.example.scheduledeveloped.config;

import jakarta.servlet.Filter;
import org.example.scheduledeveloped.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 관련 설정을 정의하는 설정 클래스입니다.
 * 필터 등록 등을 통해 전체 애플리케이션의 요청 흐름을 제어할 수 있습니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 로그인 필터를 등록하는 메서드입니다.
     * 애플리케이션의 모든 요청("/*")에 대해 LoginFilter가 작동하도록 설정하며,
     * 필터의 실행 순서를 1로 지정하여 가장 먼저 실행되도록 합니다.
     *
     * @return 필터 등록 설정 객체
     */
    @Bean
    public FilterRegistrationBean<Filter> loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());         // 사용할 필터 클래스 지정
        filterRegistrationBean.setOrder(1);                          // 필터 실행 순서 지정 (낮을수록 먼저 실행)
        filterRegistrationBean.addUrlPatterns("/*");                 // 전체 경로에 필터 적용

        return filterRegistrationBean;
    }
}
