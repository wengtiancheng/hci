package com.example.demo.service;

import com.example.demo.po.Motherboard;
import com.example.demo.vo.MotherboardVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface MotherboardService {
    List<MotherboardVO> getAllMotherboards();

    MotherboardVO getMotherboard(Integer id);
}
