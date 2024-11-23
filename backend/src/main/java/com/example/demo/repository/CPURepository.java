package com.example.demo.repository;

import com.example.demo.po.CPU;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:23
 */
public interface CPURepository extends JpaRepository<CPU, Integer> {
}
