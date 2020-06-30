package com.zjclugger.oa.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.basiclib.Log;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.CircleImageTextView;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;
import com.zjclugger.oa.R;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 请假类型<br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ApproveAdapter extends BaseQuickAdapter<Staff, ERPBaseViewHolder> {

    private int mMaxCount = 10;
    private List<Staff> mContactList;
    private int mCount;
    private boolean mReadOnly;

    public ApproveAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        mCount = data == null ? 0 : data.size();
        mContactList = data;
    }

    public ApproveAdapter(int layoutResId, List data, int maxCount, boolean readOnly) {
        this(layoutResId, data);
        mMaxCount = maxCount;
        mReadOnly = readOnly;
    }

    private boolean isLastPosition(int position) {
        if (mContactList == null) {
            return true;
        }

        if (mContactList.size() - 1 == position) {
            return true;
        }

        if (mMaxCount - 1 == position) {
            return true;
        }

        return false;
    }

    @Override
    protected void convert(@NonNull final ERPBaseViewHolder holder, final Staff item) {
        Log.d("====position=" + holder.getAdapterPosition() + ",count=" + mCount + ",max count" + mMaxCount);
        CircleImageTextView citView = holder.getView(R.id.approve_view);
        if (holder.getAdapterPosition() >= mMaxCount) {
            citView.setVisibility(View.GONE);
        } else {
            if (Constants.KeyCode.FLAG_ADD_NEW == item.getId()) {
                citView.getAddImageView().setVisibility(View.GONE);
                citView.getDeleteImageView().setVisibility(View.GONE);
                citView.getTextView().setVisibility(View.INVISIBLE);
                citView.setImage(mContext.getResources().getDrawable(R.mipmap.ic_add_more));
                citView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WidgetUtils.toastMessage(mContext, "增加新的抄送人");
                    }
                });
            } else {
                //TODO:获取用户头像,如果有头像则显示头像
                if (isLastPosition(holder.getAdapterPosition())) {
                    citView.getAddImageView().setVisibility(View.GONE);
                }

                if (mReadOnly) {
                    citView.getDeleteImageView().setVisibility(View.GONE);
                }

                citView.getTextView().setText(item.getName());
                try {
                    citView.setCircleImageCenterText(ERPUtils.subString(item.getName(), 4, "GBK",
                            true));
                } catch (UnsupportedEncodingException e) {
                    citView.setCircleImageCenterText(item.getName());
                }
                citView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WidgetUtils.toastMessage(mContext, "打开详情=" + item.getName());
                    }
                });
                citView.getDeleteImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WidgetUtils.toastMessage(mContext, "删除=" + item.getName());
                        mContactList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
