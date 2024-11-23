package com.example.demo.vo;

import com.example.demo.enums.ChassisBrand;
import com.example.demo.enums.ChassisType;
import com.example.demo.po.Chassis;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:32
 */
@Getter
@Setter
@NoArgsConstructor
public class ChassisVO {
    private Integer id; // 唯一标识符
    private String name; // 机箱名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private ChassisBrand brand; // 品牌
    private ChassisType type; // 机箱类型

    public Chassis toPO() {
        Chassis chassis = new Chassis();
        chassis.setId(id);
        chassis.setName(name);
        chassis.setImageUrl(imageUrl);
        chassis.setPrice(price);
        chassis.setLinkUrl(linkUrl);
        chassis.setBrand(brand);
        chassis.setType(type);
        return chassis;
    }
}
