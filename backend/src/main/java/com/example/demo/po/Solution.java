package com.example.demo.po;

import com.example.demo.vo.SolutionVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 20:18
 */


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Solution {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "image_url")
    private String imageUrl;

    @Basic
    @Column(name = "total_price")
    private Integer totalPrice;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "save_num")
    private Integer saveNum;

    @Basic
    @Column(name = "cpu_id")
    private Integer cpuId;

    @Basic
    @Column(name = "motherboard_id")
    private Integer motherboardId;

    @Basic
    @Column(name = "gpu_id")
    private Integer gpuId;

    @Basic
    @Column(name = "memory_id")
    private Integer memoryId;

    @Basic
    @Column(name = "harddisk_id")
    private Integer harddiskId;

    @Basic
    @Column(name = "powersupply_id")
    private Integer powersupplyId;

    @Basic
    @Column(name = "cooling_id")
    private Integer coolingId;

    @Basic
    @Column(name = "chassis_id")
    private Integer chassisId;

    @Basic
    @Column(name = "display_id")
    private Integer displayId;


    public SolutionVO toVO() {
        SolutionVO solutionVO = new SolutionVO();
        solutionVO.setId(id);
        solutionVO.setName(name);
        solutionVO.setImageUrl(imageUrl);
        solutionVO.setTotalPrice(totalPrice);
        solutionVO.setDescription(description);
        solutionVO.setSaveNum(saveNum);
        solutionVO.setCpuId(cpuId);
        solutionVO.setMotherboardId(motherboardId);
        solutionVO.setGpuId(gpuId);
        solutionVO.setMemoryId(memoryId);
        solutionVO.setHarddiskId(harddiskId);
        solutionVO.setPowersupplyId(powersupplyId);
        solutionVO.setCoolingId(coolingId);
        solutionVO.setChassisId(chassisId);
        solutionVO.setDisplayId(displayId);
        return solutionVO;
    }
}
