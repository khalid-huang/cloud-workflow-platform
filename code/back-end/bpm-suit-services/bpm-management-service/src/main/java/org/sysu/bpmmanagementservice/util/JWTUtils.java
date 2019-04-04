package org.sysu.bpmmanagementservice.util;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.sysu.bpmmanagementservice.constant.ResponseCode;

/**
 * API调用认证工具类，采用HS512加密
 *
 */
public class JWTUtils {

    private static class SingletonHolder {
        private static final JWTUtils INSTANCE = new JWTUtils();
    }

    public synchronized static JWTUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取Token
     * @param claims 需要保存的内容，主要有
     *               uid 用户ID
     *               role 授权用户身份
     *               tid 租户ID
     * @param expiration 失效时间，单位秒
     * @return
     */
    public static String getToken(Map<String, Object> claims, Long expiration, String secreteKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secreteKey).compact();
    }


    /**
     * 检查Token是否合法
     * @param token
     * @return JWTResult
     */
    public JWTResult checkToken(String token, String secreteKey) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(token).getBody();
            return new JWTResult(true, claims, "合法请求", ResponseCode.SUCCESS_CODE.getCode());
        } catch (ExpiredJwtException e) {
            // 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
            return new JWTResult(false, null, "token已过期", ResponseCode.TOKEN_TIMEOUT_CODE.getCode());
        } catch (SignatureException e) {
            // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            return new JWTResult(false, null, "非法请求", ResponseCode.NO_AUTH_CODE.getCode());
        } catch (Exception e) {
            return new JWTResult(false, null, "非法请求", ResponseCode.NO_AUTH_CODE.getCode());
        }
    }

    public static class JWTResult {
        private boolean status;
        private Map<String, Object> claims;
        private String msg;
        private int code;

        public JWTResult() {
            super();
        }

        public JWTResult(boolean status, Map<String,Object> claims, String msg, int code) {
            super();
            this.status = status;
            this.claims = claims;
            this.msg = msg;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public Map<String, Object> getClaims() {
            return claims;
        }

        public void setClaims(Map<String, Object> claims) {
            this.claims = claims;
        }
    }
}
