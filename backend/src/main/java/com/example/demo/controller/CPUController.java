package com.example.demo.controller;

import com.example.demo.po.CPU;
import com.example.demo.service.CPUService;
import com.example.demo.vo.CPUVO;
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
@RequestMapping("/api/CPU")
public class CPUController {
    @Autowired
    private CPUService cpuService;

    /**
     * 获取所有CPU
     *
     * @return 所有 CPU
     */
    @RequestMapping("/all")
    public ResultVO<List<CPUVO>> getAllCPUs() {
        return ResultVO.buildSuccess(cpuService.getAllCPUs());
    }

    /**
     * 获取单个CPU
     *
     * @param id CPU id
     * @return 单个 CPU
     */
    @RequestMapping("/{id}")
    public ResultVO<CPUVO> getCPU(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(cpuService.getCPU(id));
    }
}
