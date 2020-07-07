package com.baokaicong.sm.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {
    private String order="id";
    private int sort=0;

    public String orderBy(){
        return this.order+" "+(this.sort==0?"asc":"desc");
    }
}
