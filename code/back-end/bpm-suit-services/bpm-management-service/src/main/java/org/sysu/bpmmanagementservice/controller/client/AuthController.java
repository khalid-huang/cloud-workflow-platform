package org.sysu.bpmmanagementservice.controller.client;

import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sysu.bpmmanagementservice.constant.ResponseConstantManager;
import org.sysu.bpmmanagementservice.entity.ActIdUserEntity;
import org.sysu.bpmmanagementservice.service.AuthService;
import org.sysu.bpmmanagementservice.vo.AccountVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "AuthController", description = "用户认证服务")
public class AuthController {
    @Autowired
    AuthService authService;

    @ApiOperation(value = "登录入口")
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestParam(value = "username") String username,
                                @RequestParam(value = "password") String password,
                                HttpServletResponse httpServletResponse) {
        Map<String, Object> result = new HashMap<>();
        //先进行登录认证
        AccountVo accountVo = authService.authenticate(username, password);
        if(accountVo == null) {
            result.put("status", ResponseConstantManager.STATUS_FAIL);
            result.put("msg", "用户不存在或是密码错误");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        //生成jwt token
        Cookie accessCookie = authService.generateAccessCookie(accountVo);
        httpServletResponse.addCookie(accessCookie);
        result.put("status", ResponseConstantManager.STATUS_SUCCESS);
        result.put("msg", "登录成功");
        result.put("account", accountVo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
