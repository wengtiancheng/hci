package com.example.demo.po;

import com.example.demo.enums.BrandEnum;
import com.example.demo.enums.MemoryType;
import com.example.demo.enums.TypeEnum;
import com.example.demo.vo.MotherboardVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 21:58
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Motherboard {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "memory_type")
    private MemoryType memoryType;

    public MotherboardVO toVO() {
        MotherboardVO motherboardVO = new MotherboardVO();
        motherboardVO.setId(id);
        motherboardVO.setName(name);
        motherboardVO.setImageUrl(imageUrl);
        motherboardVO.setPrice(price);
        motherboardVO.setLinkUrl(linkUrl);
        motherboardVO.setType(type);
        motherboardVO.setBrand(brand);
        motherboardVO.setMemoryType(memoryType);
        return motherboardVO;
    }
}
