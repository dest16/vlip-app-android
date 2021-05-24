package com.vlip.app.activity.located;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.vlip.app.R;

import java.util.List;


public class LocatedSearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<Tip> mListTips;

    public LocatedSearchAdapter(Context context, List<Tip> tipList) {
        mContext = context;
        mListTips = tipList;
    }

    public void notifyData(List<Tip> tipList) {
        mListTips.clear();
        if (null != tipList && tipList.size() > 0){
            mListTips.addAll(tipList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mListTips != null) {
            return mListTips.size();
        }
        return 0;
    }


    @Override
    public Tip getItem(int i) {
        if (mListTips != null) {
            return mListTips.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_located_search, null);
            holder.mName = (TextView) view.findViewById(R.id.name);
            holder.mAddress = (TextView) view.findViewById(R.id.adress);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        if (mListTips == null) {
            return view;
        }

        holder.mName.setText(mListTips.get(i).getName());
        holder.mAddress.setText(mListTips.get(i).getAddress());
//        String address = mListTips.get(i).getAddress();
//        if (address == null || address.equals("")) {
//            holder.mAddress.setVisibility(View.GONE);
//        } else {
//            holder.mAddress.setVisibility(View.VISIBLE);
//            holder.mAddress.setText(address);
//        }

        return view;
    }

    class Holder {
        TextView mName;
        TextView mAddress;
    }
}