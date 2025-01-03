package com.example.demo.service;

import com.example.demo.vo.FilterVO;
import com.example.demo.vo.SolutionVO;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/23 21:08
 */
public interface SolutionService {
    // 获取所有 装机方案
    List<SolutionVO> getAllSolutions();
    Boolean saveSolution(SolutionVO solutionVO);
    Boolean deleteSolution(Integer id);
    Boolean starSolution(Integer id);

    SolutionVO getSolution(Integer id);

    List<SolutionVO> getAllSolutionsByFilter(FilterVO filterVO);

//    Boolean unStarSolution(Integer id);
}
