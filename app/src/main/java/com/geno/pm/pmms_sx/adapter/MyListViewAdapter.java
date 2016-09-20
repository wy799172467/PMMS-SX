package com.geno.pm.pmms_sx.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.activity.MainActivity;

import java.util.List;
import java.util.Objects;

public class MyListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Project> mProjects;

    public MyListViewAdapter(Context context, List<Project> projects) {
        mInflater = LayoutInflater.from(context);
        mProjects = projects;
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


        final Project project = mProjects.get(i);
        ListViewItem listViewItem;
        if (view == null) {
            view = mInflater.inflate(R.layout.main_project_list_content, null);
//            listViewItem.listView_image=XXX;
            listViewItem = new ListViewItem();
            listViewItem.project_content = (TextView) view.findViewById(R.id.project_content);
            listViewItem.over_time = (ImageView) view.findViewById(R.id.over_time);
            listViewItem.project_status = (TextView) view.findViewById(R.id.project_status);
            listViewItem.bt_enter = (ImageView) view.findViewById(R.id.bt_enter);
            listViewItem.project_status_image = (ImageView) view.findViewById(R.id.project_status_image);
            view.setTag(listViewItem);
        } else {
            listViewItem = (ListViewItem) view.getTag();
        }
        String project_content="";
        if (project.getYearNo() == null || Objects.equals(project.getYearNo(), "")) {
            project_content=project_content+"[XXXX]";
        } else {
            project_content=project_content+"[" + project.getYearNo() + "]";
        }
        listViewItem.project_status.setText(project.getActivityName());
        String projectType = project.getProjectType();
        switch (projectType) {
            case "拟建":
                listViewItem.project_status_image.setImageResource(R.drawable.icon_plan);
                break;
            case "在建":
                listViewItem.project_status_image.setImageResource(R.drawable.icon_construction);
                break;
            default:
                listViewItem.project_status_image.setImageResource(R.drawable.icon_reserve);
                break;
        }
        if (!project.getIsOver()) {
            listViewItem.over_time.setVisibility(View.GONE);
        } else {
            listViewItem.over_time.setVisibility(View.VISIBLE);
        }
        project_content=project_content+project.getProjectName();
        SpannableString ss = new SpannableString(project_content);//定义hint的值
        ss.setSpan(new AbsoluteSizeSpan((int) MainActivity.instance.getResources().getDimension(R.dimen.listView_item_size)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(MainActivity.instance.getResources().getColor(R.color.deep_text)),
                0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        listViewItem.project_content.setText(ss);
        return view;
    }

    public final class ListViewItem {
        public ImageView project_status_image;
        public TextView project_content;
        public ImageView over_time;
        public TextView project_status;
        public ImageView bt_enter;
    }

}
