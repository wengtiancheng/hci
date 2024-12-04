package com.example.demo.repository;

import com.example.demo.po.Solution;
import com.example.demo.vo.SolutionVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/23 21:14
 */
public interface SolutionRepository extends JpaRepository<Solution, Integer> {
    @Override
    void deleteById(Integer integer);
}
