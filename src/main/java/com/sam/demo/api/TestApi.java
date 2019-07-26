package com.sam.demo.api;


import com.alibaba.fastjson.JSON;
import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/test")
@Api(tags = "测试API")
public class TestApi {
    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/test")
    @ApiOperation(value = "组装block123")
    public List<BlockInfoVO> test() {
        List<BlockInfoVO> blockInfoVOS = commonService.buildBlock();
        return blockInfoVOS;
    }

    @GetMapping(value = "/get")
    @ApiOperation(value = "测试查询")
    @ResponseBody
    public List<CustomerVO> get() {
        List<CustomerVO> customerVOS = commonService.queryCustomer();
        return customerVOS;
    }


    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/testEnum")
    @ApiOperation(value = "测试实体类中的枚举类")
    @ResponseBody
    public List<CustomerEntity> testEnum() {
        List<CustomerEntity> customerEntities = commonService.testEnum();
        return customerEntities;
    }

    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/testEnum1",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "测试实体类中的枚举类")
    @ResponseBody
    public String testsave() {
        List<CustomerEntity> customerEntities = commonService.testEnum();
        System.out.println(JSON.toJSONString(customerEntities));
        return JSON.toJSONString(customerEntities);
    }

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

    @GetMapping(value = "testEntityManager")
    @ResponseBody
    public String testEntityManager(){
        commonService.testEntityManager();
        return "";
    }

    /**
     * 实体类中的枚举类没办法序列化在实体中
     *
     * @return
     */
    @GetMapping(value = "/update")
    @ApiOperation(value = "批量新增")
    @ResponseBody()
    public String update() {
        commonService.update();
        return null;
    }


    @GetMapping(value = "updateBatch")
    @ResponseBody
    public String updateBatch(){
        commonService.updateBatch();
        return "";
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


    @GetMapping(value = "mergeAll")
    @ResponseBody
    public String mergeAll(){
        commonService.mergeAll();
        return "";
    }
}
