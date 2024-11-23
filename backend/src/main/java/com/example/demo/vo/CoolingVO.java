package com.example.demo.vo;
import com.example.demo.enums.CoolingBrand;
import com.example.demo.po.Cooling;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:25
 */

@Getter
@Setter
@NoArgsConstructor
public class CoolingVO {
    private Integer id; // 唯一标识符
    private String name; // 散热名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private CoolingBrand brand; // 品牌

    public Cooling toPO() {
        Cooling cooling = new Cooling();
        cooling.setId(id);
        cooling.setName(name);
        cooling.setImageUrl(imageUrl);
        cooling.setPrice(price);
        cooling.setLinkUrl(linkUrl);
        cooling.setBrand(brand);
        return cooling;
    }
}
