package com.example.demo.service;

import com.example.demo.po.Chassis;
import com.example.demo.vo.ChassisVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface ChassisService {
    List<ChassisVO> getAllChassiss();

    ChassisVO getChassis(Integer id);
}
