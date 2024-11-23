package com.example.demo.service;

import com.example.demo.po.Display;
import com.example.demo.vo.DisplayVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface DisplayService {
    List<DisplayVO> getAllDisplays();

    DisplayVO getDisplay(Integer id);
}
