package com.example.demo.po;
import com.example.demo.enums.ChassisBrand;
import com.example.demo.enums.ChassisType;
import com.example.demo.vo.ChassisVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:38
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
/**
    private Integer id; // 唯一标识符
    private String name; // 机箱名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private ChassisBrand brand; // 品牌
    private ChassisType type; // 机箱类型
 */
public class Chassis {
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
    @Column(name = "link_url")
    private String linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private ChassisBrand brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ChassisType type;

    public ChassisVO toVO() {
        ChassisVO chassisVO = new ChassisVO();
        chassisVO.setId(id);
        chassisVO.setName(name);
        chassisVO.setImageUrl(imageUrl);
        chassisVO.setPrice(price);
        chassisVO.setLinkUrl(linkUrl);
        chassisVO.setBrand(brand);
        chassisVO.setType(type);
        return chassisVO;
    }
}
