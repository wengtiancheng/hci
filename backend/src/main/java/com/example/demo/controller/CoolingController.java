package com.example.demo.controller;

import com.example.demo.po.Cooling;
import com.example.demo.service.CoolingService;
import com.example.demo.vo.CoolingVO;
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
@RequestMapping("/api/Cooling")
public class CoolingController {
    @Autowired
    private CoolingService CoolingService;

    /**
     * 获取所有Cooling
     *
     * @return 所有 Cooling
     */
    @RequestMapping("/all")
    public ResultVO<List<CoolingVO>> getAllCoolings() {
        return ResultVO.buildSuccess(CoolingService.getAllCoolings());
    }

    /**
     * 获取单个Cooling
     *
     * @param id Cooling id
     * @return 单个 Cooling
     */
    @RequestMapping("/{id}")
    public ResultVO<CoolingVO> getCooling(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(CoolingService.getCooling(id));
    }
}
