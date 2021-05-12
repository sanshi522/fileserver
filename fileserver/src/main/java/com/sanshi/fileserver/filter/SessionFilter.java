package com.sanshi.fileserver.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
public class SessionFilter implements Filter {
    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String NO_LOGIN = "nologin";

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/login", "/js", "/css", "/images", "/favicon.ico", "/join", "/findpass", "file", "/error", "/hello", "/subject"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * @param uri
     * @Author: xxxxx
     * @Description: 是否需要过滤
     */
    public boolean isNeedFilter(String uri) {
        if (uri.equals("/"))
            return false;
        for (String includeUrl : includeUrls) {
            //if(includeUrl.equals(uri)) {
            if (uri.contains(includeUrl)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();


        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);

        System.out.println("filter url:" + uri);
        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println("指定的不过滤");
        } else { //需要过滤器
            // session中包含user对象,则是登录状态
            if (session != null && session.getAttribute("user") != null) {
                // System.out.println("user:"+session.getAttribute("user"));
                System.out.println("已登录，不过滤");
                filterChain.doFilter(request, response);
            } else {
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
                    System.out.println("返回未登录json");
                    response.getWriter().write(NO_LOGIN);
                    //response.sendRedirect(request.getContextPath()+"/");
                } else {
                    //重定向到登录页(需要在static文件夹下建立此html文件)
                    System.out.println("重定向到登录页:" + request.getContextPath() + "/");
                    response.sendRedirect(request.getContextPath() + "/");
                }
                return;
            }
        }
    }

    @Override
    public void destroy() {

    }
}
