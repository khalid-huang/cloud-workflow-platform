package org.sysu.bpmmanagementservice.multitenancy;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String tenant = request.getParameter("tenantId");
        if(tenant == null) {
            //尝试从头部获取；针对非登录请求
            tenant =  request.getHeader("tenantId");
        }
        System.out.println("tenantId: " + tenant);
        TenantContext.setCurrentTenant(tenant);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }
}
