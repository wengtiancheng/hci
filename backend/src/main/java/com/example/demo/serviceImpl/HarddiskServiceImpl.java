package com.example.demo.serviceImpl;

import com.example.demo.repository.HarddiskRepository;
import com.example.demo.service.HarddiskService;
import com.example.demo.vo.HarddiskVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.Harddisk;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class HarddiskServiceImpl implements HarddiskService {

    @Autowired
    HarddiskRepository harddiskRepository;

    @Override
    public List<HarddiskVO> getAllHarddisks() {
        return harddiskRepository.findAll().stream().map(Harddisk::toVO).toList();
    }

    @Override
    public HarddiskVO getHarddisk(Integer id) {
        return Objects.requireNonNull(harddiskRepository.findById(id).orElse(null)).toVO();
    }
}
