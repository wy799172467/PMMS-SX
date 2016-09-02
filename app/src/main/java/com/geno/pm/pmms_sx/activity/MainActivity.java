package com.geno.pm.pmms_sx.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.geno.pm.pmms_sx.Bean.Data;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.adapter.MyListViewAdapter;
import com.geno.pm.pmms_sx.http.HttpQuery;
import com.geno.pm.pmms_sx.util.Util;

import java.util.List;


public class MainActivity extends AppCompatActivity implements HttpQuery.GetAllProjectCallback {

    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取上个activity的数据
        Intent intent = getIntent();
        data = intent.getParcelableExtra("Data");

        initToolbar();//初始化导航栏
        initSpinner();//初始化Spinner

        HttpQuery.getAllProject(this);//获取数据

    }

    //设置导航栏
    private void initToolbar() {
        ViewGroup customView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.toolbar, null);
        ImageView imageView = (ImageView) customView.findViewById(R.id.mainImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.putExtra("Data", data);
                startActivity(intent);
                finish();
            }
        });

        //设置状态栏透明
        Util.setToolBar(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.addView(customView);
    }

    //初始化Spinner
    private void initSpinner() {
        Spinner all_type = (Spinner) findViewById(R.id.all_type);
        Spinner year_plan = (Spinner) findViewById(R.id.year_plan);
        Spinner project_state = (Spinner) findViewById(R.id.project_state);

        ArrayAdapter<CharSequence> all_type_adapter = ArrayAdapter.createFromResource(this,
                R.array.all_type, android.R.layout.simple_spinner_item);
        all_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        all_type.setAdapter(all_type_adapter);

        ArrayAdapter<CharSequence> year_plan_adapter = ArrayAdapter.createFromResource(this,
                R.array.year_plan, android.R.layout.simple_spinner_item);
        year_plan_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_plan.setAdapter(year_plan_adapter);

        ArrayAdapter<CharSequence> project_state_adapter = ArrayAdapter.createFromResource(this,
                R.array.project_state, android.R.layout.simple_spinner_item);
        project_state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        project_state.setAdapter(project_state_adapter);
    }


    @Override
    public void onGetAllProjectSuccess(final List<Project> projects) {
        initListView(projects);
    }

    private void initListView(final List<Project> projects) {
        ListView listView = (ListView) findViewById(R.id.listView);
        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(MainActivity.this, projects);
        listView.setAdapter(myListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Project project = projects.get(i);
                Intent intent = new Intent(MainActivity.this, SpecificsActivity.class);
                intent.putExtra("Data", project);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onGetAllProjectFail(String message) {

    }
}
