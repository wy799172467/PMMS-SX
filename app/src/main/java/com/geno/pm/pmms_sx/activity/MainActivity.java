package com.geno.pm.pmms_sx.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.geno.pm.pmms_sx.AllData.MainToSpecifics;
import com.geno.pm.pmms_sx.Bean.Information;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.adapter.MyInformationListAdapter;
import com.geno.pm.pmms_sx.adapter.MyListViewAdapter;
import com.geno.pm.pmms_sx.presenter.IMainPresenter;
import com.geno.pm.pmms_sx.presenter.MainPresenter;
import com.geno.pm.pmms_sx.receiver.MyReceiver;
import com.geno.pm.pmms_sx.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements  MyReceiver.InformationIcon,IMainView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.main_linear1)
    LinearLayout mLinearLayout1;
    @Bind(R.id.main_linear2)
    LinearLayout mLinearLayout2;
    @Bind(R.id.main_linear3)
    LinearLayout mLinearLayout3;
    @Bind(R.id.bottom_line)
    View mBottomLine;
    @Bind(R.id.main_load_progressbar)
    ProgressBar mProgressBar;
    @Bind(R.id.main_project_listView)
    ListView mListView;
    @Bind(R.id.ll_popup_hide)
    LinearLayout mLPopupHide;
    @Bind(R.id.window_hide)
    LinearLayout mWindowHide;


    private LayoutInflater mInflater;


    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private ViewGroup informationInflate;
    private PopupWindow informationPopupWindow ;
    private View filterListInflate;
    private PopupWindow filterPopupWindow;
    private ImageView filterImage1;
    private ImageView filterImage2;
    private ImageView filterImage3;


    /*private static final String ACTION1 = "cn.jpush.android.intent.REGISTRATION";
    private static final String ACTION2 = "cn.jpush.android.intent.MESSAGE_RECEIVED";
//    private static final String ACTION2 = "cn.jpush.android.intent.MESSAGE_OPENED";
    private static final String ACTION3 = "cn.jpush.android.intent.NOTIFICATION_RECEIVED";
    private static final String ACTION4 = "cn.jpush.android.intent.NOTIFICATION_OPENED";
    private static final String ACTION5 = "cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK";
    private static final String ACTION6 = "cn.jpush.android.intent.CONNECTION";*/

    @SuppressLint("StaticFieldLeak")
    public static MainActivity mInstance;//用于别的activity操作本activity

    private ImageView icon_information;
    private IMainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mInstance = this;//用于别的activity操作本activity
        mInflater = LayoutInflater.from(this);

        mMainPresenter= MainPresenter.getInstance();
        mMainPresenter.init(this);
        initToolBar();

        mMainPresenter.initFilter();

        mMainPresenter.initAllProject();

