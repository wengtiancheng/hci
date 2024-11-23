package com.example.demo.po;
import com.example.demo.enums.CoolingBrand;
import com.example.demo.vo.CoolingVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:28
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
/**
    private Integer id; // 唯一标识符
    private String name; // 散热名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private CoolingBrand brand; // 品牌
 */
public class Cooling {
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
    private CoolingBrand brand;

    public CoolingVO toVO() {
        CoolingVO coolingVO = new CoolingVO();
        coolingVO.setId(id);
        coolingVO.setName(name);
        coolingVO.setImageUrl(imageUrl);
        coolingVO.setPrice(price);
        coolingVO.setLinkUrl(linkUrl);
        coolingVO.setBrand(brand);
        return coolingVO;
    }
}
