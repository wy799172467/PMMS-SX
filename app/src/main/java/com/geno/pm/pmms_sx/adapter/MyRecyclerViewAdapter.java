package com.geno.pm.pmms_sx.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geno.pm.pmms_sx.Bean.Project_Detail;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.util.Child;
import com.geno.pm.pmms_sx.util.Parent;
import com.geno.pm.pmms_sx.util.Reflect;
import com.geno.pm.pmms_sx.util.SortableField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private Project_Detail mProjectDetail;
    private Field[] mFields;
    private List<SortableField> mInit;
    private HashMap<String, String> mMap;
    private int mWidth;

    public MyRecyclerViewAdapter(Project_Detail projectDetail, int width) {
        mProjectDetail = projectDetail;
        mFields = mProjectDetail.getClass().getDeclaredFields();
        mWidth = width;
        Parent parent = new Child();
        //noinspection unchecked
        mInit = parent.init(); //为javabean中字段排序
        //建立显示映射
        setMap();
    }

    //建立显示映射
    private void setMap() {
        mMap = new HashMap<>();
        mMap.put("ProjectName", "项目名称");
        mMap.put("ProjectType", "计划类别");
        mMap.put("ProjectNo", "项目编号");
        mMap.put("ProjectCategory", "所属类别");
        mMap.put("YearNo", "建设计划年度");
        mMap.put("Invest", "投资金额");
        mMap.put("FundsSourceName", "金额来源");
        mMap.put("Scale", "工程规模");
        mMap.put("StartTime", "计划开工时间");
        mMap.put("FinishTime", "计划竣工设计");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specifics_tablayout_recycler_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Field field = mFields[position];
        Field field = mInit.get(position).getField();
        field.setAccessible(true);
        String key = field.getName();
        String value = null;
        try {
            value = Reflect.fieldReflect(mProjectDetail, field);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        ViewHolder mHolder = (ViewHolder) holder;

//        mHolder.keyView.setText(key);
        mHolder.keyView.setText(mMap.get(key));
        if (value != null) {
            if (value.equals("null") || value.equals("")) {
                mHolder.valueView.setText("暂无数据");
            } else {
                mHolder.valueView.setText(value);
            }
        } else {
            mHolder.valueView.setText("暂无数据");
        }
//        if(mHolder.valueView.getLineCount()>1){
//            mHolder.valueView.setHeight(RecyclerView.LayoutParams.WRAP_CONTENT);
//        }

    }

    @Override
    public int getItemCount() {
        return mFields.length; //序列化之后会多一个字段,处理方式如下
    }

    //持有者类
    private final class ViewHolder extends RecyclerView.ViewHolder {

        TextView keyView;
        TextView valueView;

        ViewHolder(View itemView) {
            super(itemView);
            keyView = (TextView) itemView.findViewById(R.id.specifics_recycler_key);
            valueView = (TextView) itemView.findViewById(R.id.specifics_recycler_value);
            valueView.setWidth(2 * mWidth / 5);
        }
    }
}