//        setBroadcastReceiver();
    }

    @Override
    public void filterDismiss() {
        mLPopupHide.setVisibility(View.INVISIBLE);
        mListView.setEnabled(true);
        filterPopupWindow.dismiss();
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
    @Override
    public void showWaiting() {
        mProgressBar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void hideWaiting() {
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    //初始化导航栏
    @SuppressLint("InflateParams")
    private void initToolBar() {
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
        informationInflate = (ViewGroup) mInflater.inflate(R.layout.information_list, null);
        icon_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection deprecation
                mMainPresenter.showProjectChangeInformation();
            }
        });

        mToolbar.addView(customView);
        setSupportActionBar(mToolbar);
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

    //关闭InformationPopupWindow
    @Override
    public void setInformationPopupWindowCloseListen() {
        informationInflate.findViewById(R.id.information_list_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informationPopupWindow.dismiss();
                mWindowHide.setVisibility(View.INVISIBLE);
                mListView.setEnabled(true);
            }
        });
    }

    //生成InformationPopupWindow
    @Override
    public void showInformationPopupWindow() {
        /*final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);*/
        WindowManager wm = this.getWindowManager();
        //noinspection deprecation
        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
        final PopupWindow popupWindow = new PopupWindow(informationInflate, width - 80,
                1200);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);

        mWindowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                mWindowHide.setVisibility(View.INVISIBLE);
                mListView.setEnabled(true);
            }
        });
        informationPopupWindow=popupWindow;
        //noinspection deprecation
        icon_information.setImageDrawable(getResources().getDrawable(R.drawable.icon_information));
        if (!informationPopupWindow.isShowing()) {
            informationPopupWindow.showAsDropDown(findViewById(R.id.toolbar), 40, 0, Gravity.CENTER);
        }
    }

    //展示消息数据
    @Override
    public void showInformationData(List<Information> information) {
        ListView list = (ListView) informationInflate.findViewById(R.id.information_list);
        MyInformationListAdapter adapter = new MyInformationListAdapter(MainActivity.this, information);
        list.setAdapter(adapter);
    }

    //初始化Filter
    @SuppressLint("InflateParams")
    @Override
    public void initFilterTitle(String[] filter) {
        @SuppressLint("InflateParams")
        ViewGroup view1 = (ViewGroup) mInflater.inflate(R.layout.filter, null);
        mTextView1 = (TextView) view1.findViewById(R.id.filter_text);
        filterImage1 = (ImageView) view1.findViewById(R.id.filter_image);
        mTextView1.setText(filter[0]);
        @SuppressLint("InflateParams")
        ViewGroup view2 = (ViewGroup) mInflater.inflate(R.layout.filter, null);
        mTextView2 = (TextView) view2.findViewById(R.id.filter_text);
        filterImage2 = (ImageView) view2.findViewById(R.id.filter_image);
        mTextView2.setText(filter[1]);
        @SuppressLint("InflateParams")
        ViewGroup view3 = (ViewGroup) mInflater.inflate(R.layout.filter, null);
        mTextView3 = (TextView) view3.findViewById(R.id.filter_text);
        filterImage3 = (ImageView) view3.findViewById(R.id.filter_image);
        mTextView3.setText(filter[2]);
        mLinearLayout1.addView(view1);
        mLinearLayout2.addView(view2);
        mLinearLayout3.addView(view3);
    }

    @Override
    public void setFilterItem(){
        ListView filterList = (ListView) filterListInflate.findViewById(R.id.filter_list);
        filterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filter = (String) ((TextView) view).getText();
                mMainPresenter.setFilterItem(filter);
            }
        });
    }


    @Override
    public boolean isFilterImage2Up() {
        //noinspection ConstantConditions,deprecation
        return filterImage2.getDrawable().getConstantState().
                equals(getResources().getDrawable(R.drawable.icon_up).getConstantState());
    }

    @Override
    public void setFilterTextView1(String filter) {
        mTextView1.setText(filter);
    }

    @Override
    public void setFilterTextView2(String filter) {
        mTextView2.setText(filter);
    }

    @Override
    public void setFilterTextView3(String filter) {
        mTextView3.setText(filter);
    }

    @Override
    public void setFilterImage1Up() {
        filterImage1.setImageResource(R.drawable.icon_up);
    }

    @Override
    public void setFilterImage2Up() {
        filterImage2.setImageResource(R.drawable.icon_up);
    }

    @Override
    public void setFilterImage3Up() {
        filterImage3.setImageResource(R.drawable.icon_up);
    }

    @Override
    public boolean isFilterImage1Up() {
        //noinspection ConstantConditions,deprecation
        return filterImage1.getDrawable().getConstantState().
                equals(getResources().getDrawable(R.drawable.icon_up).getConstantState());
    }

    //设置下拉框的联动监听
    @Override
    public void setLinkListen() {

        mLinearLayout1.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                mMainPresenter.setFilter1LinkListen();
            }
        });

        mLinearLayout2.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                mMainPresenter.setFilter2LinkListen();
            }
        });

        mLinearLayout3.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                mMainPresenter.setFilter3LinkListen();
            }
        });
    }

    @Override
    public void setBackViewAction() {
        mLPopupHide.setVisibility(View.VISIBLE);
        mLPopupHide.getBackground().setAlpha(153);
        mListView.setEnabled(false);
    }

    @Override
    public void showFilterPopWindow() {
        filterPopupWindow.showAsDropDown(mBottomLine);
    }

    @Override
    public boolean isFilterImage3Down() {
        //noinspection ConstantConditions,deprecation
        return filterImage3.getDrawable().getConstantState().
                equals(getResources().getDrawable(R.drawable.icon_drop).getConstantState());
    }

    @Override
    public void setFilterImage1Down() {
        filterImage1.setImageResource(R.drawable.icon_drop);
    }

    @Override
    public void setFilterImage2Down() {
        filterImage2.setImageResource(R.drawable.icon_drop);
    }

    @Override
    public void setFilterImage3Down() {
        filterImage3.setImageResource(R.drawable.icon_drop);
    }

    @Override
    public boolean isFilterImage1Down() {
        //noinspection ConstantConditions,deprecation
        return filterImage1.getDrawable().getConstantState().
                equals(getResources().getDrawable(R.drawable.icon_drop).getConstantState());
    }

    @Override
    public boolean isFilterImage2Down() {
        //noinspection ConstantConditions,deprecation
        return filterImage2.getDrawable().getConstantState().
                equals(getResources().getDrawable(R.drawable.icon_drop).getConstantState());
    }

    @Override
    public boolean isFilterPopupWindowShowing() {
        return filterPopupWindow.isShowing();
    }

    //初始化PopWindow
    @SuppressLint("InflateParams")
    @Override
    public void initFilterPopupWindow() {
        filterListInflate = mInflater.inflate(R.layout.filter_list, null);
        final PopupWindow popupWindow = new PopupWindow(filterListInflate, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        mLPopupHide.setVisibility(View.INVISIBLE);
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
//                mLPopupHide.setVisibility(View.INVISIBLE);
//                findViewById(R.id.listView).setEnabled(true);
//            }
//        });
        filterPopupWindow = popupWindow;
    }

    //设置popupWindow消失
    @Override
    public void setFilterPopupWindowDismiss() {
        mLPopupHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection ConstantConditions,deprecation
                if (filterImage1.getDrawable().getConstantState().
                        equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())) {
                    filterImage1.setImageResource(R.drawable.icon_drop);
                } else //noinspection ConstantConditions,deprecation
                    if (filterImage2.getDrawable().getConstantState().
                            equals(getResources().getDrawable(R.drawable.icon_up).getConstantState())) {
                        filterImage2.setImageResource(R.drawable.icon_drop);
                    } else {
                        filterImage3.setImageResource(R.drawable.icon_drop);
                    }
                filterPopupWindow.dismiss();
                mLPopupHide.setVisibility(View.INVISIBLE);
                mListView.setEnabled(true);
            }
        });
    }

    //装载PopWindow的数据
    @Override
    public void setPopData( String[] data) {
        ListView popListView = (ListView) filterListInflate
                .findViewById(R.id.filter_list);
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.filter_list_item, data);
        adapter.notifyDataSetChanged();
        popListView.setAdapter(adapter);
    }

    @Override
    public void initListView(final List<Project> projects) {
        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(MainActivity.this, projects);
        myListViewAdapter.notifyDataSetChanged();
        mListView.setAdapter(myListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Project project = projects.get(i);
                Intent intent = new Intent(MainActivity.this, SpecificsActivity.class);
                MainToSpecifics.mProjectNo=project.getProjectNo();
                MainToSpecifics.mProjectName=project.getProjectName();
                startActivity(intent);
            }
        });
    }

    @Override
    public void projectCallToast(String message) {
        Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
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
