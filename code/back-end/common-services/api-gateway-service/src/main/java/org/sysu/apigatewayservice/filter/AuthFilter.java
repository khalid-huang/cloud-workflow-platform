package org.sysu.apigatewayservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.sysu.apigatewayservice.util.JWTUtils;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthFilter extends ZuulFilter {

    @Value("${filter.white-apis-uri}")
    private String whiteApisStr;

    @Value("${jwt.header.access}")
    private String accessTokenHeader;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthFilter() {
        super();

    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        //白名单，放过
        List<String> whiteApis = Arrays.asList(whiteApisStr.split(","));
        String uri = ctx.getRequest().getRequestURI();
        System.out.println(uri);
        System.out.println(whiteApis);
        if (whiteApis.contains(uri)) {
            return null;
        }
        // path uri 处理，类似 /user/{userId}
        for (String wapi : whiteApis) {
            if (wapi.contains("{") && wapi.contains("}")) {
                if (wapi.split("/").length == uri.split("/").length) {
                    String reg = wapi.replaceAll("\\{.*}", ".*{1,}");
                    System.err.println(reg);
                    Pattern r = Pattern.compile(reg);
                    Matcher m = r.matcher(uri);
                    if (m.find()) {
                        return null;
                    }
                }
            }
        }

        //获取accessCookie
        Cookie[] cookies = ctx.getRequest().getCookies();
        Cookie accessCookie = null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(accessTokenHeader)) {
                accessCookie = cookie;
            }
        }

        //验证TOKEN
        if (accessCookie == null) {
            ctx.setSendZuulResponse(false);
            ctx.set("isSuccess", false);
            Map<String, String> result = new HashMap<>();
            result.put("status", "fail");
            result.put("msg", "非法请求【缺少认证信息】");
            ctx.setResponseBody(result.toString());
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }
        String accessToken = accessCookie.getValue();
        System.out.println(accessToken);
        JWTUtils.JWTResult jwt = JWTUtils.checkToken(accessToken, jwtSecret);
        if (!jwt.isStatus()) {
            ctx.setSendZuulResponse(false);
            ctx.set("isSuccess", false);
            Map<String, String> result = new HashMap<>();
            result.put("status", "fail");
            result.put("msg", jwt.getMsg());
            ctx.setResponseBody(result.toString());
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }
        ctx.addZuulRequestHeader("tenantId", (String)jwt.getClaims().get("tenantId"));
        ctx.addZuulRequestHeader("username", (String)jwt.getClaims().get("username"));
        ctx.addZuulRequestHeader("role", (String)jwt.getClaims().get("role"));
        System.out.println("claims: " + jwt.getClaims().toString());
        return null;
    }
}
