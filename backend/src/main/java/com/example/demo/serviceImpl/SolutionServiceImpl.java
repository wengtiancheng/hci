package com.example.demo.serviceImpl;
import com.example.demo.exception.DemoException;
import com.example.demo.po.User;
import com.example.demo.repository.SolutionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SolutionService;
import com.example.demo.util.SecurityUtil;
import com.example.demo.vo.SolutionVO;

import com.example.demo.po.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/23 21:13
 */
@Service
public class SolutionServiceImpl implements SolutionService{

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public List<SolutionVO> getAllSolutions() {
        return solutionRepository.findAll().stream().map(Solution::toVO).toList();
    }

    @Override
    public Boolean saveSolution(SolutionVO solutionVO) {
        User user = securityUtil.getCurrentUser();
        if (user == null) throw DemoException.notLogin();
        Solution solution = solutionVO.toPO();
        List<Integer> solutions = user.getMySolutions();
        solutions.add(solution.getId());
        user.setMySolutions(solutions);
        userRepository.save(user);
        return true;
    }
}
