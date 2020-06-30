package com.zjclugger.buyer.ui.im;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.IMResult;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息适配器<br>
 * Created by King.Zi on 2020/5/11.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class MessageAdapter extends BaseAdapter implements Filterable, SlideView.OnSlideListener {
    private static final String TAG = "IMResultAdapter";
    private Context mContext;
    private LayoutInflater mInflater;
    private List<IMResult> mDataList = new ArrayList<>();
    private SlideView mLastSlideViewWithStatusOn;
    private final Object mLock = new Object();
    private ArrayList<IMResult> mOriginalValues;
    private ArrayFilter mFilter;

    public MessageAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDataSource(List<IMResult> IMResultList) {
        this.mDataList = IMResultList;
    }

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mDataList != null) {
            mDataList.get(position).getId();
        }
        return -1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SlideView slideView = (SlideView) convertView;
        if (slideView == null) {
            View itemView = mInflater.inflate(R.layout.item_message_list, null);

            slideView = new SlideView(mContext);
            slideView.setContentView(itemView);

            holder = new ViewHolder(slideView);
            slideView.setOnSlideListener(this);
            slideView.setTag(holder);
        } else {
            holder = (ViewHolder) slideView.getTag();
        }
        IMResult item = mDataList.get(position);
        item.setSlideView(slideView);
        item.getSlideView().shrink();
        if (!TextUtils.isEmpty(item.getHeadImageUrl())) {
            ImageUtils.loadImage(mContext, holder.imageHeadView, item.getHeadImageUrl());
        }
        holder.messageFromView.setText(item.getName());
        holder.messageContentView.setText(item.getMessage());
        holder.dateTimeView.setText(item.getDateTime());
        holder.countView.setText(String.valueOf(item.getNewCount()));

        holder.deleteHolder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (mOnItemDeleteListener != null) {
                    mOnItemDeleteListener.onDelete(mDataList.get(position));
                }*/
                //TODO: 更新本地数据文件
                //notifyDataSetChanged();
                WidgetUtils.toast(mContext, "删除", false);
            }
        });

        return slideView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    static class ViewHolder {
        @BindView(R.id.civ_image_head)
        CircleImageView imageHeadView;
        @BindView(R.id.im_date_time)
        TextView dateTimeView;
        @BindView(R.id.im_count)
        TextView countView;
        @BindView(R.id.message_from)
        TextView messageFromView;
        @BindView(R.id.message_content)
        TextView messageContentView;

        @BindView(R.id.delete_layout)
        ViewGroup deleteHolder;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null
                && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    private class ArrayFilter extends Filter {
        //执行刷选
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();//过滤的结果
            //原始数据备份为空时，上锁，同步复制原始数据
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mDataList);
                }
            }
            //当首字母为空时
            if (prefix == null || prefix.length() == 0) {
                ArrayList<IMResult> list;
                synchronized (mLock) {//同步复制一个原始备份数据
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();//此时返回的results就是原始的数据，不进行过滤
            } else {
                String prefixString = prefix.toString().toLowerCase();//转化为小写

                ArrayList<IMResult> values;
                synchronized (mLock) {//同步复制一个原始备份数据
                    values = new ArrayList<>(mOriginalValues);
                }
                final int count = values.size();
                final ArrayList<IMResult> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final IMResult value = values.get(i);

                    //todo:判断条件
                    //name,content属性作为过滤的参数
                    if (isSearched(prefixString, String.valueOf(value.getName()))) {
                        newValues.add(value);
                    } else if (isSearched(prefixString, String.valueOf(value.getMessage()))) {
                        newValues.add(value);
                    }
                }

                //此时的results就是过滤后的List<>数组
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        private boolean isSearched(String prefixString, String valueText) {
            // First match against the whole, non-splitted value
            if (valueText.startsWith(prefixString) || valueText.indexOf(prefixString.toString()) != -1) {
                //第一个字符是否匹配
                return true;
            } else {
                //处理首字符是空格
                final String[] words = valueText.split(" ");
                final int wordCount = words.length;
                // Start at index 0, in case valueText starts with space(s)
                for (int k = 0; k < wordCount; k++) {
                    if (words[k].startsWith(prefixString)) {
                        //一旦找到匹配的就return，跳出for循环
                        return true;
                    }
                }
            }
            return false;
        }

        //刷选结果
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            //此时，Adapter数据源就是过滤后的Results
            mDataList = (List<IMResult>) results.values;
            if (results.count > 0) {
                //这个相当于从mDatas中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
                notifyDataSetChanged();
            } else {
                //todo: 数据为空时
                /**
                 * 数据容器变化 ----> notifyDataSetInValidated
                 容器中的数据变化  ---->  notifyDataSetChanged
                 */
                notifyDataSetInvalidated();//当results.count<=0时，此时数据源就是重新new出来的，说明原始的数据源已经失效了
            }
        }
    }
}