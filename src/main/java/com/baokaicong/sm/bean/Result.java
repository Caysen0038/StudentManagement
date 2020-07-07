package com.baokaicong.sm.bean;

import com.baokaicong.sm.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result {
    private String code;
    private String time;
    private Object data;

    public Result generateTime(){
        setTime(DateUtil.getDateTime());
        return this;
    }
}
