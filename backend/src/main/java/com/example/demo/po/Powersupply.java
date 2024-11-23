package com.example.demo.po;
import com.example.demo.enums.PowerSupplyBrand;
import com.example.demo.vo.PowersupplyVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
/**
    private Integer id; // 唯一标识符
    private String name; // 电源名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private PowerSupplyBrand brand; // 品牌
 */
public class Powersupply {
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
    private PowerSupplyBrand brand;

    public PowersupplyVO toVO() {
        PowersupplyVO powersupplyVO = new PowersupplyVO();
        powersupplyVO.setId(id);
        powersupplyVO.setName(name);
        powersupplyVO.setImageUrl(imageUrl);
        powersupplyVO.setPrice(price);
        powersupplyVO.setLinkUrl(linkUrl);
        powersupplyVO.setBrand(brand);
        return powersupplyVO;
    }

}
