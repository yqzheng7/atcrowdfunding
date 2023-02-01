package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailReturnVO {

    private Integer returnId;

    private Integer supportMoney;

    private Integer signalPurchase;

    private Integer purchase;

    private Integer supporterCount;

    private Integer freight;

    private Integer returnDate;

    private String content;


}
