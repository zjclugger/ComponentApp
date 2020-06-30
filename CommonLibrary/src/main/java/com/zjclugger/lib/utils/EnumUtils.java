package com.zjclugger.lib.utils;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * 根据key获取枚举对象的value <br>
 * Created by King.Zi on 2020/6/28.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class EnumUtils {

    private static Map<Class, Object> map = new ConcurrentHashMap<>();

    /**
     * 根据条件获取枚举对象
     *
     * @param className 枚举类
     * @param predicate 筛选条件
     * @param <T>
     * @return
     */
    @TargetApi(Build.VERSION_CODES.N)
    public static <T> Optional<T> getEnumObject(Class<T> className, Predicate<T> predicate) {
        if (!className.isEnum()) {
            return null;
        }
        Object obj = map.get(className);
        T[] ts = null;
        if (obj == null) {
            ts = className.getEnumConstants();
            map.put(className, ts);
        } else {
            ts = (T[]) obj;
        }
        return Arrays.stream(ts).filter(predicate).findAny();
    }

/*
调用方法
 @TargetApi(Build.VERSION_CODES.N)
    public static String getErrorCode(int errorCode) {
        Optional<ErrorCode> m1 = EnumUtils.getEnumObject(ErrorCode.class,
                e -> e.getCode() == errorCode);
        return m1.isPresent() ? m1.get().getMsg() : null;
    }

public enum ErrorCode {
    SUCCESS(0, "成功"),
    INVALID_PARAM(1, "无效的参数"),
    NETWORK_UNAVAILABLE(2, "网络不可达"),
    WRONG_PASSWORD(3, "密码错误"),

    private int code;
    private String msg;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}

 */
}
