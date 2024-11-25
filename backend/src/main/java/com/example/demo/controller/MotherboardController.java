package com.example.demo.controller;

import com.example.demo.po.Motherboard;
import com.example.demo.service.MotherboardService;
import com.example.demo.vo.MotherboardVO;
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
@RequestMapping("/api/motherboard")
public class MotherboardController {
    @Autowired
    private MotherboardService motherboardService;

    /**
     * 获取所有Motherboard
     *
     * @return 所有 Motherboard
     */
    @RequestMapping("/all")
    public ResultVO<List<MotherboardVO>> getAllMotherboards() {
        return ResultVO.buildSuccess(motherboardService.getAllMotherboards());
    }

    /**
     * 获取单个Motherboard
     *
     * @param id Motherboard id
     * @return 单个 Motherboard
     */
    @RequestMapping("/{id}")
    public ResultVO<MotherboardVO> getMotherboard(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(motherboardService.getMotherboard(id));
    }
}
