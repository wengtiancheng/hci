package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.SolutionVO;
import com.example.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResultVO<Boolean> register(@RequestBody UserVO userVO){
        return ResultVO.buildSuccess(userService.register(userVO));
    }

    @PostMapping("/login")
    public ResultVO<String> login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return ResultVO.buildSuccess(userService.login(phone, password));
    }

    @GetMapping
    public ResultVO<UserVO> getInformation(){
        return ResultVO.buildSuccess(userService.getInformation());
    }

    /**
     * 获取 我的所有 装机方案
     *
     * @return 所有 装机方案
     */
    @GetMapping("/solutions")
    public ResultVO<List<SolutionVO>> getMySolutions(){
        return ResultVO.buildSuccess(userService.getMySolutions());
    }

}
