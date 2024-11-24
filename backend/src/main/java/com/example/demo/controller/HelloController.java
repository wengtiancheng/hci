package com.example.demo.controller;

import com.example.demo.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/Hello")
    public ResultVO<String> sayHello() {
        return ResultVO.buildSuccess("你好世界");
    }
}