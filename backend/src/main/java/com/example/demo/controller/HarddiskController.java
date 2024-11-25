package com.example.demo.controller;

import com.example.demo.po.Harddisk;
import com.example.demo.service.HarddiskService;
import com.example.demo.vo.HarddiskVO;
import com.example.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:20
 */

@RestController
@RequestMapping("/api/harddisk")
public class HarddiskController {
    @Autowired
    private HarddiskService harddiskService;

    /**
     * 获取所有Harddisk
     *
     * @return 所有 Harddisk
     */
    @RequestMapping("/all")
    public ResultVO<List<HarddiskVO>> getAllHarddisks() {
        return ResultVO.buildSuccess(harddiskService.getAllHarddisks());
    }

    /**
     * 获取单个Harddisk
     *
     * @param id Harddisk id
     * @return 单个 Harddisk
     */
    @RequestMapping("/{id}")
    public ResultVO<HarddiskVO> getHarddisk(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(harddiskService.getHarddisk(id));
    }
}
