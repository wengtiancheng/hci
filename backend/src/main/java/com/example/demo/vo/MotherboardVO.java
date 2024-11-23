package com.example.demo.vo;

import com.example.demo.enums.BrandEnum;
import com.example.demo.enums.MemoryType;
import com.example.demo.enums.TypeEnum;
import com.example.demo.po.Motherboard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 21:49
 */
@Getter
@Setter
@NoArgsConstructor
public class MotherboardVO {
    private Integer id; // 唯一标识符
    private String name; // 主板名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private TypeEnum type; // 主板类型
    private BrandEnum brand; // 主板品牌
    private MemoryType memoryType; // 内存接口类型

    public Motherboard toPO() {
        Motherboard motherboard = new Motherboard();
        motherboard.setId(id);
        motherboard.setName(name);
        motherboard.setImageUrl(imageUrl);
        motherboard.setPrice(price);
        motherboard.setLinkUrl(linkUrl);
        motherboard.setType(type);
        motherboard.setBrand(brand);
        motherboard.setMemoryType(memoryType);
        return motherboard;
    }
}
