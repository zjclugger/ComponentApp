package com.zjclugger.finance.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.finance.databinding.ItemOriginalBillDetailEditBinding;
import com.zjclugger.finance.webapi.entity.response.OriginalServiceInfoResult;
import com.zjclugger.lib.utils.Monitor;
import com.zjclugger.lib.utils.ViewUtils;

import java.util.List;

public class OriginalDetailsEditAdapter extends RecyclerView.Adapter<OriginalDetailsEditAdapter.OriginalDetailsHolder> {
    private List<OriginalServiceInfoResult> mDetailList;
    private int mItemLayoutResId;
    private Monitor.OnItemClickListener mOnItemClickListener;

    public OriginalDetailsEditAdapter(List<OriginalServiceInfoResult> dataList,
                                      @LayoutRes int itemLayoutResId) {
        mDetailList = dataList;
        mItemLayoutResId = itemLayoutResId;
    }

    public class OriginalDetailsHolder extends RecyclerView.ViewHolder {
        private ItemOriginalBillDetailEditBinding mBinding;
        View mView;
        int mPosition;

        public OriginalDetailsHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mBinding = DataBindingUtil.bind(itemView);
            ViewUtils.setEditTextDigits(mBinding.letvUnitPrice.getEditTextView());
            ViewUtils.setEditTextDigits(mBinding.letvTaxAmount.getEditTextView());
            ViewUtils.setEditTextDigits(mBinding.letvAmount.getEditTextView());
        }

        public void bind(int position) {
            mPosition = position;
            mBinding.setDetailItem(mDetailList.get(position));
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(OriginalDetailsEditAdapter.this, mView,
                                mPosition);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(Monitor.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public OriginalDetailsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayoutResId,
                viewGroup, false);
        return new OriginalDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OriginalDetailsHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDetailList.size();
    }
}