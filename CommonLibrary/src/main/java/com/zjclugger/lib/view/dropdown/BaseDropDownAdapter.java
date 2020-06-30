package com.zjclugger.lib.view.dropdown;

import android.widget.BaseAdapter;

public abstract class BaseDropDownAdapter extends BaseAdapter {
    public abstract void setCheckItem(int position);

    public abstract DropDownItem getCheckItem();
}
