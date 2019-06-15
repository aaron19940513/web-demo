package com.sam.demo.api;


import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.CustomerVO;
import com.sam.demo.VO.InvoiceDetailVO;
import com.sam.demo.mysql.entity.CustomerEntity;
import com.sam.demo.service.CommonService;
import com.sam.demo.util.BeanConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestApi {
    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/test")
    public List<BlockInfoVO> testDate() {
        List<BlockInfoVO> blockInfoVOS = commonService.buildBlock();
        return blockInfoVOS;
    }

}
