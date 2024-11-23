package com.example.demo.vo;

import com.example.demo.enums.MemoryBrand;
import com.example.demo.enums.MemoryType;
import com.example.demo.po.Memory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:06
 */
@Getter
@Setter
@NoArgsConstructor
public class MemoryVO {
    private Integer id; // 唯一标识符
    private String name; // 内存名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private MemoryType type; // 内存类型， DDR3/DDR4/DDR5，和 MemoryBoard 的 memoryType 对应
    private MemoryBrand brand; // 品牌

    public Memory toPO() {
        Memory memory = new Memory();
        memory.setId(id);
        memory.setName(name);
        memory.setImageUrl(imageUrl);
        memory.setPrice(price);
        memory.setLinkUrl(linkUrl);
        memory.setType(type);
        memory.setBrand(brand);
        return memory;
    }
}
