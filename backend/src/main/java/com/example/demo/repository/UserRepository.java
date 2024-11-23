package com.example.demo.repository;

import com.example.demo.po.Solution;
import com.example.demo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

// git
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhone(String phone);

    User findByPhoneAndPassword(String phone, String password);

}
