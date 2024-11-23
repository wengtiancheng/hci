package com.example.demo.po;

import com.example.demo.vo.GPUVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.demo.enums.BrandEnum;
import com.example.demo.enums.TypeEnum;
import javax.persistence.*;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:02
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class GPU {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private BrandEnum brand;

    public GPUVO toVO() {
        GPUVO gpuVO = new GPUVO();
        gpuVO.setId(id);
        gpuVO.setName(name);
        gpuVO.setImageUrl(imageUrl);
        gpuVO.setPrice(price);
        gpuVO.setLinkUrl(linkUrl);
        gpuVO.setType(type);
        gpuVO.setBrand(brand);
        return gpuVO;
    }

}
