package com.example.demo.controller;

import com.example.demo.po.Memory;
import com.example.demo.service.MemoryService;
import com.example.demo.vo.MemoryVO;
import com.example.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:20
 */

@RestController
@RequestMapping("/api/Memory")
public class MemoryController {
    @Autowired
    private MemoryService MemoryService;

    /**
     * 获取所有Memory
     *
     * @return 所有 Memory
     */
    @RequestMapping("/all")
    public ResultVO<List<MemoryVO>> getAllMemorys() {
        return ResultVO.buildSuccess(MemoryService.getAllMemorys());
    }

    /**
     * 获取单个Memory
     *
     * @param id Memory id
     * @return 单个 Memory
     */
    @RequestMapping("/{id}")
    public ResultVO<MemoryVO> getMemory(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(MemoryService.getMemory(id));
    }
}
