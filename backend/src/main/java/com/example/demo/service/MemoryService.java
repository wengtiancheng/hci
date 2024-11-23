package com.example.demo.service;

import com.example.demo.po.Memory;
import com.example.demo.vo.MemoryVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:21
 */
public interface MemoryService {
    List<MemoryVO> getAllMemorys();

    MemoryVO getMemory(Integer id);
}
