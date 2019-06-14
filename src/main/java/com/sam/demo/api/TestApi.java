package com.sam.demo.api;


import com.sam.demo.VO.BlockInfoVO;
import com.sam.demo.VO.InvoiceDetailVO;
import com.sam.demo.util.BeanConvertUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Test api.
 *
 * @author Rakim
 * @date 2019 /5/17
 */
@RestController
@RequestMapping("/test")
public class TestApi {

    /**
     * Test date json entity.
     *
     * @return the json entity
     */
    @GetMapping(value = "/test")
    public List<BlockInfoVO> testDate() {
        InvoiceDetailVO detail1 = InvoiceDetailVO.builder().assetId("123").build();
        InvoiceDetailVO detail2 = InvoiceDetailVO.builder().assetId("456").build();
        List<BlockInfoVO> list= new ArrayList<>();
        try {
            list.addAll(BeanConvertUtils.convert(detail1,BlockInfoVO.class));
            list.addAll(BeanConvertUtils.convert(detail1,BlockInfoVO.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @GetMapping()
    public String test() {
        InvoiceDetailVO detail1 = InvoiceDetailVO.builder().assetId("123").build();
        InvoiceDetailVO detail2 = InvoiceDetailVO.builder().assetId("456").build();
        List<BlockInfoVO> list= new ArrayList<>();
        try {
            list.addAll(BeanConvertUtils.convert(detail1,BlockInfoVO.class));
            list.addAll(BeanConvertUtils.convert(detail1,BlockInfoVO.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
