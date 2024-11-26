package com.example.demo.controller;
import com.example.demo.exception.DemoException;

import com.example.demo.util.OssUtil;
import com.example.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class HelloController {
    @Autowired
    OssUtil ossUtil;

    @GetMapping("/hello")
    public ResultVO<String> sayHello() {
        return ResultVO.buildSuccess("你好世界");
    }

    @PostMapping("/images")
    public ResultVO<String> upload(@RequestParam MultipartFile file){
        String imageUrl;
        try {
            imageUrl = ossUtil.upload(file.getOriginalFilename(),file.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
            throw DemoException.fileUploadFail();
        }
        return ResultVO.buildSuccess(imageUrl);
    }
}