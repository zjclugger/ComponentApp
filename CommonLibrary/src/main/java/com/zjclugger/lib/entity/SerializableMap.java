package com.zjclugger.lib.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化Map@title <br>
 * Created by King.Zi on 2020/5/7.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SerializableMap<K, V> implements Serializable {
    private Map<K, V> map;

    public Map<K, V> getMap() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }
}
