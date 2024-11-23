package com.example.demo.serviceImpl;

import com.example.demo.repository.GPURepository;
import com.example.demo.service.GPUService;
import com.example.demo.vo.GPUVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.GPU;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class GPUServiceImpl implements GPUService {

    @Autowired
    GPURepository gpuRepository;

    @Override
    public List<GPUVO> getAllGPUs() {
        return gpuRepository.findAll().stream().map(GPU::toVO).toList();
    }

    @Override
    public GPUVO getGPU(Integer id) {
        return Objects.requireNonNull(gpuRepository.findById(id).orElse(null)).toVO();
    }
}
