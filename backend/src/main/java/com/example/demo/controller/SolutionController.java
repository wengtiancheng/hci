package com.example.demo.controller;

import com.example.demo.service.SolutionService;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.SolutionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/23 21:04
 */
@RestController
@RequestMapping("/api/users")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    /**
     * 获取所有的 装机方案
     * 不需要登录也可以访问
     *
     * @return 所有的 装机方案
     */
    @GetMapping("/all")
    public ResultVO<List<SolutionVO>> getAllSolutions(){
        return ResultVO.buildSuccess(solutionService.getAllSolutions());
    }

    /**
     * 保存一个 装机方案
     * 需要登录
     *
     * @param solutionVO
     * @return 保存是否成功
     */
    @PostMapping("/save")
    public ResultVO<Boolean> saveSolution(@RequestBody SolutionVO solutionVO){
        return ResultVO.buildSuccess(solutionService.saveSolution(solutionVO));
    }
}
