package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author simba@onlying.cn
 * @date 2024/10/19 22:39
 */


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultVO<T> implements Serializable {

    private String code;

    private String msg;

    private T result;

    public static <T> ResultVO<T> buildSuccess(T result) {
        return new ResultVO<T>("000", null, result);
    }

    public static <T> ResultVO<T> buildFailure(String msg) {
        return new ResultVO<T>("400", msg, null);
    }
}
