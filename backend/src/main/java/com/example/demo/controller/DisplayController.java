package com.example.demo.controller;

import com.example.demo.po.Display;
import com.example.demo.service.DisplayService;
import com.example.demo.vo.DisplayVO;
import com.example.demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:20
 */

@RestController
@RequestMapping("/api/Display")
public class DisplayController {
    @Autowired
    private DisplayService DisplayService;

    /**
     * 获取所有Display
     *
     * @return 所有 Display
     */
    @RequestMapping("/all")
    public ResultVO<List<DisplayVO>> getAllDisplays() {
        return ResultVO.buildSuccess(DisplayService.getAllDisplays());
    }

    /**
     * 获取单个Display
     *
     * @param id Display id
     * @return 单个 Display
     */
    @RequestMapping("/{id}")
    public ResultVO<DisplayVO> getDisplay(@PathVariable(value = "id") Integer id) {
        return ResultVO.buildSuccess(DisplayService.getDisplay(id));
    }
}
