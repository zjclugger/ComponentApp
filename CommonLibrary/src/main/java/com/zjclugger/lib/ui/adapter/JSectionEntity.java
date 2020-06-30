
package com.zjclugger.lib.ui.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.zjclugger.lib.entity.common.JListItem;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class JSectionEntity extends SectionEntity<JListItem> {
    private boolean isMore;
    private boolean isFirst;

    public JSectionEntity(boolean isHeader, String header, boolean isMore, boolean isFirstHeader) {
        super(isHeader, header);
        this.isMore = isMore;
        this.isFirst = isFirstHeader;
    }

    public JSectionEntity(boolean isHeader, String header, boolean isMore) {
        this(isHeader, header, isMore, false);
    }

    public JSectionEntity(boolean isHeader, String header) {
        this(isHeader, header, false, false);
    }

    public JSectionEntity(JListItem t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public boolean isFirstHeader(){
        return isFirst;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
}