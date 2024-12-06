package kr.heartbeat.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionTimeoutFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 메서드 (필요하면 사용, 여기서는 사용하지 않음)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // ServletRequest를 HttpServletRequest로 변환 (HTTP 관련 메서드 사용 가능)
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 요청 URI (요청된 자원의 경로)를 가져옴
        String requestURI = httpRequest.getRequestURI();

        // 디버깅용: 현재 요청된 URI를 콘솔에 출력
        System.out.println("Request URI: " + requestURI);

        // 컨텍스트 경로 (웹 애플리케이션의 루트 경로)를 가져옴
        String contextPath = httpRequest.getContextPath();

        /**
         * 1. 정적 리소스 요청 제외 처리
         * 정적 리소스 경로 (CSS, JS, 이미지 등)에 대한 요청은 필터를 통과시킴
         * 예: /resources/, /css/, /js/, /img/ 경로
         */
        if (requestURI.startsWith(contextPath + "/resources/")
                || requestURI.startsWith(contextPath + "/css/")
                || requestURI.startsWith(contextPath + "/js/")
                || requestURI.startsWith(contextPath + "/video/")
                || requestURI.startsWith(contextPath + "/img/")) {
            chain.doFilter(request, response); // 필터를 통과하여 요청 계속 처리
            return;
        }

        /**
         * 2. 로그인 및 회원가입 페이지 요청 제외 처리
         * 로그인 페이지(/login), 회원가입 페이지(/join), 또는 루트 페이지(/)에 대한 요청은 필터를 통과시킴
         */
        if (requestURI.endsWith("/login") || requestURI.endsWith("/join") || requestURI.equals(contextPath + "/") || requestURI.endsWith("/admin/*") ) {
            chain.doFilter(request, response); // 필터를 통과하여 요청 계속 처리
            return;
        }

        /**
         * 3. 세션 유효성 검사
         * 세션에서 "UserVO"라는 이름의 속성을 확인하여 사용자가 로그인 상태인지 검사
         */
        Object user = httpRequest.getSession().getAttribute("UserVO");

        if (user == null) {
            // 세션이 만료된 경우 처리

            // 응답의 Content-Type을 설정 (HTML 응답, UTF-8 인코딩 사용)
            httpResponse.setContentType("text/html; charset=UTF-8");

            // HTML 응답 작성: 경고 메시지를 표시하고 로그인 페이지로 리다이렉트
            httpResponse.getWriter().write(
                "<html>" +
                "<head>" +
                "<script>" +
                // alert()로 세션 만료 메시지를 사용자에게 표시
                "alert('세션이 만료되었습니다. 로그인 페이지로 이동합니다.');" +
                // 로그인 페이지로 이동
                "window.location='" + contextPath + "/login';" +
                "</script>" +
                "</head>" +
                "<body></body>" +
                "</html>"
            );

            // 여기서 필터 체인을 종료하고 반환 (요청 처리 중단)
            return;
        }

        /**
         * 4. 세션이 유효한 경우 요청 계속 처리
         * 세션이 유효하면 다음 필터 또는 컨트롤러로 요청을 전달
         */
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 필터 종료 메서드 (필요하면 사용, 여기서는 사용하지 않음)
    }
}
