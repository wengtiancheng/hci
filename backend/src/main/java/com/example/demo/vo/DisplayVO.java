package com.example.demo.vo;
import com.example.demo.enums.DisplayBrand;
import com.example.demo.po.Display;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:41
 */
@Getter
@Setter
@NoArgsConstructor
public class DisplayVO {
    private Integer id; // 唯一标识符
    private String name; // 机箱名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private DisplayBrand brand; // 品牌
    public Display toPO(){
        Display display = new Display();
        display.setId(id);
        display.setName(name);
        display.setImageUrl(imageUrl);
        display.setPrice(price);
        display.setLinkUrl(linkUrl);
        display.setBrand(brand);
        return display;
    }
}
