package com.geno.pm.pmms_sx.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;

import java.util.List;
import java.util.Objects;

public class MyListViewAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<Project> mProjects;

    public MyListViewAdapter(Context context, List<Project> projects) {
        mInflater= LayoutInflater.from(context);
        mProjects=projects;
    }

    @Override
    public int getCount() {
        return mProjects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        final Project project=mProjects.get(i);
        ListViewItem listViewItem;
        if(view==null){
            view= mInflater.inflate(R.layout.listview_content,null);
//            listViewItem.listView_image=XXX;
            listViewItem=new ListViewItem();
            listViewItem.project_content= (TextView) view.findViewById(R.id.project_content);
            listViewItem.project_time= (TextView) view.findViewById(R.id.project_time);
            listViewItem.overTime= (ImageView) view.findViewById(R.id.overTime);
            listViewItem.project_status= (TextView) view.findViewById(R.id.project_status);
            listViewItem.image_button= (ImageView) view.findViewById(R.id.image_button);
            listViewItem.listView_image= (ImageView) view.findViewById(R.id.listView_image);
            view.setTag(listViewItem);
        }else {
            listViewItem= (ListViewItem) view.getTag();
        }

        if (project.getYearNo()==null|| Objects.equals(project.getYearNo(), "")){
            listViewItem.project_time.setText("[XXXX]");
        }else {
            listViewItem.project_time.setText("["+project.getYearNo()+"]");
        }
        listViewItem.project_status.setText(project.getActivityName());
        String projectType=project.getProjectType();
        switch (projectType) {
            case "拟建":
                listViewItem.listView_image.setImageResource(R.drawable.icon_plan);
                break;
            case "在建":
                listViewItem.listView_image.setImageResource(R.drawable.icon_construction);
                break;
            default:
                listViewItem.listView_image.setImageResource(R.drawable.icon_reserve);
                break;
        }
        if(!project.getIsOver()){
            listViewItem.overTime.setVisibility(View.GONE);
        }else {
            listViewItem.overTime.setVisibility(View.VISIBLE);
        }
        listViewItem.project_content.setText(project.getProjectName());
        return view;
    }

    public final class ListViewItem{
        public ImageView listView_image;
        public TextView project_content;
        public TextView project_time;
        public ImageView overTime;
        public TextView project_status;
        public ImageView image_button;
    }

}
