package com.example.demo.vo;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 20:58
 */

import com.example.demo.enums.TypeEnum;
import com.example.demo.po.CPU;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CPUVO {
    private Integer id; // 唯一标识符
    private String name; // CPU名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private TypeEnum type; // CPU 类型，和主板的 type 对应


    public CPU toPO() {
        CPU cpu = new CPU();
        cpu.setId(id);
        cpu.setName(name);
        cpu.setImageUrl(imageUrl);
        cpu.setPrice(price);
        cpu.setLinkUrl(linkUrl);
        cpu.setType(type);
        return cpu;
    }
}
