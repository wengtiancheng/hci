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
     * 获取 我的收藏 装机方案
     *
     * @return 装机方案s
     */
    @GetMapping("/starSolutions")
    public ResultVO<List<SolutionVO>> getMyStarSolutions(){
        return ResultVO.buildSuccess(userService.getMyStarSolutions());
    }

    /**
     * 获取 我的创建装机方案
     *
     * @return 装机方案s
     */
    @GetMapping("/saveSolutions")
    public ResultVO<List<SolutionVO>> getMySaveSolutions(){
        return ResultVO.buildSuccess(userService.getMySaveSolutions());
    }

}
