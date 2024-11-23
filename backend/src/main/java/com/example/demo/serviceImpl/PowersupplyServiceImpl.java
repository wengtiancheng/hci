package com.example.demo.serviceImpl;

import com.example.demo.repository.PowersupplyRepository;
import com.example.demo.service.PowersupplyService;
import com.example.demo.vo.PowersupplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.Powersupply;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class PowersupplyServiceImpl implements PowersupplyService {

    @Autowired
    PowersupplyRepository powersupplyRepository;

    @Override
    public List<PowersupplyVO> getAllPowersupplys() {
        return powersupplyRepository.findAll().stream().map(Powersupply::toVO).toList();
    }

    @Override
    public PowersupplyVO getPowersupply(Integer id) {
        return Objects.requireNonNull(powersupplyRepository.findById(id).orElse(null)).toVO();
    }
}
