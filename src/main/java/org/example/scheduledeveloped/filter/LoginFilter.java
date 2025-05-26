package org.example.scheduledeveloped.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledeveloped.Common.Const;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {
            "/",
            "/auth/signup",
            "/auth/login",
            "/logout",
            "/login.html",
            "/signup.html"
    };

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse response1 = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        if (!isWhiteList(requestURI)){
            HttpSession session = httpRequest.getSession(false);

            if(session==null||session.getAttribute(Const.LOGIN_USER) == null){
                throw new RuntimeException("로그인 해주세요");

            }

            log.info("로그인에 성공했습니다.");

        }

        filterChain.doFilter(request,response);
    }

    private boolean isWhiteList(String requestURI){
        return PatternMatchUtils.simpleMatch(WHITE_LIST,requestURI);
    }
}
