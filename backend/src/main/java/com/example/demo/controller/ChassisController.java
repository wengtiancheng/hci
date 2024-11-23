package com.example.demo.controller;

import com.example.demo.po.Chassis;
import com.example.demo.service.ChassisService;
import com.example.demo.vo.ChassisVO;
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
@RequestMapping("/api/Chassis")
public class ChassisController {
    @Autowired
    private ChassisService ChassisService;

    /**
     * 获取所有Chassis
     *
     * @return 所有 Chassis
     */
    @RequestMapping("/all")
    public ResultVO<List<ChassisVO>> getAllChassiss() {
        return ResultVO.buildSuccess(ChassisService.getAllChassiss());
    }

    /**
     * 获取单个Chassis
     *
     * @param id Chassis id
     * @return 单个 Chassis
     */
    @RequestMapping("/{id}")
    public ResultVO<ChassisVO> getChassis(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(ChassisService.getChassis(id));
    }
}
