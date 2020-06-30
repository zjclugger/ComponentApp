package com.zjclugger.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 传递自定义对象，需要实现 SerializationService,并使用@Route注解标注(方便用户自行选择序列化方式)<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */

@Route(path = "/service/json")
public class JsonObjectImpl implements SerializationService {

    public JsonObjectImpl() {

    }

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return new Gson().fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return new Gson().toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return new Gson().fromJson(input, clazz);
    }

    @Override
    public void init(Context context) {

    }
}
