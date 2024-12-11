package com.example.demo.service;

import com.example.demo.vo.SolutionVO;
import com.example.demo.vo.UserVO;

import java.util.List;

public interface UserService {
    Boolean register(UserVO userVO);

    String login(String phone,String password);

    UserVO getInformation();

    List<SolutionVO> getMySolutions();

    List<String> getSolutionImages(Integer solutionId);

    //...removeReceiveInfo
}
