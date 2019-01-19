package org.sysu.bpmmanagementservice.controller.client;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//这里要使用Controller，因为RestController是返回json数据的
@Api(tags = "EditorController", description = "模型编辑器")
@Controller
//@RequestMapping("models")
public class EditorController {
    /**
     * 等同于访问：modeler.html?modelId=300001,这个是静态资源直接访问
     * @return
     */
    @ApiOperation(value = "进入流程编辑器，需要接入模型参数,editor?modelId=XXX")
    @GetMapping(value = "editor")
    public String edtior() {
        return "modeler";
    }
}
