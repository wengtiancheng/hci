package com.example.demo.serviceImpl;

import com.example.demo.exception.DemoException;
import com.example.demo.po.User;
import com.example.demo.po.Solution;
import com.example.demo.repository.CPURepository;
import com.example.demo.repository.SolutionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CPUService;
import com.example.demo.service.ChassisService;
import com.example.demo.service.GPUService;
import com.example.demo.service.UserService;
import com.example.demo.service.HarddiskService;
import com.example.demo.service.MemoryService;
import com.example.demo.service.PowersupplyService;
import com.example.demo.service.CoolingService;
import com.example.demo.service.DisplayService;
import com.example.demo.service.MotherboardService;
import com.example.demo.service.SolutionService;
import com.example.demo.util.SecurityUtil;
import com.example.demo.util.TokenUtil;
import com.example.demo.vo.SolutionVO;
import com.example.demo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


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

    @Autowired
    CPUService CPUService;

    @Autowired
    GPUService GPUService;

    @Autowired
    ChassisService chassisService;

    @Autowired
    MemoryService memoryService;

    @Autowired
    HarddiskService harddiskService;

    @Autowired
    PowersupplyService powersupplyService;

    @Autowired
    CoolingService coolingService;

    @Autowired
    DisplayService displayService;

    @Autowired
    MotherboardService motherboardService;

    @Autowired
    SolutionService solutionService;

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

        List<SolutionVO> solutionVOs = solutionList.stream()
                .filter(Objects::nonNull) // 过滤掉 null 值
                .map(Solution :: toVO) // 转换为 SolutionVO
                .toList();

        for (SolutionVO solution : solutionVOs) {
            String cpuImage = CPUService.getCPU(solution.getCpuId()).getImageUrl();
            String cpuName = CPUService.getCPU(solution.getCpuId()).getName();
            String gpuImage = GPUService.getGPU(solution.getGpuId()).getImageUrl();
            String gpuName = GPUService.getGPU(solution.getGpuId()).getName();
            String chassisImage = chassisService.getChassis(solution.getChassisId()).getImageUrl();
            String chassisName = chassisService.getChassis(solution.getChassisId()).getName();
            String memoryImage = memoryService.getMemory(solution.getMemoryId()).getImageUrl();
            String memoryName = memoryService.getMemory(solution.getMemoryId()).getName();
            String harddiskImage = harddiskService.getHarddisk(solution.getHarddiskId()).getImageUrl();
            String harddiskName = harddiskService.getHarddisk(solution.getHarddiskId()).getName();
            String powersupplyImage = powersupplyService.getPowersupply(solution.getPowersupplyId()).getImageUrl();
            String powersupplyName = powersupplyService.getPowersupply(solution.getPowersupplyId()).getName();
            String coolingImage = coolingService.getCooling(solution.getCoolingId()).getIma