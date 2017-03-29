package com.b1b.js.erpandroid_market.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.b1b.js.erpandroid_market.R;
import com.b1b.js.erpandroid_market.entity.NahuoDan;

import java.util.List;

/**
 Created by 张建宇 on 2017/3/29. */

public class NahuoAdapter extends MyBaseAdapter<NahuoDan> {
    public NahuoAdapter(List<NahuoDan> data, Context mContext, int itemViewId) {
        super(data, mContext, itemViewId);
    }

    @Override
    protected void initItems(View convertView, MyBaseAdapter<NahuoDan>.MyBasedHolder holder) {
        NhHolder nhHolder = (NhHolder) holder;
        nhHolder.tv = (TextView) convertView.findViewById(R.id.simple_tv);
    }

    @Override
    protected void onBindHolder(NahuoDan currentData, MyBaseAdapter.MyBasedHolder mHolder) {
        NhHolder holder = (NhHolder) mHolder;
        holder.tv.setText(currentData.toString());
    }

    @Override
    protected MyBaseAdapter.MyBasedHolder getCustomHolder() {
        return new NhHolder();
    }

    class NhHolder extends MyBaseAdapter.MyBasedHolder {
        TextView tv;
    }
}
