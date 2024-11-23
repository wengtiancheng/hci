package com.example.demo.po;

import com.example.demo.vo.CPUVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo.enums.TypeEnum;
import javax.persistence.*;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 21:46
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
/**
 *     private Integer id; // 唯一标识符
 *     private String name; // CPU名称
 *     private String imageUrl; // 图片链接
 *     private Integer price; // 价格
 *     private String linkUrl; // 购买链接
 *     private TypeEnum type; // CPU 类型
 */
public class CPU {
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
    @Column(name = "type")
    private TypeEnum type;

    public CPUVO toVO() {
        CPUVO cpuVO = new CPUVO();
        cpuVO.setId(id);
        cpuVO.setName(name);
        cpuVO.setImageUrl(imageUrl);
        cpuVO.setPrice(price);
        cpuVO.setLinkUrl(linkUrl);
        cpuVO.setType(type);
        return cpuVO;
    }
}
