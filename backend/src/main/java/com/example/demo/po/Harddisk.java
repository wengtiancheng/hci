package com.example.demo.po;
import com.example.demo.vo.HarddiskVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.demo.enums.HardDiskType;
import com.example.demo.enums.HardDiskBrandType;
/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:01
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
/**
    private Integer id; // 唯一标识符
    private String name; // 硬盘名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private Integer capacity; // 容量
    private String linkUrl; // 购买链接
    private HardDiskType type; // 硬盘类型
    private HardDiskBrandType brand; // 品牌
 */
public class Harddisk {
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
    @Column(name = "price")
    private Integer price;

    @Basic
    @Column(name = "capacity")
    private Integer capacity;

    @Basic
    @Column(name = "link_url")
    private String linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private HardDiskType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private HardDiskBrandType brand;

    public HarddiskVO toVO() {
        HarddiskVO harddiskVO = new HarddiskVO();
        harddiskVO.setId(id);
        harddiskVO.setName(name);
        harddiskVO.setImageUrl(imageUrl);
        harddiskVO.setPrice(price);
        harddiskVO.setLinkUrl(linkUrl);
        harddiskVO.setType(type);
        harddiskVO.setBrand(brand);
        return harddiskVO;
    }
}
