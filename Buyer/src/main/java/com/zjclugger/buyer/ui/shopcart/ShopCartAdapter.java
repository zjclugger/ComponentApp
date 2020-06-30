package com.zjclugger.buyer.ui.shopcart;

import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.SoftKeyboardUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车适配器 <br>
 * Created by King.Zi on 2020/4/24.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopCartAdapter extends BaseExpandableListAdapter {
    private List<ShopResult> mGroupList;
    //这个String对应着ShopResult的Id，也就是店铺的Id
    private Map<String, List<GoodsResult>> mChildrenList;
    private Context mContext;
    private CheckInterface mCheckInterface;
    private ModifyCountInterface mModifyCountInterface;
    private GroupEditorListener mGroupEditorListener;
    private int mCount = 0;
    private boolean mEditVisible = true; //组的编辑按钮是否可见，true可见，false不可见

    public ShopCartAdapter(Context context, List<ShopResult> groupList, Map<String,
            List<GoodsResult>> childrenList) {
        this.mGroupList = groupList;
        this.mChildrenList = childrenList;
        this.mContext = context;
    }

    public void setData(List<ShopResult> groupList, Map<String, List<GoodsResult>> childrenList) {
        this.mGroupList = groupList;
        this.mChildrenList = childrenList;
    }


    @Override
    public int getGroupCount() {
        return null == mGroupList ? 0 : mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (null != mGroupList && null != mChildrenList) {
            String groupId = mGroupList.get(groupPosition).getId();
            return mChildrenList.get(groupId).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<GoodsResult> childs = mChildrenList.get(mGroupList.get(groupPosition).getId());
        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shopcat_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final ShopResult group = (ShopResult) getGroup(groupPosition);
        groupViewHolder.storeName.setText(group.getName());
        groupViewHolder.storeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setChoosed(((CheckBox) v).isChecked());
                mCheckInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });
        groupViewHolder.storeCheckBox.setChecked(group.isChoosed());

        /**【文字指的是组的按钮的文字】
         * 当我们按下ActionBar的 "编辑"按钮， 应该把所有组的文字显示"编辑",并且设置按钮为不可见
         * 当我们完成编辑后，再把组的编辑按钮设置为可见
         * 不懂，请自己操作淘宝，观察一遍
         */
        if (group.isActionBarEditor()) {
            group.setEditor(false);
            groupViewHolder.storeEdit.setVisibility(View.GONE);
            mEditVisible = false;
        } else {
            mEditVisible = true;
            groupViewHolder.storeEdit.setVisibility(View.VISIBLE);
        }

        /**
         * 思路:当我们按下组的"编辑"按钮后，组处于编辑状态，文字显示"完成"
         * 当我们点击“完成”按钮后，文字显示"编辑“,组处于未编辑状态
         */
        if (group.isEditor()) {
            groupViewHolder.storeEdit.setText("完成");
        } else {
            groupViewHolder.storeEdit.setText("编辑");
        }

        groupViewHolder.storeEdit.setOnClickListener(new GroupViewClick(group, groupPosition,
                groupViewHolder.storeEdit));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_shopcat_goods, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        /**
         * 根据组的编辑按钮的可见与不可见，去判断是组对下辖的子元素编辑  还是ActionBar对组的下瞎元素的编辑
         * 如果组的编辑按钮可见，那么肯定是组对自己下辖元素的编辑
         * 如果组的编辑按钮不可见，那么肯定是ActionBar对组下辖元素的编辑
         */
        if (mEditVisible) {
            if (mGroupList.get(groupPosition).isEditor()) {
                childViewHolder.editGoodsData.setVisibility(View.VISIBLE);
                childViewHolder.delGoods.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.GONE);
            } else {
                childViewHolder.delGoods.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.VISIBLE);
                childViewHolder.editGoodsData.setVisibility(View.GONE);
            }
        } else {

            if (mGroupList.get(groupPosition).isActionBarEditor()) {
                childViewHolder.delGoods.setVisibility(View.GONE);
                childViewHolder.editGoodsData.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.GONE);
            } else {
                childViewHolder.delGoods.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.VISIBLE);
                childViewHolder.editGoodsData.setVisibility(View.GONE);
            }
        }

        final GoodsResult child = (GoodsResult) getChild(groupPosition, childPosition);
        if (child != null) {
            childViewHolder.goodsName.setText(child.getName());
            childViewHolder.goodsPrice.setText("￥" + child.getPrice() + "");
            childViewHolder.goodsNum.setText(String.valueOf(child.getCount()));

            childViewHolder.goodsImage.setImageResource(R.mipmap.img_picture_default);
            if (!TextUtils.isEmpty(child.getUrl())) {
                ImageUtils.loadImage(mContext, childViewHolder.goodsImage, child.getUrl());
            }

            childViewHolder.goods_size.setText("属性:" + child.getProperties());
            //设置打折前的原价
            SpannableString spannableString = new SpannableString("￥" + child.getPrimePrice());
            StrikethroughSpan span = new StrikethroughSpan();
            spannableString.setSpan(span, 0, spannableString.length() - 1 + 1,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            //避免无限次的append
            if (childViewHolder.goodsPrimePrice.length() > 0) {
                childViewHolder.goodsPrimePrice.setText("");
            }
            childViewHolder.goodsPrimePrice.setText(spannableString);
            childViewHolder.goodsBuyNum.setText("x" + child.getCount() + "");
            childViewHolder.singleCheckBox.setChecked(child.isChecked());
            childViewHolder.singleCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    child.setChecked(((CheckBox) v).isChecked());
                    childViewHolder.singleCheckBox.setChecked(((CheckBox) v).isChecked());
                    mCheckInterface.checkChild(groupPosition, childPosition,
                            ((CheckBox) v).isChecked());
                }
            });
            childViewHolder.increaseGoodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mModifyCountInterface.doIncrease(groupPosition, childPosition,
                            childViewHolder.goodsNum, childViewHolder.singleCheckBox.isChecked());
                }
            });
            childViewHolder.reduceGoodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mModifyCountInterface.doDecrease(groupPosition, childPosition,
                            childViewHolder.goodsNum, childViewHolder.singleCheckBox.isChecked());
                }
            });
            childViewHolder.goodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(groupPosition, childPosition, childViewHolder.goodsNum,
                            childViewHolder.singleCheckBox.isChecked(), child);
                }
            });
            childViewHolder.delGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("确定要删除该商品吗")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mModifyCountInterface.childDelete(groupPosition, childPosition);
                                }
                            })
                            .create()
                            .show();
                }
            });
        }
        return convertView;
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @param showCountView
     * @param isChecked
     * @param child
     */
    private void showDialog(final int groupPosition, final int childPosition,
                            final View showCountView, final boolean isChecked,
                            final GoodsResult child) {
        final AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_change_num, null);
        final AlertDialog dialog = alertDialog_Builder.create();
        dialog.setView(view);//errored,这里是dialog，不是alertDialog_Buidler
        mCount = child.getCount();
        final EditText num = (EditText) view.findViewById(R.id.dialog_num);
        num.setText(mCount + "");
        //自动弹出键盘
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SoftKeyboardUtils.showKeyboard(mContext, showCountView);
            }
        });
        final TextView increase = (TextView) view.findViewById(R.id.dialog_increaseNum);
        final TextView DeIncrease = (TextView) view.findViewById(R.id.dialog_reduceNum);
        final TextView pButton = (TextView) view.findViewById(R.id.dialog_Pbutton);
        final TextView nButton = (TextView) view.findViewById(R.id.dialog_Nbutton);
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(num.getText().toString().trim());
                if (number == 0) {
                    dialog.dismiss();
                } else {
                    num.setText(String.valueOf(number));
                    child.setCount(number);
                    mModifyCountInterface.doUpdate(groupPosition, childPosition, showCountView,
                            isChecked);
                    dialog.dismiss();
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount++;
                num.setText(String.valueOf(mCount));
            }
        });
        DeIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount > 1) {
                    mCount--;
                    num.setText(String.valueOf(mCount));
                }
            }
        });
        dialog.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public GroupEditorListener getGroupEditorListener() {
        return mGroupEditorListener;
    }

    public void setGroupEditorListener(GroupEditorListener mGroupEditorListener) {
        this.mGroupEditorListener = mGroupEditorListener;
    }

    public CheckInterface getCheckInterface() {
        return mCheckInterface;
    }

    public void setCheckInterface(CheckInterface mCheckInterface) {
        this.mCheckInterface = mCheckInterface;
    }

    public ModifyCountInterface getModifyCountInterface() {
        return mModifyCountInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface mModifyCountInterface) {
        this.mModifyCountInterface = mModifyCountInterface;
    }


    static class GroupViewHolder {
        @BindView(R.id.store_checkBox)
        CheckBox storeCheckBox;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.store_edit)
        TextView storeEdit;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 店铺的复选框
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param isChecked     组元素的选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param isChecked     子元素的选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView,
                        boolean isChecked);

        void doDecrease(int groupPosition, int childPosition, View showCountView,
                        boolean isChecked);

        void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删除子Item
         *
         * @param groupPosition
         * @param childPosition
         */
        void childDelete(int groupPosition, int childPosition);
    }

    /**
     * 监听编辑状态
     */
    public interface GroupEditorListener {
        void groupEditor(int groupPosition);
    }

    /**
     * 使某个小组处于编辑状态
     */
    private class GroupViewClick implements View.OnClickListener {
        private ShopResult group;
        private int groupPosition;
        private TextView editor;

        public GroupViewClick(ShopResult group, int groupPosition, TextView editor) {
            this.group = group;
            this.groupPosition = groupPosition;
            this.editor = editor;
        }

        @Override
        public void onClick(View v) {
            if (editor.getId() == v.getId()) {
                mGroupEditorListener.groupEditor(groupPosition);
                if (group.isEditor()) {
                    group.setEditor(false);
                } else {
                    group.setEditor(true);
                }
                notifyDataSetChanged();
            }
        }
    }


    static class ChildViewHolder {
        @BindView(R.id.single_checkBox)
        CheckBox singleCheckBox;
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_pro)
        TextView goods_size;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_prime_price)
        TextView goodsPrimePrice;
        @BindView(R.id.goods_buyNum)
        TextView goodsBuyNum;
        @BindView(R.id.goods_data)
        RelativeLayout goodsData;
        @BindView(R.id.reduce_goodsNum)
        TextView reduceGoodsNum;
        @BindView(R.id.goods_Num)
        TextView goodsNum;
        @BindView(R.id.increase_goods_Num)
        TextView increaseGoodsNum;
        @BindView(R.id.goodsSize)
        TextView goodsSize;
        @BindView(R.id.del_goods)
        TextView delGoods;
        @BindView(R.id.edit_goods_data)
        LinearLayout editGoodsData;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
