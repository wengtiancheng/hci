package com.example.demo.serviceImpl;

import com.example.demo.repository.DisplayRepository;
import com.example.demo.service.DisplayService;
import com.example.demo.vo.DisplayVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.po.Display;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:22
 */
@Service
public class DisplayServiceImpl implements DisplayService {

    @Autowired
    DisplayRepository displayRepository;

    @Override
    public List<DisplayVO> getAllDisplays() {
        return displayRepository.findAll().stream().map(Display::toVO).toList();
    }

    @Override
    public DisplayVO getDisplay(Integer id) {
        return Objects.requireNonNull(displayRepository.findById(id).orElse(null)).toVO();
    }
}
