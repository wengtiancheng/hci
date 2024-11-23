package com.example.demo.serviceImpl;

import com.example.demo.repository.ChassisRepository;
import com.example.demo.service.ChassisService;
import com.example.demo.vo.ChassisVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.Chassis;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class ChassisServiceImpl implements ChassisService {

    @Autowired
    ChassisRepository chassisRepository;

    @Override
    public List<ChassisVO> getAllChassiss() {
        return chassisRepository.findAll().stream().map(Chassis::toVO).toList();
    }

    @Override
    public ChassisVO getChassis(Integer id) {
        return Objects.requireNonNull(chassisRepository.findById(id).orElse(null)).toVO();
    }
}

