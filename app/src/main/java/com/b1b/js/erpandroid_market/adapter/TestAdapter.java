package com.b1b.js.erpandroid_market.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.b1b.js.erpandroid_market.R;
import com.b1b.js.erpandroid_market.entity.BijiadanInfo;

import java.util.List;

/**
 Created by 张建宇 on 2017/4/1. */

public class TestAdapter extends MyBaseAdapter2<BijiadanInfo, TestAdapter.MyHolder> {

    public TestAdapter(List<BijiadanInfo> data, Context mContext, int itemViewId) {
        super(data, mContext, itemViewId);
    }

    @Override
    protected void findChildViews(View convertView, TestAdapter.MyHolder holder) {
        holder.tv = (TextView) convertView.findViewById(R.id.simple_tv);
    }

    @Override
    protected void onBindData(BijiadanInfo currentData, TestAdapter.MyHolder mHolder) {

    }

    @Override
    protected MyHolder getCustomHolder() {
        return new MyHolder();
    }

    class MyHolder extends MyBaseAdapter2.MyBasedHolder {
        TextView tv;
    }
}
