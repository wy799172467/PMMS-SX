package com.geno.pm.pmms_sx.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geno.pm.pmms_sx.Bean.Data;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.util.Reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SettingListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Field[] mFields;
    private Data mData;

    public SettingListViewAdapter(Context context, Data data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mFields = data.getClass().getDeclaredFields();
    }

    @Override
    public int getCount() {
        return mFields.length - 1;//序列化之后会多一个字段,处理方式如下
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Field field = mFields[i];
        field.setAccessible(true);
        String key = field.getName();
        String value = null;
        ViewHolder holder;
        try {
            value = Reflect.fieldReflect(mData, field);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (view == null) {
            view = mInflater.inflate(R.layout.setting_person_list_item, null);
            holder = new ViewHolder();
            holder.key = (TextView) view.findViewById(R.id.setting_person_list_key);
            holder.value = (TextView) view.findViewById(R.id.setting_person_list_value);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (!key.equals("CREATOR")) {
            holder.key.setText(key);
            holder.value.setText(value);
        }
        return view;
    }

    private final class ViewHolder {
        TextView key;
        TextView value;
    }
}
