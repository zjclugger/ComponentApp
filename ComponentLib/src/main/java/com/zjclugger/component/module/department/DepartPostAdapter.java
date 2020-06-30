package com.zjclugger.component.module.department;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zjclugger.component.R;

import java.util.HashMap;
import java.util.List;

/**
 * 部门岗位选择<br>
 * Created by King.Zi on 2020/03/10.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class DepartPostAdapter extends BaseAdapter {
    private Context mContext;
    private List<DepartPostEntity> mDataList;
    private String mTopId;
    private String mKeyword = "";
    private HashMap<String, DepartPostEntity> mDataMap = new HashMap<>();

    public DepartPostAdapter(Context context, List<DepartPostEntity> dataList) {
        mContext = context;
        mDataList = dataList;
        for (DepartPostEntity entity : dataList) {
            mDataMap.put(entity.getId(), entity);
        }
    }

    public void setTopId(String topId){
        mTopId = topId;
    }

    /**
     * 搜索的时候，先关闭所有的条目，然后，按照条件，找到含有关键字的数据
     * 如果是叶子节点，
     */
    public void setKeyword(String keyword) {
        this.mKeyword = keyword.toLowerCase();
        for (DepartPostEntity entity : mDataList) {
            entity.setExpand(false);
        }
        if (!TextUtils.isEmpty(mKeyword)) {
            for (DepartPostEntity entity : mDataList) {
                if (entity.getName().toLowerCase().contains(mKeyword)) {
                    if (entity.isNode()) {
                        entity.setExpand(true);
                    }
                    //展开从最顶层到该点的所有节点
                    openExpand(entity);
                }
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 递归展开
     *
     * @param entity
     */
    private void openExpand(DepartPostEntity entity) {
        if (mTopId.equalsIgnoreCase(entity.getParentId())) {
            entity.setExpand(true);
        } else {
            mDataMap.get(entity.getParentId()).setExpand(true);
            openExpand(mDataMap.get(entity.getParentId()));
        }
    }

    //第一要准确计算数量
    @Override
    public int getCount() {
        int count = 0;
        for (DepartPostEntity entity : mDataList) {
            if (mTopId.equals(entity.getParentId())) {
                count++;
            } else {
                if (isItemExpand(entity.getParentId())) {
                    count++;
                }
            }
        }
        return count;
    }

    //判断当前Id的tempPoint是否展开了
    private boolean isItemExpand(String id) {
        for (DepartPostEntity entity : mDataList) {
            if (id.equalsIgnoreCase(entity.getId())) {
                return entity.isExpand();
            }
        }
        return false;
    }

    @Override
    public DepartPostEntity getItem(int position) {
        return mDataList.get(getPosition(position));
    }

    private int getPosition(int position) {
        int count = 0;
        for (int i = 0; i < mDataList.size(); i++) {
            DepartPostEntity entity = mDataList.get(i);
            if (mTopId.equals(entity.getParentId())) {
                count++;
            } else {
                if (isItemExpand(entity.getParentId())) {
                    count++;
                }
            }
            if (position == (count - 1)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_com_depart_post, null);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.center_text);
            holder.leftView = convertView.findViewById(R.id.left_view);
            holder.leftTopLine = convertView.findViewById(R.id.left_top_line);
            holder.leftBottomLine = convertView.findViewById(R.id.left_bottom_line);
            holder.rightView = convertView.findViewById(R.id.right_view);
            holder.treeLayout = convertView.findViewById(R.id.tree_layout);
            holder.bottomLineView = convertView.findViewById(R.id.bottom_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //todo:判断级别，进行图标缩进
        // int level = TreeUtils.getLevel(entity, mDataMap);
        // holder.leftView.setPadding(25 * level, holder.icon.getPaddingTop(), 0, holder.leftView
        // .getPaddingBottom());

        //TODO:绑定值
        final DepartPostEntity entity = getItem(position);
        holder.leftView.setVisibility(View.VISIBLE);
        if (entity.isNode()) {
            //节点
            if (entity.getParentId().equalsIgnoreCase(mTopId)) {
                //一级节点
                holder.leftView.setImageResource(R.mipmap.ic_tree_node);
                if (entity.isExpand()) {
                    //holder.leftView.setImageResource(R.drawable.outline_list_expand);
                    holder.leftTopLine.setVisibility(View.INVISIBLE);
                    holder.leftBottomLine.setVisibility(View.VISIBLE);
                    holder.bottomLineView.setVisibility(View.GONE);
                } else {
                    //holder.leftView.setImageResource(R.drawable.outline_list_collapse);
                    holder.leftTopLine.setVisibility(View.INVISIBLE);
                    holder.leftBottomLine.setVisibility(View.INVISIBLE);
                    holder.bottomLineView.setVisibility(View.VISIBLE);
                }
            } else {
                //非一级节点
                holder.leftView.setImageResource(R.mipmap.ic_circle_smallest);
                holder.leftTopLine.setVisibility(View.VISIBLE);
                if (position + 1 > getCount() || getItem(position + 1).getParentId().equalsIgnoreCase(mTopId)) {
                    holder.leftBottomLine.setVisibility(View.INVISIBLE);
                    holder.bottomLineView.setVisibility(View.VISIBLE);
                } else {
                    holder.leftBottomLine.setVisibility(View.VISIBLE);
                    holder.bottomLineView.setVisibility(View.GONE);
                }
            }

            /*holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "你选择了部门：【" + getItem(position).getName() + "】",
                            Toast.LENGTH_SHORT).show();
                    LiveEventBus.get(HRConstants.Keywords.KEY_DEPART_SELECTED).post(entity);
                }
            });*/

            //rightView:有子部门或岗位才显示
            if (entity.isHasDepartment()) {
                holder.rightView.setVisibility(View.VISIBLE);
                holder.rightView.setImageResource(entity.isExpand() ? R.mipmap.ic_arrow_down :
                        R.mipmap.ic_arrow_right);
                holder.rightView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(position);
                    }
                });
            } else {
                holder.rightView.setVisibility(View.INVISIBLE);
            }
        } else {
            //如果叶子，不占位显示
            holder.leftTopLine.setVisibility(View.VISIBLE);
            //没有下一项或下一项不是同一父ID
            if (position + 1 > getCount() || getItem(position + 1).getParentId().equalsIgnoreCase(mTopId)) {
                holder.leftBottomLine.setVisibility(View.INVISIBLE);
                holder.bottomLineView.setVisibility(View.VISIBLE);
            } else {
                holder.leftBottomLine.setVisibility(View.VISIBLE);
                holder.bottomLineView.setVisibility(View.GONE);
            }
            holder.leftView.setImageResource(R.mipmap.ic_circle_smallest);
            holder.rightView.setVisibility(View.INVISIBLE);

         /*   holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "你选择了岗位：【" + getItem(position).getName() + "】",
                            Toast.LENGTH_SHORT).show();
                    LiveEventBus.get(HRConstants.Keywords.KEY_POST_SELECTED).post(entity);
                }
            });*/
        }

        //如果存在搜索关键字
        if (!TextUtils.isEmpty(mKeyword) && entity.getName().toLowerCase().contains(mKeyword)) {
            int index = entity.getName().toLowerCase().indexOf(mKeyword);
            int len = mKeyword.length();
            Spanned temp = Html.fromHtml(entity.getName().substring(0, index)
                    + "<font color=#FF0000>"
                    + entity.getName().substring(index, index + len) + "</font>"
                    + entity.getName().substring(index + len, entity.getName().length()));

            holder.textView.setText(temp);
        } else {
            holder.textView.setText(entity.getName());
        }
        //holder.textView.setCompoundDrawablePadding(DensityUtil.dip2px(mContext, 10));
        return convertView;
    }

    public void onItemClick(int position) {
        DepartPostEntity entity = getItem(position);
        if (!entity.isNode()) {   //点击叶子节点
            //处理回填
            //Toast.makeText(mcontext, getSubmitResult(entity), Toast.LENGTH_SHORT).show();
        } else {  //如果点击的是父类
            if (entity.isExpand()) {
                for (DepartPostEntity tempEntity : mDataList) {
                    if (tempEntity.getParentId().equalsIgnoreCase(entity.getId())) {
                        if (entity.isNode()) {
                            tempEntity.setExpand(false);
                        }
                    }
                }
                entity.setExpand(false);
            } else {
                entity.setExpand(true);
            }
        }
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        RelativeLayout treeLayout;
        TextView textView;
        ImageView leftView;
        TextView leftTopLine;
        TextView leftBottomLine;
        ImageView rightView;
        TextView bottomLineView;
    }
}