package com.example.demo.vo;

import com.example.demo.enums.SortType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author simba@onlying.cn
 * @date 2024/11/28 15:03
 */
@Getter
@Setter
@NoArgsConstructor
public class FilterVO {
    Integer lowPrice;
    Integer highPrice;
    SortType sortBy; // 当前选择的排序方式
//    Boolean sortByPrice; // 是否从高价往低排序
//    Boolean sortBySaveNum; // 是否按照收藏数排序
//    Boolean sortByCreateTime; // 是否按照创建时间排序

    String cpuName; // AMD 锐龙 5 7600X // Intel Core i7-12700KF
    String gpuName; // RTX 3080 // RX 6750
    String motherboardName; // B550 // B560
    String memoryName; // DDR4 // DDR5
}
