package com.example.demo.service;

import com.example.demo.po.Powersupply;
import com.example.demo.vo.PowersupplyVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface PowersupplyService {
    List<PowersupplyVO> getAllPowersupplys();

    PowersupplyVO getPowersupply(Integer id);
}
