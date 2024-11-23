package com.example.demo.vo;

import com.example.demo.po.Powersupply;

import com.example.demo.enums.PowerSupplyBrand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:17
 */
@Getter
@Setter
@NoArgsConstructor
public class PowersupplyVO {
    private Integer id; // 唯一标识符
    private String name; // 电源名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private PowerSupplyBrand brand; // 品牌

    public Powersupply toPO() {
        Powersupply powersupply = new Powersupply();
        powersupply.setId(id);
        powersupply.setName(name);
        powersupply.setImageUrl(imageUrl);
        powersupply.setPrice(price);
        powersupply.setLinkUrl(linkUrl);
        powersupply.setBrand(brand);
        return powersupply;
    }
}
