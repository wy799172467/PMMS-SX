package com.geno.pm.pmms_sx.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.geno.pm.pmms_sx.Bean.Information;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.adapter.MyInformationListAdapter;
import com.geno.pm.pmms_sx.adapter.MyListViewAdapter;
import com.geno.pm.pmms_sx.manager.MainManager;
import com.geno.pm.pmms_sx.receiver.MyReceiver;
import com.geno.pm.pmms_sx.util.Util;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainManager.ProjectResult, MyReceiver.InformationIcon {

    private LayoutInflater mInflater;

    private String YEAR = "all";
    private String STATUS = "all";
    private String TYPE = "all";

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

    private ListView mListView;
    private ProgressBar mProgressBar;

    /*private static final String ACTION1 = "cn.jpush.android.intent.REGISTRATION";
    private static final String ACTION2 = "cn.jpush.android.intent.MESSAGE_RECEIVED";
//    private static final String ACTION2 = "cn.jpush.android.intent.MESSAGE_OPENED";
    private static final String ACTION3 = "cn.jpush.android.intent.NOTIFICATION_RECEIVED";
    private static final String ACTION4 = "cn.jpush.android.intent.NOTIFICATION_OPENED";
    private static final String ACTION5 = "cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK";
    private static final String ACTION6 = "cn.jpush.android.intent.CONNECTION";*/

    @SuppressLint("StaticFieldLeak")
    public static MainActivity mInstance;//用于别的activity操作本activity

    private MainManager mMainManager;
    private ImageView icon_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInstance = this;//用于别的activity操作本activity

        //初始化数据控制器
        mMainManager = MainManager.getInstance();
        mMainManager.init(this);

        mProgressBar = (ProgressBar) findViewById(R.id.main_load_progressbar);
        mListView = (ListView) findViewById(R.id.main_project_listView);

        mInflater = LayoutInflater.from(this);

        initToolbar();//初始化导航栏
        initFilter();//初始化筛选栏

        showWaiting();
        mMainManager.getAllProject(this);//获取数据

//        setBroadcastReceiver();
    }

    /*private void setBroadcastReceiver() {

        BroadcastReceiver myReceiver=new MyReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION1);
        filter.addAction(ACTION2);
        filter.addAction(ACTION3);
        filter.addAction(ACTION4);
        filter.addAction(ACTION5);
        filter.addAction(ACTION6);
        filter.addCategory("com.geno.pm.pmms_sx");
        registerReceiver(myReceiver, filter);
    }*/

    /*public class MyReceiver extends BroadcastReceiver{

        public MyReceiver(){}

        @Override
        public void onReceive(Context context, Intent intent) {
            if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                if (LoginActivity.IsLogin) {
                    Intent i = new Intent(context, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else {
                    Intent i = new Intent(context, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }

            if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Bundle bundle = intent.getExtras();
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                Log.i("JPush--------title", title);
                String message = bundle.getString(JPushInterface.EXTRA_ALERT);
                Log.i("JPush--------message", message);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

                DatabaseHelper database = new DatabaseHelper(context);//这段代码放到Activity类中才用this
                SQLiteDatabase db = database.getWritableDatabase();
                @SuppressLint("Recycle")
                Cursor cursor = db.query("information", null, null, null, null, null, null);//查询并获得游标
                int count = cursor.getCount();
                if (count < 10) {
                    Util.insertData(db, extras);
                } else {
                    Util.deleteFirstLine(db);
                    Util.insertData(db, extras);
                }


                if(icon_information!=null){
                    //noinspection deprecation
                    icon_information.setImageDrawable(getResources().
                            getDrawable(R.drawable.icon_information_new));
                }
                onResume();

            }
        }
    }
*/
    private void showWaiting() {
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    private void hideWaiting() {
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    /*//遍历SharedPreferences中的数据
    private void getFilterData() {
        Map<String, ?> allContent1 = filter_year.getAll();
        int i = 0;
        String[] strings1 = new String[allContent1.entrySet().size()];
        for (Map.Entry<String, ?> entry : allContent1.entrySet()) {
            strings1[i++] = entry.getKey();
        }
        FILTER_YEAR = strings1;

        Map<String, ?> allContent2 = filter_status.getAll();
        int j = 0;
        String[] strings2 = new String[allContent2.entrySet().size()];
        for (Map.Entry<String, ?> entry : allContent2.entrySet()) {
            strings2[j++] = entry.getKey();
        }
        FILTER_STATUS = strings2;

        Map<String, ?> allContent3 = filter_type.getAll();
        int m = 0;
        String[] strings3 = new String[allContent3.entrySet().size()];
        for (Map.Entry<String, ?> entry : allContent3.entrySet()) {
            strings3[m++] = entry.getKey();
        }
        FILTER_TYPE = strings3;

    }*/


    //设置导航栏
    private void initToolbar() {

        //设置状态栏透明
        Util.setToolBarClear(this);

        @SuppressLint("InflateParams")
        ViewGroup customView = (ViewGroup) mInflater.inflate(R.layout.main_toolbar, null);
        ImageView icon_setting = (ImageView) customView.findViewById(R.id.main_toolbar_image);
        icon_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        icon_information = (ImageView) customView.findViewById(R.id.main_icon_information);
        @SuppressLint("InflateParams") final
        ViewGroup inflate = (ViewGroup) mInflater.inflate(R.layout.information_list, null);
        final PopupWindow popupWindow = getInformationPopupWindow(inflate);
        icon_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection deprecation
                icon_information.setImageDrawable(getResources().getDrawable(R.drawable.icon_information));
                if (!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(findViewById(R.id.toolbar), 40, 0, Gravity.CENTER);
                }
                showInformationData(inflate);
                findViewById(R.id.window_hide).setVisibility(View.VISIBLE);
                findViewById(R.id.window_hide).getBackground().setAlpha(153);
                findViewById(R.id.main_project_listView).setEnabled(false);
            }
        });

        closeInformationPopupWindow(inflate, popupWindow);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.addView(customView);
        setSupportActionBar(mToolbar);
    }

    //关闭InformationPopupWindow
    private void closeInformationPopupWindow(ViewGroup inflate, final PopupWindow popupWindow) {
        inflate.findViewById(R.id.information_list_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                findViewById(R.id.window_hide).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_project_listView).setEnabled(true);
            }
        });
    }

    //生成InformationPopupWindow
    @NonNull
    private PopupWindow getInformationPopupWindow(ViewGroup inflate) {
        /*final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);*/
        WindowManager wm = this.getWindowManager();
        //noinspection deprecation
        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
        final PopupWindow popupWindow = new PopupWindow(inflate, width-80,
                1200);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);

        findViewById(R.id.window_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                findViewById(R.id.window_hide).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_project_listView).setEnabled(true);
            }
        });
        return popupWindow;
    }

    //展示消息数据
    private void showInformationData(ViewGroup inflate) {

        List<Information> informations = mMainManager.getInformationData(MainActivity.this);
        ListView list = (ListView) inflate.findViewById(R.id.information_list);
        MyInformationListAdapter adapter = new MyInformationListAdapter(MainActivity.this, informations);
        list.setAdapter(adapter);
    }

    //初始化Filter
    private void initFilter() {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.main_linear1);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.main_linear2);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.main_linear3);
        @SuppressLint("InflateParams")
        ViewGroup view1 = (ViewGroup) mInflater.inflate(R.layout.filter, null);
        mTextView1 = (TextView) view1.findViewById(R.id.filter_text);
        final ImageView image1 = (ImageView) view1.findViewById(R.id.filter_image);
        mTextView1.setText(mMainManager.getFilter()[0]);
        @SuppressLint("InflateParams")
        ViewGroup view2 = (ViewGroup) mInflater.inflate(R.layout.filter, null);
        mTextView2 = (TextView) view2.findViewById(R.id.filter_text);
        final ImageView image2 = (ImageView) view2.findViewById(R.id.filter_image);
        mTextView2.setText(mMainManager.getFilter()[1]);
        @SuppressLint("InflateParams")
        ViewGroup view3 = (ViewGroup) mInflater.inflate(R.layout.filter, null);
        mTextView3 = (TextView) view3.findViewById(R.id.filter_text);
        final ImageView image3 = (ImageView) view3.findViewById(R.id.filter_image);
        mTextView3.setText(mMainManager.getFilter()[2]);
        linearLayout1.addView(view1);
        linearLayout2.addView(view2);
        linearLayout3.addView(view3);

        @SuppressLint("InflateParams")
        View inflate = mInflater.inflate(R.layout.filter_list, null);
        final PopupWindow popupWindow = initPopupWindow(inflate, image1, image2, image3);
        //设置下拉框的联动监听
        setLinkListen(linearLayout1, linearLayout2, linearLayout3, image1, image2, image3, popupWindow, inflate);
        //获取筛选条件
        setFilterItem(image1, image2, image3, inflate, popupWindow);
    }

    //获取筛选条件
    private void setFilterItem(final ImageView image1,
                               final ImageView image2,
                               final ImageView image3,
                               View inflate,
                               final PopupWindow popupWindow) {
        ListView filter_list = (ListView) inflate.findViewById(R.id.filter_list);
        filter_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filter = (String) ((TextView) view).getText();
                //noinspection ConstantConditions,deprecation
                if (image1.getDrawable().getConstantState().
                        equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())) {
                    if (filter.equals("全部类型")) {
                        TYPE = "all";
                        mTextView1.setText(mMainManager.getFilter()[0]);
                    } else {
                        TYPE = mMainManager.getFilterTypeSharePreferences().getString(filter, "");
                        mTextView1.setText(filter);
                    }
                    image1.setImageResource(R.drawable.icon_drop);
                    showWaiting();
                    mMainManager.getFilterProjects(TYPE, YEAR, STATUS, MainActivity.this);
                } else //noinspection ConstantConditions,deprecation
                    if (image2.getDrawable().getConstantState().
                            equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())) {
                        if (filter.equals("全部年度")) {
                            YEAR = "all";
                            mTextView2.setText(mMainManager.getFilter()[1]);
                        } else {
                            YEAR = mMainManager.getFilterYearSharePreferences().getString(filter, "");
                            mTextView2.setText(filter);
                        }
                        image2.setImageResource(R.drawable.icon_drop);
                        showWaiting();
                        mMainManager.getFilterProjects(TYPE, YEAR, STATUS, MainActivity.this);
                    } else {//noinspection ConstantConditions,deprecation
                        if (filter.equals("全部状态")) {
                            STATUS = "all";
                            mTextView3.setText(mMainManager.getFilter()[2]);
                        } else {
                            STATUS = mMainManager.getFilterStatusSharePreferences().getString(filter, "");
                            mTextView3.setText(filter);
                        }
                        image3.setImageResource(R.drawable.icon_drop);
                        showWaiting();
                        mMainManager.getFilterProjects(TYPE, YEAR, STATUS, MainActivity.this);
                    }
                findViewById(R.id.ll_popup_hide).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_project_listView).setEnabled(true);
                popupWindow.dismiss();
            }
        });
    }

    //设置下拉框的联动监听
    private void setLinkListen(LinearLayout linearLayout1,
                               LinearLayout linearLayout2,
                               LinearLayout linearLayout3,
                               final ImageView image1,
                               final ImageView image2,
                               final ImageView image3,
                               final PopupWindow popupWindow,
                               final View inflate) {


        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {

                if (popupWindow.isShowing()) {
                    //noinspection ConstantConditions
                    if (image1.getDrawable().getConstantState().
                            equals(getResources().getDrawable(R.drawable.icon_drop).getConstantState())) {
                        setPopData(inflate, mMainManager.getFILTER_TYPE());
                        image1.setImageResource(R.drawable.icon_up);
                        //noinspection ConstantConditions
                        if (image2.getDrawable().getConstantState().equals(getResources().
                                getDrawable(R.drawable.icon_up).getConstantState())) {
                            image2.setImageResource(R.drawable.icon_drop);
                        } else {
                            image3.setImageResource(R.drawable.icon_drop);
                        }
                    } else {
                        findViewById(R.id.ll_popup_hide).setVisibility(View.INVISIBLE);
                        findViewById(R.id.main_project_listView).setEnabled(true);
                        popupWindow.dismiss();
                        image1.setImageResource(R.drawable.icon_drop);
                    }
                } else {
                    setPopData(inflate, mMainManager.getFILTER_TYPE());
                    popupWindow.showAsDropDown(findViewById(R.id.bottom_line));
                    findViewById(R.id.ll_popup_hide).setVisibility(View.VISIBLE);
                    findViewById(R.id.main_project_listView).setEnabled(false);
                    findViewById(R.id.ll_popup_hide).getBackground().setAlpha(153);
                    image1.setImageResource(R.drawable.icon_up);
                    //方法二
                /*WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=0.5f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(params);*/
                    //方法三
                /*ListView listView = (ListView) findViewById(R.id.listView);
                listView.getBackground().setAlpha(255);
                listView.setBackgroundColor(getResources().getColor(R.color.main_listView_item_project_status));*/
                }
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {

                if (popupWindow.isShowing()) {
                    //noinspection ConstantConditions,ConstantConditions
                    if (image2.getDrawable().getConstantState().
                            equals(getResources().getDrawable(R.drawable.icon_drop).getConstantState())) {
                        setPopData(inflate, mMainManager.getFILTER_YEAR());
                        image2.setImageResource(R.drawable.icon_up);
                        //noinspection ConstantConditions
                        if (image1.getDrawable().getConstantState().equals(getResources().
                                getDrawable(R.drawable.icon_up).getConstantState())) {
                            image1.setImageResource(R.drawable.icon_drop);
                        } else {
                            image3.setImageResource(R.drawable.icon_drop);
                        }
                    } else {
                        findViewById(R.id.ll_popup_hide).setVisibility(View.INVISIBLE);
                        findViewById(R.id.main_project_listView).setEnabled(true);
                        popupWindow.dismiss();
                        image2.setImageResource(R.drawable.icon_drop);
                    }
                } else {
                    setPopData(inflate, mMainManager.getFILTER_YEAR());
                    popupWindow.showAsDropDown(findViewById(R.id.bottom_line));
                    findViewById(R.id.ll_popup_hide).setVisibility(View.VISIBLE);
                    findViewById(R.id.main_project_listView).setEnabled(false);
                    findViewById(R.id.ll_popup_hide).getBackground().setAlpha(153);
                    image2.setImageResource(R.drawable.icon_up);
                }
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {

                if (popupWindow.isShowing()) {
                    //noinspection ConstantConditions
                    if (image3.getDrawable().getConstantState().
                            equals(getResources().getDrawable(R.drawable.icon_drop).getConstantState())) {
                        setPopData(inflate, mMainManager.getFILTER_STATUS());
                        image3.setImageResource(R.drawable.icon_up);
                        //noinspection ConstantConditions
                        if (image1.getDrawable().getConstantState().equals(getResources().
                                getDrawable(R.drawable.icon_up).getConstantState())) {
                            image1.setImageResource(R.drawable.icon_drop);
                        } else {
                            image2.setImageResource(R.drawable.icon_drop);
                        }
                    } else {
                        findViewById(R.id.ll_popup_hide).setVisibility(View.INVISIBLE);
                        findViewById(R.id.main_project_listView).setEnabled(true);
                        popupWindow.dismiss();
                        image3.setImageResource(R.drawable.icon_drop);
                    }
                } else {
                    setPopData(inflate, mMainManager.getFILTER_STATUS());
                    popupWindow.showAsDropDown(findViewById(R.id.bottom_line));
                    findViewById(R.id.ll_popup_hide).setVisibility(View.VISIBLE);
                    findViewById(R.id.main_project_listView).setEnabled(false);
                    findViewById(R.id.ll_popup_hide).getBackground().setAlpha(153);
                    image3.setImageResource(R.drawable.icon_up);
                }
            }
        });
    }

    //初始化PopWindow
    @NonNull
    private PopupWindow initPopupWindow(final View inflate,
                                        final ImageView image1,
                                        final ImageView image2,
                                        final ImageView image3) {
        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        findViewById(R.id.ll_popup_hide).setVisibility(View.INVISIBLE);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                if(image1.getDrawable().getConstantState().
//                        equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())){
//                    image1.setImageResource(R.drawable.icon_drop);
//                }else if (image2.getDrawable().getConstantState().
//                        equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())){
//                    image2.setImageResource(R.drawable.icon_drop);
//                }else {
//                    image3.setImageResource(R.drawable.icon_drop);
//                }
//                popupWindow.dismiss();
//                findViewById(R.id.ll_popup_hide).setVisibility(View.INVISIBLE);
//                findViewById(R.id.listView).setEnabled(true);
//            }
//        });
        popupWindowDismiss(image1, image2, image3, popupWindow);//设置popupWindow消失
        return popupWindow;
    }

    //设置popupWindow消失
    private void popupWindowDismiss(final ImageView image1, final ImageView image2, final ImageView image3, final PopupWindow popupWindow) {
        findViewById(R.id.ll_popup_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection ConstantConditions,deprecation
                if (image1.getDrawable().getConstantState().
                        equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())) {
                    image1.setImageResource(R.drawable.icon_drop);
                } else //noinspection ConstantConditions,deprecation
                    if (image2.getDrawable().getConstantState().
                            equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())) {
                        image2.setImageResource(R.drawable.icon_drop);
                    } else {
                        image3.setImageResource(R.drawable.icon_drop);
                    }
                popupWindow.dismiss();
                findViewById(R.id.ll_popup_hide).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_project_listView).setEnabled(true);
            }
        });
    }

    //装载PopWindow的数据
    private void setPopData(View inflate, String[] data) {
        ListView popListView = (ListView) inflate
                .findViewById(R.id.filter_list);
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.filter_list_item, data);
        adapter.notifyDataSetChanged();
        popListView.setAdapter(adapter);
    }

    private void initListView(final List<Project> projects) {
        hideWaiting();
        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(MainActivity.this, projects);
        myListViewAdapter.notifyDataSetChanged();
        mListView.setAdapter(myListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Project project = projects.get(i);
                Intent intent = new Intent(MainActivity.this, SpecificsActivity.class);
                intent.putExtra("ProjectNo", project.getProjectNo());
                intent.putExtra("ProjectName", project.getProjectName());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onProjectSuccess(List<Project> projects) {
        if (projects.size() == 0) {
            Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
        }
        initListView(projects);
    }

    @Override
    public void onProjectFailed() {

    }

    @Override
    public void setInformationIcon() {
        if (icon_information != null) {
            //noinspection deprecation
            icon_information.setImageDrawable(getResources().
                    getDrawable(R.drawable.icon_information_new));
        }
        onResume();
    }
}
