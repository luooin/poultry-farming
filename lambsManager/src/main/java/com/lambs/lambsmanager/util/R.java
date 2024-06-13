package com.lambs.lambsmanager.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    /**
     * 成功
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    /**
     * 成功的多态
     *
     * @param object
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> R<T> success(T object, String msg) {
        R<T> r = new R<T>();
        r.data = object;
        r.msg = msg;
        r.code = 1;
        return r;
    }

    /**
     * 失败
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
}
