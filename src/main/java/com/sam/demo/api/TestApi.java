package com.sam.demo.api;


import com.sam.demo.VO.CustomerVO;
import com.sam.demo.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Api(tags = "测试API")
public class TestApi {
    @Autowired
    private CommonService commonService;


    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/testSave")
    @ApiOperation(value = "测试含有枚举属性的entity新增")
    @ResponseBody()
    public String testSave() {
        commonService.testSave();
        return null;
    }


    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/testSaveAll")
    @ApiOperation(value = "批量新增")
    @ResponseBody()
    public String testSaveAll() {
        commonService.testSaveAll();
        return null;
    }

    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/persistAll")
    @ApiOperation(value = "批量新增")
    @ResponseBody()
    public String persistAll() {
        commonService.persistAll();
        return null;
    }
    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/persistAllWithAuto")
    @ApiOperation(value = "批量新增")
    @ResponseBody()
    public String persistAllWithAuto() {
        commonService.persistAllWithAuto();
        return null;
    }
    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/testValidate")
    @ResponseBody()
    public String testValidate(@Valid CustomerVO customerVO) {
        return null;
    }




    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/merge")
    @ApiOperation(value = "批量新增")
    @ResponseBody()
    public String merge() {
        commonService.merge();
        return null;
    }


}
