package com.sanshi.fileserver.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WaisHandleInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("==postHandle:在Controller调用之后 且 页面渲染之前运行==" + handler.getClass() + "-----" + response.getStatus());
        if (response.getStatus() == 500) {
            modelAndView.setViewName("error/500");
        } else if (response.getStatus() == 400) {
            modelAndView.setViewName("error/400");
        } else if (response.getStatus() == 401) {
            modelAndView.setViewName("error/401");
        } else if (response.getStatus() == 404) {
            modelAndView.setViewName("error/404");
        }
    }
}
