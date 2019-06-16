package com.sam.demo.api;


import com.alibaba.fastjson.JSON;
import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@Api(tags = "测试API")
public class TestApi {
    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/test")
    @ApiOperation(value = "组装block")
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

    @GetMapping(value = "/testsave")
    @ApiOperation(value = "测试含有枚举属性的entity新增")
    @ResponseBody()
    public String testEnum1() {
        commonService.testsave();
        return null;
    }
}
