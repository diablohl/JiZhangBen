package com.example.hele.jizhangben;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hele on 2017/3/19.
 */

public class CostListAdapter extends BaseAdapter{


    private List<CostBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public CostListAdapter(Context context,List<CostBean> list){
        mContext=context;
        mList=list;
        mLayoutInflater=LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            viewHolder=new ViewHolder();
            convertView=mLayoutInflater.inflate(R.layout.list_item,null);
            viewHolder.mtvCostDate=(TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.mtvCostMoney=(TextView) convertView.findViewById(R.id.tv_cost);
            viewHolder.mtvCostTitle=(TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        CostBean bean=mList.get(position);
        viewHolder.mtvCostTitle.setText(bean.costTitle);
        viewHolder.mtvCostMoney.setText(bean.costMoney);
        viewHolder.mtvCostDate.setText(bean.costDate);
        return convertView;
    }

    private static class ViewHolder{
        public TextView mtvCostTitle;
        public TextView mtvCostDate;
        public TextView mtvCostMoney;


    }





}
