package com.example.demo.serviceImpl;

import com.example.demo.repository.MemoryRepository;
import com.example.demo.service.MemoryService;
import com.example.demo.vo.MemoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.Memory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class MemoryServiceImpl implements MemoryService {

    @Autowired
    MemoryRepository memoryRepository;

    @Override
    public List<MemoryVO> getAllMemorys() {
        return memoryRepository.findAll().stream().map(Memory::toVO).toList();
    }

    @Override
    public MemoryVO getMemory(Integer id) {
        return Objects.requireNonNull(memoryRepository.findById(id).orElse(null)).toVO();
    }
}
