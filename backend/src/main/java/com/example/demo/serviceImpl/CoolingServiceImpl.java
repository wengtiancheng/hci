package com.example.demo.serviceImpl;

import com.example.demo.repository.CoolingRepository;
import com.example.demo.service.CoolingService;
import com.example.demo.vo.CoolingVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.Cooling;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class CoolingServiceImpl implements CoolingService {

    @Autowired
    CoolingRepository coolingRepository;

    @Override
    public List<CoolingVO> getAllCoolings() {
        return coolingRepository.findAll().stream().map(Cooling::toVO).toList();
    }

    @Override
    public CoolingVO getCooling(Integer id) {
        return Objects.requireNonNull(coolingRepository.findById(id).orElse(null)).toVO();
    }
}

