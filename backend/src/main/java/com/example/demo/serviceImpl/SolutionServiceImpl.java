package com.example.demo.serviceImpl;
import com.example.demo.enums.SortType;
import com.example.demo.exception.DemoException;
import com.example.demo.po.CPU;
import com.example.demo.po.User;
import com.example.demo.repository.*;
import com.example.demo.service.SolutionService;
import com.example.demo.service.UserService;
import com.example.demo.util.SecurityUtil;
import com.example.demo.vo.FilterVO;
import com.example.demo.vo.SolutionVO;

import com.example.demo.po.Solution;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
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

    private Solution SetTotalPrice(Solution solution){
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
        return solution;
    }

    @Override
    public Boolean saveSolution(SolutionVO solutionVO) {
        User user = securityUtil.getCurrentUser();
        if (user == null) throw DemoException.notLogin();
        if (solutionVO == null) throw DemoException.paramError();

        Solution solution = solutionVO.toPO();
        solution = SetTotalPrice(solution);

        List<Integer> solutions = user.getMySolutions();

        if (solutionVO.getId() == null) // 新建方案
        {
            solutionRepository.save(solution);

            solutions.add(solution.getId());
            user.setMySolutions(solutions);
            userRepository.save(user);
        } else {
            Solution solutionPre = solutionRepository.findById(solutionVO.getId()).orElse(null);
            if (solutionPre.getSaveNum() >= 0) // 原方案 是 广场方案
            {
                solution.setId(null);
                solution.setSaveNum(-1);
                solutionRepository.save(solution);

                solutions.add(solution.getId());
                user.setMySolutions(solutions);
                userRepository.save(user);
            }
            else {
                solutionPre.setSaveNum(-1);
                solutionPre.setCpuId(solution.getCpuId());
                solutionPre.setGpuId(solution.getGpuId());
                solutionPre.setMemoryId(solution.getMemoryId());
                solutionPre.setHarddiskId(solution.getHarddiskId());
                solutionPre.setPowersupplyId(solution.getPowersupplyId());
                solutionPre.setCoolingId(solution.getCoolingId());
                solutionPre.setChassisId(solution.getChassisId());
                solutionPre.setDisplayId(solution.getDisplayId());
                solutionPre.setMotherboardId(solution.getMotherboardId());

                solutionRepository.save(solution);
            }
        }

        return true;
    }

    @Override
    public Boolean deleteSolution(Integer id) {

        if (id == null) throw DemoException.paramError();
        User user = securityUtil.getCurrentUser();
        if (user == null) throw DemoException.notLogin();
        List<Integer> solutions = user.getMySolutions();
        if (solutions.contains(id)) {
            solutions.remove(id);
        }
        user.setMySolutions(solutions);
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean starSolution(Integer id) {
//        if (id == null) throw DemoException.paramError();
//        User user = securityUtil.getCurrentUser();
//        if (user == null) throw DemoException.notLogin();
//        List<Integer> solutions = user.getMyStarSolutions();
//        if (solutions.contains(id)) return false;
//        solutions.add(id);
//        user.setMyStarSolutions(solutions);
//        userRepository.save(user);
        return true;
    }

    @Override
    public SolutionVO getSolution(Integer id) {
        if (id == null) throw DemoException.paramError();
        Solution solution = solutionRepository.findById(id).orElseThrow(DemoException::solutionNotExists);
        return solution.toVO();
    }

    private boolean isContain(String name, List<String> Names){
        for (String name1 : Names){
            if (name.contains(name1)) return true;
        }
        return false;
    }
    @Override
    public List<SolutionVO> getAllSolutionsByFilter(FilterVO filterVO) {
        if (filterVO == null) throw DemoException.paramError();
        List<Solution> solutions = solutionRepository.findAll();
        solutions = solutions.stream().filter(solution -> solution.getSaveNum() != -1).toList();
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
        System.out.println(solutions.size());
        // 筛选 solution 的 cpuName 包含 cpuNames 其中一个
        if (!filterVO.getCpuName().isEmpty()) {
            List<String> cpuNames = filterVO.getCpuName();
            solutions = solutions.stream()
                    .filter(solution -> cpuNames.stream()
                            .anyMatch(cpuName -> Objects.requireNonNull(cpuRepository.findById(solution.getCpuId()).orElse(null))
                                    .getName().contains(cpuName)))
                    .toList();
        }
        System.out.println(solutions.size());
        if (!filterVO.getGpuName().isEmpty()) {
            List<String> gpuNames = filterVO.getGpuName();
            solutions = solutions.stream()
                .filter(solution -> gpuNames.stream()
                        .anyMatch(gpuName -> Objects.requireNonNull(gpuRepository.findById(solution.getGpuId()).orElse(null))
                                .getName().contains(gpuName)))
                .toList();
        }
        System.out.println(solutions.size());
        if (!filterVO.getMotherboardName().isEmpty()) {
            List<String> motherboardNames = filterVO.getMotherboardName();
            solutions = solutions.stream()
                .filter(solution -> motherboardNames.stream()
                        .anyMatch(motherboardName -> Objects.requireNonNull(motherboardRepository.findById(solution.getMotherboardId()).orElse(null))
                                .getName().contains(motherboardName)))
                .toList();
        }
        System.out.println(solutions.size());
        if (!filterVO.getMemoryName().isEmpty()) {
            List<String> memoryNames = filterVO.getMemoryName();
            solutions = solutions.stream()
                .filter(solution -> memoryNames.stream()
                        .anyMatch(memoryName -> Objects.requireNonNull(memoryRepository.findById(solution.getMemoryId()).orElse(null))
                                .getName().contains(memoryName)))
                .toList();
        }
        System.out.println(solutions.size());
        return solutions.stream().map(Solution::toVO).toList();
    }

}
