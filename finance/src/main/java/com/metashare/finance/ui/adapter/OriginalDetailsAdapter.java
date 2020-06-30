package com.zjclugger.finance.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.finance.databinding.ItemOriginalBillDetailBinding;
import com.zjclugger.finance.webapi.entity.response.OriginalServiceInfoResult;

import java.util.ArrayList;
import java.util.List;

public class OriginalDetailsAdapter extends RecyclerView.Adapter<OriginalDetailsAdapter.OriginalDetailsHolder> {
    private List<OriginalServiceInfoResult> mDetailList = new ArrayList<>();
    private int mItemLayoutResId;

    public OriginalDetailsAdapter(List<OriginalServiceInfoResult> dataList,
                                  @LayoutRes int itemLayoutResId) {
        mDetailList = dataList;
        mItemLayoutResId = itemLayoutResId;
    }

    public static class OriginalDetailsHolder extends RecyclerView.ViewHolder {
        private ItemOriginalBillDetailBinding mBinding;

        public OriginalDetailsHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull OriginalServiceInfoResult result) {
            mBinding.setDetailItem(result);
        }
    }

    @Override
    public OriginalDetailsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayoutResId,
                viewGroup, false);
        return new OriginalDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OriginalDetailsHolder holder, int position) {
        holder.bind(mDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDetailList.size();
    }

    public void setData(List<OriginalServiceInfoResult> dataList) {
        mDetailList = dataList;
        notifyDataSetChanged();
    }
}
