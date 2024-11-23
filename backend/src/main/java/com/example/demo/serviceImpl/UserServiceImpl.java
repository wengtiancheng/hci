package com.example.demo.serviceImpl;

import com.example.demo.exception.DemoException;
import com.example.demo.po.User;
import com.example.demo.po.Solution;
import com.example.demo.repository.SolutionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.SecurityUtil;
import com.example.demo.util.TokenUtil;
import com.example.demo.vo.SolutionVO;
import com.example.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: GaoZhaolong
 * @Date: 14:46 2023/11/26
 *
 * 注册登录功能实现
*/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SecurityUtil securityUtil;
    
    @Autowired
    SolutionRepository solutionRepository;

    @Override
    public Boolean register(UserVO userVO) {
        if (userVO == null) throw DemoException.userNotExists();
        User user = userRepository.findByPhone(userVO.getPhone());
        if (user != null) throw DemoException.phoneAlreadyExists();
        User newUser = userVO.toPO();
        newUser.setCreateTime(new Date());
        userRepository.save(newUser);
        return true;
    }

    @Override
    public String login(String phone, String password) {
        User user = userRepository.findByPhone(phone);
        if (user == null) throw DemoException.phoneOrPasswordError();
        return tokenUtil.getToken(user);
    }

    @Override
    public UserVO getInformation() {
        User user=securityUtil.getCurrentUser();
        return user.toVO();
    }

    @Override
    public List<SolutionVO> getMySolutions() {
        User user=securityUtil.getCurrentUser();
        List<Integer> solutions = user.getMySolutions();
        if (solutions == null) throw DemoException.mySolutionNotExists();

        List<Solution> solutionList = solutions.stream()
            .map(solutionId -> solutionRepository.findById(solutionId).orElse(null))
            .toList();

        return solutionList.stream()
            .filter(Objects::nonNull) // 过滤掉 null 值
            .map(Solution :: toVO) // 转换为 SolutionVO
            .toList();
    }


}
