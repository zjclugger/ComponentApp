package com.zjclugger.finance.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.finance.databinding.ItemAccountBillDetailBinding;
import com.zjclugger.finance.webapi.entity.response.AccountDetailListResult;

import java.util.ArrayList;
import java.util.List;

public class AccountDetailsAdapter extends RecyclerView.Adapter<AccountDetailsAdapter.DetailsHolder> {
    private List<AccountDetailListResult> mDetailList = new ArrayList<>();
    private int mItemLayoutResId;

    public AccountDetailsAdapter(List<AccountDetailListResult> dataList,
                                 @LayoutRes int itemLayoutResId) {
        mDetailList = dataList;
        mItemLayoutResId = itemLayoutResId;
    }

    public static class DetailsHolder extends RecyclerView.ViewHolder {
        private ItemAccountBillDetailBinding mBinding;

        public DetailsHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull AccountDetailListResult result) {
            mBinding.setDetailItem(result);
        }
    }

    @Override
    public DetailsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayoutResId,
                viewGroup, false);
        return new DetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailsHolder holder, int position) {
        mDetailList.get(position).setItemIndex(position + 1);
        holder.bind(mDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDetailList.size();
    }

    public void setData(List<AccountDetailListResult> dataList) {
        mDetailList = dataList;
        notifyDataSetChanged();
    }
}
