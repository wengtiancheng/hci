package com.example.demo.service;

import com.example.demo.po.Cooling;
import com.example.demo.vo.CoolingVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface CoolingService {
    List<CoolingVO> getAllCoolings();

    CoolingVO getCooling(Integer id);
}
