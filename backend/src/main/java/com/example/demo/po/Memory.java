package com.example.demo.po;

import com.example.demo.enums.MemoryBrand;
import com.example.demo.enums.MemoryType;
import com.example.demo.vo.MemoryVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author simba@onlying.cn
 * @date 2024/11/12 22:17
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Memory {
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
    private MemoryType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private MemoryBrand brand;

    public MemoryVO toVO() {
        MemoryVO memoryVO = new MemoryVO();
        memoryVO.setId(id);
        memoryVO.setName(name);
        memoryVO.setImageUrl(imageUrl);
        memoryVO.setPrice(price);
        memoryVO.setLinkUrl(linkUrl);
        memoryVO.setType(type);
        memoryVO.setBrand(brand);
        return memoryVO;
    }

}
