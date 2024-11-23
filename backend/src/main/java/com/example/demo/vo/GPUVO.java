package com.example.demo.vo;

import com.example.demo.enums.BrandEnum;
import com.example.demo.enums.TypeEnum;
import com.example.demo.po.GPU;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:00
 */
@Getter
@Setter
@NoArgsConstructor
public class GPUVO {
    private Integer id; // 唯一标识符
    private String name; // GPU名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private TypeEnum type; // GPU 类型
    private BrandEnum brand; // 品牌

    public GPU toPO() {
        GPU gpu = new GPU();
        gpu.setId(id);
        gpu.setName(name);
        gpu.setImageUrl(imageUrl);
        gpu.setPrice(price);
        gpu.setLinkUrl(linkUrl);
        gpu.setType(type);
        gpu.setBrand(brand);
        return gpu;
    }
}
