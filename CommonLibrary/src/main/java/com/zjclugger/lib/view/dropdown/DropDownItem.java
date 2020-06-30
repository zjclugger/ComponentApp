package com.zjclugger.lib.view.dropdown;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class DropDownItem {
    public String name;
    public String value;
    public int id;

    public DropDownItem(String name, String value) {
        this.name = name;
        this.value = value;
        this.id = -1;
    }

    public DropDownItem(String name, int id) {
        this.name = name;
        this.value = name;
        this.id = id;
    }

    public DropDownItem(String name, String value, int id) {
        this.name = name;
        this.value = value;
        this.id = id;
    }
}
