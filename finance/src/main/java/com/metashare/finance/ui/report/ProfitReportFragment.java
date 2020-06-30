package com.zjclugger.finance.ui.report;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.utils.DateTimeFormat;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.zjclugger.finance.R;
import com.zjclugger.finance.R2;
import com.zjclugger.finance.webapi.entity.response.ReportProfitResult;
import com.zjclugger.lib.api.ApiResponseUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 利润表 <br>
 * Created by King.Zi on 2020/1/8.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ProfitReportFragment extends BaseReportFragment {
    @BindView(R2.id.profit_label_date)
    TextView mDateView;
    @BindView(R2.id.profit_label_currency)
    TextView mCurrencyView;
    @BindView(R2.id.profit_table_view)
    SmartTable<ReportProfitResult> mProfitTableView;

    private List<ReportProfitResult> mProfitList = new ArrayList<>();
    private List<String> mRowKeywordList = new ArrayList<>();
    private List<Integer> mRowIndexList = new ArrayList<>();
    private TableData<ReportProfitResult> mTableData;
    private List<Column> mColumnList = new ArrayList<>();

    @Override
    public OnTimeSelectListener getOnTimeSelectListener() {
        return new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mEndDate = DateTimeFormat.parseDateTime(date, VALUE_DATE_FORMAT);
                mDateView.setText(DateTimeFormat.parseDateTime(date, getDateFormat()));
                //todo:读取接口
                getDataFromServer();
            }
        };
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_report_profit;
    }

    @Override
    public void initViews() {
        super.initViews();
        initDetailTitleViews(getString(R.string.report_profit));
        mDateView.setText(DateTimeFormat.parseDateTime(new Date(), getDateFormat()));
        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerView.show();
            }
        });

        //init table view
        mRowKeywordList.add("1");
        mRowKeywordList.add("4");
        mRowKeywordList.add("9");
        mRowKeywordList.add("12");

        FontStyle.setDefaultTextSize(DensityUtils.sp2px(mContext, 12));
        //FontStyle.setDefaultTextColor(getResources().getColor(R.color.black));
        mProfitTableView.getConfig()
                .setVerticalPadding(DensityUtils.dp2px(mContext, 2))
                .setTextLeftOffset(DensityUtils.dp2px(mContext, 5))
                .setShowXSequence(false)
                .setShowYSequence(false)
                .setFixedTitle(true)
                // .setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R
                // .color.light_gray)))
                .setShowTableTitle(false)
                .setContentStyle(new FontStyle().setTextColor(getResources().getColor(R.color.black)))
                .setShowColumnTitle(true);
        //设置单个格子背景颜色
        mProfitTableView.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (hasRowIndex(cellInfo.row)) {
                    return ContextCompat.getColor(mContext, R.color.lighter_gray);
                } else {
                    return TableConfig.INVALID_COLOR;
                }
            }

            //根据背景颜色设置字体颜色
            @Override
            public int getTextColor(CellInfo cellInfo) {
               /* if ("number".equals(cellInfo.column.getFieldName())
                        && cellInfo.row % 2 == 1) {
                    return ContextCompat.getColor(KingTestActivity.this, R.color.white);
                } else {
                    return super.getTextColor(cellInfo);
                }*/
                return super.getTextColor(cellInfo);
            }
        });

        //设置列
        Column<String> colProject = new Column<>(getString(R.string.text_project), "projectName");
        colProject.setTextAlign(Paint.Align.LEFT);
        colProject.setFixed(true);
        //colProject.setWidth(DensityUtils.dp2px(mContext, 220));
        //colProject.setDrawFormat(new MultiLineDrawFormat<String>(mContext, 200));
        Column<String> colNumber = new Column<>(getString(R.string.text_line_number), "number");
        colNumber.setFixed(true);
        Column<String> colAmount = new Column<>(getString(R.string.text_amount), "amount");
        colAmount.setTextAlign(Paint.Align.RIGHT);
        colAmount.setFixed(true);

        mColumnList.add(colNumber);
        mColumnList.add(colProject);
        mColumnList.add(colAmount);
        mTableData = new TableData<>(getString(R.string.report_profit), mProfitList, colNumber, colProject, colAmount);
        mProfitTableView.setTableData(mTableData);
    /*
        //todo:设置合并单元格
        List<CellRange> cellRangeList = new ArrayList<>();
        cellRangeList.add(new CellRange(12, 12, 0, 2));
        tableData.setUserCellRange(cellRangeList);
    */
        getDataFromServer();
    }


    @Override
    public void getDataFromServer() {
        showWaiting();
        mViewModel.getProfitReport(mEndDate).observe(this,
                response -> {
                    closeProgressDialog();
                    if (ApiResponseUtils.isSuccess(response, getString(R.string.error_get_data))) {
                        if (mProfitList != null) {
                            mProfitList.clear();
                        }
                        mProfitList = response.body.getResult();
                        mTableData.setT(mProfitList);
                        mProfitTableView.notifyDataChanged();
                    }
                });
    }

    private boolean hasRowKeyword(String keyword) {
        for (String rowKeyword : mRowKeywordList) {
            if (rowKeyword.equalsIgnoreCase(keyword)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasRowIndex(int rowIndex) {
        for (Integer row : mRowIndexList) {
            if (row.intValue() == rowIndex) {
                return true;
            }
        }
        return false;
    }
}