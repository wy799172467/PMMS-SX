package com.geno.pm.pmms_sx.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.util.Reflect;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter{

    private Project mProject;
    private Field[] mFields;

    public MyRecyclerViewAdapter(Project project) {
        mProject=project;
        mFields = mProject.getClass().getDeclaredFields();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specifics_tablayout_view1_recycler, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Field field=mFields[position];
        field.setAccessible(true);
        String key = field.getName();
        String value = null;
        try {
            value = Reflect.fieldReflect(mProject,position);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        ViewHolder mHolder = (ViewHolder) holder;

        mHolder.keyView.setText(key);
        mHolder.valueView.setText(value);

    }

    @Override
    public int getItemCount() {
        return mFields.length-1;//序列化之后会多一个字段,处理方式如下
    }

    //持有者类
    public final class ViewHolder extends RecyclerView.ViewHolder{

        public TextView keyView;
        public TextView valueView;

        public ViewHolder(View itemView) {
            super(itemView);
            keyView= (TextView) itemView.findViewById(R.id.recycler_key);
            valueView= (TextView) itemView.findViewById(R.id.recycler_value);
        }
    }
}
