package com.example.demo.service;

import com.example.demo.po.Harddisk;
import com.example.demo.vo.HarddiskVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface HarddiskService {
    List<HarddiskVO> getAllHarddisks();

    HarddiskVO getHarddisk(Integer id);
}
