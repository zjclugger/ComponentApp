package com.zjclugger.lib.view.dropdown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjclugger.lib.R;

import java.util.List;

public class GirdDropDownAdapter extends BaseDropDownAdapter {

    private Context context;
    private List<DropDownItem> itemList;
    private int checkItemPosition = 0;
    private int tag;

    public GirdDropDownAdapter(Context context, List<DropDownItem> list) {
        this.context = context;
        this.itemList = list;
    }

    public GirdDropDownAdapter(Context context, List<DropDownItem> list,int tag) {
        this.context = context;
        this.itemList = list;
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(itemList.get(position).name);
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        context.getResources().getDrawable(R.mipmap.ic_drop_down_checked), null);
            } else {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    @Override
    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public DropDownItem getCheckItem() {
        if (itemList != null) {
            return itemList.get(checkItemPosition);
        }
        return null;
    }


    static class ViewHolder {
        TextView mText;

        ViewHolder(View view) {
            mText = view.findViewById(R.id.text);
        }
    }
}
