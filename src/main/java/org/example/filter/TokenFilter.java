package org.example.filter;


import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.CurrentHolder;
import org.example.utils.JwtUtil;
import org.springframework.util.StringUtils;

import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init ....");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("拦截到了请求 ....");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
//        1. 获取请求url
        String url = request.getRequestURL().toString();

//        2. 判断请求url中是否包含login，如果包含，说明是登录操作，放行
        if(url.contains("/login")){
            log.info("登录操作，放行");
            chain.doFilter(req,resp);
            return;
        }

//        3. 获取请求头中的令牌（token）
        String token = request.getHeader("token");

//        4. 判断令牌是否存在，如果不存在，响应 401
        if(!StringUtils.hasLength(token)){
            log.info("获取到jwt令牌为空, 返回错误结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

//        5. 解析token，如果解析失败，响应 401
        try {
            Claims claims = JwtUtil.parseJwt(token);
            String id = claims.get("id").toString();
            CurrentHolder.setCurrentEmpId(Integer.parseInt(id));

        } catch (Exception e) {
            log.info("解析令牌失败, 返回错误结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
//        6. 放行
        log.info("令牌合法，放行");
         chain.doFilter(req,resp);

         CurrentHolder.remove();
    }

    @Override
    public void destroy() {
        System.out.println("destroy ....");    }
}
