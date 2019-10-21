package com.sam.demo.api;


import com.sam.demo.VO.CustomerVO;
import com.sam.demo.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import java.util.Set;

@RestController
@RequestMapping("validate")
@Api(tags = "校验API")
public class ValidateApi {
    @Autowired
    private CommonService commonService;

    @Autowired
    private Validator validator;

    @PostMapping(value = "/test")
    @ApiOperation(value = "组装block")
    public String test(@RequestBody @Valid CustomerVO customerVO) {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

        ScriptEngine scriptEngine1 = new NashornScriptEngineFactory().getScriptEngine();

        return "123";
    }

    @PostMapping(value = "/test2")
    @ApiOperation(value = "组装block")
    public String test2(@RequestBody CustomerVO customerVO) {

        Set<ConstraintViolation<CustomerVO>> constraintViolations = validator.validate(customerVO);

        return "123";
    }


}
