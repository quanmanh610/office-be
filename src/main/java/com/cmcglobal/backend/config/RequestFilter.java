package com.cmcglobal.backend.config;


import com.cmcglobal.backend.dto.poa.UserLogin;
import com.cmcglobal.backend.utility.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter implements Filter {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (request.getRequestURI().contains("/api/v1/auth/") || request.getRequestURI().contains("swagger-ui") || request.getRequestURI().contains("api-docs") || request.getRequestURI().contains("favicon.ico")) {
            chain.doFilter(request, response);
        } else if (!request.getRequestURI().equals("/api/v1/auth/login")) {
            String authorization = request.getHeader("Authorization");
            UserLogin userLogin = null;
            try {
                userLogin = cacheManager.get(authorization, UserLogin.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (userLogin == null) {
                response.setHeader("Content-Type", "application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"massage\":\"SC_UNAUTHORIZED\"}");
            } else {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userLogin.getUser().getUserName(), authorization, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
        }
    }
}
