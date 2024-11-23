package com.example.demo.service;

import com.example.demo.po.CPU;
import com.example.demo.vo.CPUVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface CPUService {
    List<CPUVO> getAllCPUs();

    CPUVO getCPU(Integer id);
}
