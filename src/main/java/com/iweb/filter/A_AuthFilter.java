// package com.iweb.filter;
//
// import javax.servlet.*;
// import javax.servlet.annotation.WebFilter;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
//
// /**
//  * @author Liu Xiong
//  * @date 9/12/2023 上午10:30
//  */
// @WebFilter("/*")
// public class A_AuthFilter implements Filter {
//     @Override
//     public void init(FilterConfig filterConfig) throws ServletException {
//
//     }
//
//     @Override
//     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//         // 参数转型
//         HttpServletRequest req = (HttpServletRequest) servletRequest;
//         HttpServletResponse resp = (HttpServletResponse) servletResponse;
//         String uri = req.getRequestURI();
//         if (uri.endsWith("login.html") || uri.endsWith("index") || uri.endsWith(".webp") || uri.endsWith("register.html") || uri.endsWith(".img")
//                 || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith(".jpeg") || uri.endsWith("code")) {
//             filterChain.doFilter(req, resp);
//             return;
//         }
//         Object user = req.getSession().getAttribute("user");
//         if (null == user) {
//             resp.sendRedirect("index");
//             return;
//         }
//         filterChain.doFilter(req, resp);
//     }
//
//     @Override
//     public void destroy() {
//
//     }
// }
