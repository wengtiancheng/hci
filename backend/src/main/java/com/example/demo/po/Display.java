package com.example.demo.po;
import com.example.demo.enums.DisplayBrand;
import com.example.demo.vo.DisplayVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 * @author simba@onlying.cn
 * @date 2024/11/22 11:41
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
/**
 * 显示屏
    private Integer id; // 唯一标识符
    private String name; // 机箱名称
    private String imageUrl; // 图片链接
    private Integer price; // 价格
    private String linkUrl; // 购买链接
    private DisplayBrand brand; // 品牌
 */
public class Display {
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
    private DisplayBrand brand;

    public DisplayVO toVO() {
        DisplayVO displayVO = new DisplayVO();
        displayVO.setId(id);
        displayVO.setName(name);
        displayVO.setImageUrl(imageUrl);
        displayVO.setPrice(price);
        displayVO.setLinkUrl(linkUrl);
        displayVO.setBrand(brand);
        return displayVO;
    }
}
