package com.example.demo.controller;

import com.example.demo.po.Powersupply;
import com.example.demo.service.PowersupplyService;
import com.example.demo.vo.PowersupplyVO;
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
@RequestMapping("/api/powersupply")
public class PowersupplyController {
    @Autowired
    private PowersupplyService powersupplyService;

    /**
     * 获取所有Powersupply
     *
     * @return 所有 Powersupply
     */
    @RequestMapping("/all")
    public ResultVO<List<PowersupplyVO>> getAllPowersupplys() {
        return ResultVO.buildSuccess(powersupplyService.getAllPowersupplys());
    }

    /**
     * 获取单个Powersupply
     *
     * @param id Powersupply id
     * @return 单个 Powersupply
     */
    @RequestMapping("/{id}")
    public ResultVO<PowersupplyVO> getPowersupply(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(powersupplyService.getPowersupply(id));
    }
}
