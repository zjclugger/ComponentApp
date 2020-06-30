
package com.zjclugger.oa.ui.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SectionAttendance extends SectionEntity<ItemAttendance> {
    private boolean isMore;

    public SectionAttendance(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public SectionAttendance(ItemAttendance t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
