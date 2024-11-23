package com.example.demo.serviceImpl;

import com.example.demo.repository.CPURepository;
import com.example.demo.service.CPUService;
import com.example.demo.vo.CPUVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.CPU;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class CPUServiceImpl implements CPUService {

    @Autowired
    CPURepository cpuRepository;

    @Override
    public List<CPUVO> getAllCPUs() {
        return cpuRepository.findAll().stream().map(CPU::toVO).toList();
    }

    @Override
    public CPUVO getCPU(Integer id) {
        return Objects.requireNonNull(cpuRepository.findById(id).orElse(null)).toVO();
    }
}
