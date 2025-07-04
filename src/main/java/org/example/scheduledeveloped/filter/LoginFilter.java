package org.example.scheduledeveloped.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledeveloped.Common.Const;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;


/**
 * 로그인 하지 않으면 허용되는 API 이외의 request 가
 * 접근하지 못 하도록 거르게 하는 클래스
 */
@Slf4j
public class LoginFilter implements Filter {

    //허용되는 URI
    private static final String[] WHITE_LIST = {
            "/",
            "/auth/signup",
            "/auth/login",
            "/logout",
            "/login.html",
            "/signup.html",
            "/favicon.ico"
    };


    /**
     * 인증이 필요하지 않은(화이트리스트) URI를 제외한 요청에 대해
     * 세션에 로그인 정보가 존재하는지 확인한다.
     * 로그인되어 있지 않으면 401 오류를 응답하거나 예외를 던지고,
     * 로그인된 경우에는 요청을 다음 필터로 넘긴다.
     */

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
