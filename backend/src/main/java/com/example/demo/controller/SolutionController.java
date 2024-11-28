package com.example.demo.controller;

import com.example.demo.service.SolutionService;
import com.example.demo.vo.FilterVO;
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
@RequestMapping("/api/solution")
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
     * 获取所有的 装机方案，根据过滤条件
     *
     * @param filterVO
     * @return
     */
    @PostMapping("/allByFilter")
    public ResultVO<List<SolutionVO>> getAllSolutionsByFilter(@RequestBody FilterVO filterVO){
//        System.out.println(filterVO.getSortBy());
        return ResultVO.buildSuccess(solutionService.getAllSolutionsByFilter(filterVO));
    }

    /**
     * 保存一个 装机方案
     * 需要登录
     *
     * @param solutionVO
     * class SolutionVO {
     *     private String name; // 方案名称
     *     private String description; // 方案描述
     *     // 组件id
     *     private Integer cpuId;
     *     private Integer motherboardId;
     *     private Integer gpuId;
     *     private Integer memoryId;
     *     private Integer harddiskId;
     *     private Integer powersupplyId;
     *     private Integer coolingId;
     *     private Integer chassisId;
     *     private Integer displayId;
     * }
     * @return 保存是否成功
     */
    @PostMapping("/save")
    public ResultVO<Boolean> saveSolution(@RequestBody SolutionVO solutionVO){
        return ResultVO.buildSuccess(solutionService.saveSolution(solutionVO));
    }

    /**
     * 收藏 一个 装机方案
     * @param id
     * @return
     */
    @PostMapping("/star/{id}")
    public ResultVO<Boolean> starSolution(@PathVariable(value = "id") Integer id){
        return ResultVO.buildSuccess(solutionService.starSolution(id));
    }

    /**
      * 获取一个 装机方案
      * 不需要登录也可以访问
      *
      * @param id
      * @return
      */
    @GetMapping("/{id}")
    public ResultVO<SolutionVO> getSolution(@PathVariable(value = "id") Integer id){
        return ResultVO.buildSuccess(solutionService.getSolution(id));
    }
}
