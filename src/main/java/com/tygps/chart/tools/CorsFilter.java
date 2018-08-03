package com.tygps.chart.tools;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ServletComponentScan
public class CorsFilter implements Filter {
    //    @Autowired
    //这个不能自动注入servlet和filter是被tomcat管理的
    private String[] excludePaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("initFilter");
        //不能在初始化中通过Appliaction Context获取因为这时候还没初始化Application Context
        //baseUserService = SpringUtils.getBean("baseUserService", BaseUserService.class);
        excludePaths = new String[]{"/api/user/noLogin", "/api/user/tokenError", "/api/user/loginForeground",
                "/api/user/loginBackground", "/api/user/inCorrectUserId"};
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //这里填写你允许进行跨域的主机ip
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //允许的访问方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        //Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        chain.doFilter(request, response);
    }

    private boolean excludePath(String path) {
        for (int i = 0; i < excludePaths.length; i++) {
            if (path.equals(excludePaths[i]))
                return true;
        }
        return false;
    }

    @Override
    public void destroy() {
        System.out.println("destroy method");
    }

}
