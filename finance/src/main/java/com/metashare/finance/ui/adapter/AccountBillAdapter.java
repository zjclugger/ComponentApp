package com.zjclugger.finance.ui.adapter;

import androidx.annotation.NonNull;

import com.zjclugger.finance.R;
import com.zjclugger.finance.webapi.entity.response.AccountBillResult;
import com.zjclugger.lib.ui.adapter.ERPBaseQuickAdapter;
import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.recyclerview.ERPBaseViewHolder;

import java.util.List;

/**
 * <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class AccountBillAdapter extends ERPBaseQuickAdapter<AccountBillResult,
        ERPBaseViewHolder> {

    public AccountBillAdapter(int layoutResId, List<AccountBillResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull ERPBaseViewHolder holder, AccountBillResult item) {
        holder.setText(R.id.tv_account_title, item.getCredential());
        holder.setValueText(R.id.lvv_account_datetime, ERPUtils.getDate(item.getPeriodDate(),
                mContext.getString(R.string.deafult_null_string)));
        holder.setValueText(R.id.lvv_account_phase, item.getPeriod());
        holder.setText(R.id.value_account_debtor, ERPUtils.getDouble(item.getTotalDebtor(), null));
        holder.setText(R.id.value_account_lender, ERPUtils.getDouble(item.getTotalLender(), null));
    }
}