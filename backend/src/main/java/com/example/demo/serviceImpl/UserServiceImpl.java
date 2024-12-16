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

import java.util.Comparator;
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
        if (!user.getPassword().equals(password)) throw DemoException.phoneOrPasswordError();
        return tokenUtil.getToken(user);
    }

    @Override
    public UserVO getInformation() {
        User user = securityUtil.getCurrentUser();
        return user.toVO();
    }

    @Override
    public List<SolutionVO> getMySolutions() {
        User user = securityUtil.getCurrentUser();
        List<Integer> solutions = user.getMySolutions();
        if (solutions == null) throw DemoException.mySolutionNotExists();

        List<Solution> solutionList = solutions.stream()
                .map(solutionId -> solutionRepository.findById(solutionId).orElse(null))
                .toList();

        List<SolutionVO> solutionVOs = new java.util.ArrayList<>(solutionList.stream()
                .filter(Objects::nonNull) // 过滤掉 null 值
                .map(Solution::toVO) // 转换为 SolutionVO
                .toList());

        // 按创建时间降序
        solutionVOs.sort(Comparator.comparing(SolutionVO::getCreateTime).reversed());


        for (SolutionVO solution : solutionVOs) {
            List<String> images = new java.util.ArrayList<>();
            List<String> componentNames = new java.util.ArrayList<>();
            if (solution.getCpuId() != null && solution.getCpuId() != 0) {
                String cpuImage = CPUService.getCPU(solution.getCpuId()).getImageUrl();
                String cpuName = CPUService.getCPU(solution.getCpuId()).getName();
                images.add(cpuImage);
                componentNames.add(cpuName);
            }
            if (solution.getGpuId() != null && solution.getGpuId() != 0) {
                String gpuImage = GPUService.getGPU(solution.getGpuId()).getImageUrl();
                String gpuName = GPUService.getGPU(solution.getGpuId()).getName();
                images.add(gpuImage);
                componentNames.add(gpuName);
            }
            if (solution.getChassisId() != null && solution.getChassisId() != 0) {
                String chassisImage = chassisService.getChassis(solution.getChassisId()).getImageUrl();
                String chassisName = chassisService.getChassis(solution.getChassisId()).getName();
                images.add(chassisImage);
                componentNames.add(chassisName);
            }
            if (solution.getMemoryId() != null && solution.getMemoryId() != 0) {
                String memoryImage = memoryService.getMemory(solution.getMemoryId()).getImageUrl();
                String memoryName = memoryService.getMemory(solution.getMemoryId()).getName();
                images.add(memoryImage);
                componentNames.add(memoryName);
            }
            if (solution.getHarddiskId() != null && solution.getHarddiskId() != 0) {
                String harddiskImage = harddiskService.getHarddisk(solution.getHarddiskId()).getImageUrl();
                String harddiskName = harddiskService.getHarddisk(solution.getHarddiskId()).getName();
                images.add(harddiskImage);
                componentNames.add(harddiskName);
            }
            if (solution.getPowersupplyId() != null && solution.getPowersupplyId() != 0) {
                String powersupplyImage = powersupplyService.getPowersupply(solution.getPowersupplyId()).getImageUrl();
                String powersupplyName = powersupplyService.getPowersupply(solution.getPowersupplyId()).getName();
                images.add(powersupplyImage);
                componentNames.add(powersupplyName);
            }
            if (solution.getCoolingId() != null && solution.getCoolingId() != 0) {
                String coolingImage = coolingService.getCooling(solution.getCoolingId()).getImageUrl();
                String coolingName = coolingService.getCooling(solution.getCoolingId()).getName();
                images.add(coolingImage);
                componentNames.add(coolingName);
            }
            if (solution.getDisplayId() != null && solution.getDisplayId() != 0) {
                String displayImage = displayService.getDisplay(solution.getDisplayId()).getImageUrl();
                String displayName = displayService.getDisplay(solution.getDisplayId()).getName();
                images.add(displayImage);
                componentNames.add(displayName);
            }
            if (solution.getMotherboardId() != null && solution.getMotherboardId() != 0) {
                String motherboardImage = motherboardService.getMotherboard(solution.getMotherboardId()).getImageUrl();
                String motherboardName = motherboardService.getMotherboard(solution.getMotherboardId()).getName();
                images.add(motherboardImage);
                componentNames.add(motherboardName);
            }
            solution.setImages(images);
            solution.setComponentNames(componentNames);
        }
        return solutionVOs;
    }
}