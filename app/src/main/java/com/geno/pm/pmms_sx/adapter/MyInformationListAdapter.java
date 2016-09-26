package com.geno.pm.pmms_sx.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geno.pm.pmms_sx.Bean.Information;
import com.geno.pm.pmms_sx.R;

import java.util.List;

public class MyInformationListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Information> mInformations;

    public MyInformationListAdapter(Context context, List<Information> informations) {
        mInformations = informations;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mInformations.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Information information = mInformations.get(i);
        ListViewItem listViewItem;
        if (view == null) {
            listViewItem = new ListViewItem();
            view = mInflater.inflate(R.layout.information_list_item, null);
            listViewItem.projectName = (TextView) view.findViewById(R.id.information_list_item_projectName);
            listViewItem.projectNo = (TextView) view.findViewById(R.id.information_list_item_projectNo);
//            listViewItem.projectDetail = (TextView) view.findViewById(R.id.information_list_item_projectDetail);
            view.setTag(listViewItem);
        } else {
            listViewItem = (ListViewItem) view.getTag();
        }
        listViewItem.projectName.setText(information.getProjectName()+":有更新");
        listViewItem.projectNo.setText("项目ID:"+information.getProjectNo());
//        listViewItem.projectDetail.setText(information.getProjectDetail());
        return view;
    }

    private final class ListViewItem {
        TextView projectName;
        TextView projectNo;
//        TextView projectDetail;
    }
}
