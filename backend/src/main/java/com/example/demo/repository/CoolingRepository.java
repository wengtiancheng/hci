package com.example.demo.repository;

import com.example.demo.po.Cooling;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:23
 */
public interface CoolingRepository extends JpaRepository<Cooling, Integer> {
}
