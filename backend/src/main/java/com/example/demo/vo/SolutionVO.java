package com.example.demo.vo;

import com.example.demo.po.Solution;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 20:18
 */
@Getter
@Setter
@NoArgsConstructor
public class SolutionVO {
    private Integer id; // 方案id
    private String name; // 方案名称
    private String imageUrl; // 方案图片
    private Integer totalPrice; // 方案总价
    private String description; // 方案描述
    private Integer saveNum; // 收藏数
    private Date createTime; // 创建时间

    // 组件id
    private Integer cpuId;
    private Integer motherboardId;
    private Integer gpuId;
    private Integer memoryId;
    private Integer harddiskId;
    private Integer powersupplyId;
    private Integer coolingId;
    private Integer chassisId;
    private Integer displayId;

    private List<String> images; // 方案图片
    private List<String> componentNames; // 组件名称

    public Solution toPO(){
        Solution solution=new Solution();
        solution.setId(id);
        solution.setName(name);
        solution.setImageUrl(imageUrl);
        solution.setTotalPrice(totalPrice);
        solution.setDescription(description);
        solution.setSaveNum(saveNum);
        solution.setCreateTime(createTime);
        solution.setCpuId(cpuId);
        solution.setMotherboardId(motherboardId);
        solution.setGpuId(gpuId);
        solution.setMemoryId(memoryId);
        solution.setHarddiskId(harddiskId);
        solution.setPowersupplyId(powersupplyId);
        solution.setCoolingId(coolingId);
        solution.setChassisId(chassisId);
        solution.setDisplayId(displayId);
        return solution;
    }
}
