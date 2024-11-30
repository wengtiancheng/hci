package com.example.demo.serviceImpl;
import com.example.demo.enums.SortType;
import com.example.demo.exception.DemoException;
import com.example.demo.po.CPU;
import com.example.demo.po.User;
import com.example.demo.repository.*;
import com.example.demo.service.SolutionService;
import com.example.demo.util.SecurityUtil;
import com.example.demo.vo.FilterVO;
import com.example.demo.vo.SolutionVO;

import com.example.demo.po.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private CPURepository cpuRepository;
    @Autowired
    private GPURepository gpuRepository;
    @Autowired
    private MemoryRepository memoryRepository;
    @Autowired
    private HarddiskRepository harddiskRepository;
    @Autowired
    private PowersupplyRepository powersupplyRepository;
    @Autowired
    private ChassisRepository chassisRepository;
    @Autowired
    private CoolingRepository coolingRepository;
    @Autowired
    private DisplayRepository displayRepository;
    @Autowired
    private MotherboardRepository motherboardRepository;

    @Override
    public List<SolutionVO> getAllSolutions() {
        return solutionRepository.findAll().stream().map(Solution::toVO).toList();
    }

    @Override
    public Boolean saveSolution(SolutionVO solutionVO) {
        if (solutionVO == null) throw DemoException.paramError();
        User user = securityUtil.getCurrentUser();
        if (user == null) throw DemoException.notLogin();

        Solution solution = solutionVO.toPO();
        // 计算总价
        Integer totalPrice = 0;
        totalPrice += Objects.requireNonNull(cpuRepository.findById(solution.getCpuId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(gpuRepository.findById(solution.getGpuId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(memoryRepository.findById(solution.getMemoryId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(harddiskRepository.findById(solution.getHarddiskId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(powersupplyRepository.findById(solution.getPowersupplyId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(chassisRepository.findById(solution.getChassisId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(coolingRepository.findById(solution.getCoolingId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(displayRepository.findById(solution.getDisplayId()).orElse(null)).getPrice();
        totalPrice += Objects.requireNonNull(motherboardRepository.findById(solution.getMotherboardId()).orElse(null)).getPrice();
        solution.setTotalPrice(totalPrice);
        // 设置 create_time
        solution.setCreateTime(new Date());
        solution.setSaveNum(0);
        solutionRepository.save(solution);


        List<Integer> solutions = user.getMySolutions();
        solutions.add(solution.getId());
        user.setMySolutions(solutions);
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean starSolution(Integer id) {
        if (id == null) throw DemoException.paramError();
        User user = securityUtil.getCurrentUser();
        if (user == null) throw DemoException.notLogin();
        List<Integer> solutions = user.getMySolutions();
        if (solutions.contains(id)) return false;
        solutions.add(id);
        user.setMySolutions(solutions);
        userRepository.save(user);
        return true;
    }

    @Override
    public SolutionVO getSolution(Integer id) {
        if (id == null) throw DemoException.paramError();
        Solution solution = solutionRepository.findById(id).orElseThrow(DemoException::solutionNotExists);
        return solution.toVO();
    }

    @Override
    public List<SolutionVO> getAllSolutionsByFilter(FilterVO filterVO) {
        if (filterVO == null) throw DemoException.paramError();
        List<Solution> solutions = solutionRepository.findAll();
        if (filterVO.getLowPrice() != null && filterVO.getHighPrice() != null) {
            solutions = solutions.stream()
                .filter(solution -> solution.getTotalPrice() >= filterVO.getLowPrice() && solution.getTotalPrice() <= filterVO.getHighPrice())
                .toList();
        }
        if (filterVO.getSortBy() == SortType.PRICE_DESC) {
            solutions = solutions.stream()
                .sorted(Comparator.comparing(Solution::getTotalPrice).reversed())
                .toList();
        }
        if (filterVO.getSortBy() == SortType.SAVE_DESC) {
            solutions = solutions.stream()
                .sorted(Comparator.comparing(Solution::getSaveNum).reversed())
                .toList();
        }
        if (filterVO.getSortBy() == SortType.CREATE_TIME_DESC) {
            solutions = solutions.stream()
                .sorted(Comparator.comparing(Solution::getCreateTime).reversed())
                .toList();
        }
        if (filterVO.getCpuName() != null) {
            solutions = solutions.stream()
                .filter(solution -> Objects.requireNonNull(cpuRepository.findById(solution.getCpuId()).orElse(null)).getName().contains(filterVO.getCpuName()))
                .toList();
        }
        if (filterVO.getGpuName() != null) {
            solutions = solutions.stream()
                .filter(solution -> Objects.requireNonNull(gpuRepository.findById(solution.getGpuId()).orElse(null)).getName().contains(filterVO.getGpuName()))
                .toList();
        }
        if (filterVO.getMotherboardName() != null) {
            solutions = solutions.stream()
                .filter(solution -> Objects.requireNonNull(motherboardRepository.findById(solution.getMotherboardId()).orElse(null)).getName().contains(filterVO.getMotherboardName()))
                .toList();
        }
        if (filterVO.getMemoryName() != null) {
            solutions = solutions.stream()
                .filter(solution -> Objects.requireNonNull(memoryRepository.findById(solution.getMemoryId()).orElse(null)).getName().contains(filterVO.getMemoryName()))
                .toList();
        }
        return solutions.stream().map(Solution::toVO).toList();
    }


}
