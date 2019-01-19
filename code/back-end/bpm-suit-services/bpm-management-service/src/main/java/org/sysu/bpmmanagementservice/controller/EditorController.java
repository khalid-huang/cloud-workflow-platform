package org.sysu.bpmmanagementservice.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//这里要使用Controller，因为RestController是返回json数据的
@Api(tags = "EditorController", description = "模型编辑器")
@Controller
//@RequestMapping("models")
public class EditorController {
    /**
     * 等同于访问：modeler.html?modelId=300001,这个是静态资源直接访问
     * @return
     */
    @GetMapping(value = "editor")
    public String edtior() {
        return "modeler";
    }
}
