package com.example.demo.controller;

import com.example.demo.po.GPU;
import com.example.demo.service.GPUService;
import com.example.demo.vo.GPUVO;
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
@RequestMapping("/api/gpu")
public class GPUController {
    @Autowired
    private GPUService GPUService;

    /**
     * 获取所有GPU
     *
     * @return 所有 GPU
     */
    @RequestMapping("/all")
    public ResultVO<List<GPUVO>> getAllGPUs() {
        return ResultVO.buildSuccess(GPUService.getAllGPUs());
    }

    /**
     * 获取单个GPU
     *
     * @param id GPU id
     * @return 单个 GPU
     */
    @RequestMapping("/{id}")
    public ResultVO<GPUVO> getGPU(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(GPUService.getGPU(id));
    }
}
