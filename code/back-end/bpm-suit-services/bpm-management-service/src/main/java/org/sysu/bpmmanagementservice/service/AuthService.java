package org.sysu.bpmmanagementservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.sysu.bpmmanagementservice.constant.GlobalContext;
import org.sysu.bpmmanagementservice.dao.ActIdUserEntityDao;
import org.sysu.bpmmanagementservice.dao.ActIdUserInfoEntityDao;
import org.sysu.bpmmanagementservice.entity.ActIdUserEntity;
import org.sysu.bpmmanagementservice.multitenancy.TenantContext;
import org.sysu.bpmmanagementservice.util.JWTUtils;
import org.sysu.bpmmanagementservice.vo.AccountVo;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access_exp}")
    private Long accessExp;

    @Value("${jwt.header.access}")
    private String accessTokenHeader;

    @Autowired
    ActIdUserEntityDao actIdUserEntityDao;

    @Autowired
    ActIdUserInfoEntityDao actIdUserInfoEntityDao;

    //查询用户名和密码
    public AccountVo authenticate(String username, String password) {
        ActIdUserEntity account = actIdUserEntityDao.findByUsername(username);
        //为了方便先明文存储
        if(account != null && account.getPassword().equals(password)) {
            //获取用户身份
            //获取用户角色
            String role = actIdUserInfoEntityDao.findByUserIdAndKey(account.getUsername(), GlobalContext.AUTH_ACCOUNT_ROLE).getValue();
            AccountVo accountVo = new AccountVo();
            accountVo.setUsername(account.getUsername());
            accountVo.setRole(role);
            return accountVo;
        } else {
            return null;
        }
    }

    public Cookie generateAccessCookie(AccountVo accountVo) {
        String accessToken = generateAccessToken(accountVo);
        System.out.println(accessToken);
        Cookie accessCookie = new Cookie(accessTokenHeader, accessToken);
        accessCookie.setMaxAge(accessExp.intValue());
        accessCookie.setPath("/");
//        accessCookie.setHttpOnly(true);
//        accessCookie.setSecure(true);
        return accessCookie;
    }

    //生成访问token，这里jwt Token
    private String generateAccessToken(AccountVo accountVo) {

        //封装claim: tid (租户id), uid(用户id) ,role（用户角色）
        Map<String, Object> claims = new HashMap<>();
        claims.put("tenantId", TenantContext.getCurrentTenant());
        claims.put("username", accountVo.getUsername());
        claims.put("role", accountVo.getRole());
        System.out.println(claims);
        String token = JWTUtils.getToken(claims, accessExp, jwtSecret);
        return token;
    }

}
