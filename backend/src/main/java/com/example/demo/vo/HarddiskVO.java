package com.example.demo.vo;

import com.example.demo.enums.HardDiskBrandType;
import com.example.demo.enums.HardDiskType;
import com.example.demo.po.Harddisk;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:01
 */
@Getter
@Setter
@NoArgsConstructor
public class HarddiskVO {
    private Integer id; // 唯一标识符
    private String name; // 硬盘名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private Integer capacity; // 容量
    private String linkUrl; // 购买链接
    private HardDiskType type; // 硬盘类型
    private HardDiskBrandType brand; // 品牌

    public Harddisk toPO() {
        Harddisk harddisk = new Harddisk();
        harddisk.setId(id);
        harddisk.setName(name);
        harddisk.setImageUrl(imageUrl);
        harddisk.setPrice(price);
        harddisk.setCapacity(capacity);
        harddisk.setLinkUrl(linkUrl);
        harddisk.setType(type);
        harddisk.setBrand(brand);
        return harddisk;
    }

}
