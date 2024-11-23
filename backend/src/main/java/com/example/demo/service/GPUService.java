package com.example.demo.service;

import com.example.demo.po.GPU;
import com.example.demo.vo.GPUVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface GPUService {
    List<GPUVO> getAllGPUs();

    GPUVO getGPU(Integer id);
}
