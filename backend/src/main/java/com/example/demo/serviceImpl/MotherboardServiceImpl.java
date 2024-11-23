package com.example.demo.serviceImpl;

import com.example.demo.repository.MotherboardRepository;
import com.example.demo.service.MotherboardService;
import com.example.demo.vo.MotherboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.Motherboard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class MotherboardServiceImpl implements MotherboardService {

    @Autowired
    MotherboardRepository motherboardRepository;

    @Override
    public List<MotherboardVO> getAllMotherboards() {
        return motherboardRepository.findAll().stream().map(Motherboard::toVO).toList();
    }

    @Override
    public MotherboardVO getMotherboard(Integer id) {
        return Objects.requireNonNull(motherboardRepository.findById(id).orElse(null)).toVO();
    }
}
