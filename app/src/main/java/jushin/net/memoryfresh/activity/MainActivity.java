package jushin.net.memoryfresh.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.fragment.GraphFragment;
import jushin.net.memoryfresh.fragment.MainFragment;
import jushin.net.memoryfresh.service.MemoryFreshService;
import jushin.net.memoryfresh.services.MyService01;
import jushin.net.memoryfresh.services.MyService02;
import jushin.net.memoryfresh.services.MyService03;
import jushin.net.memoryfresh.services.MyService04;
import jushin.net.memoryfresh.services.MyService05;
import jushin.net.memoryfresh.services.MyService06;
import jushin.net.memoryfresh.services.MyService07;
import jushin.net.memoryfresh.services.MyService08;
import jushin.net.memoryfresh.services.MyService09;
import jushin.net.memoryfresh.services.MyService10;
import jushin.net.memoryfresh.util.ActivityState;
import jushin.net.memoryfresh.util.MemoryKillerExecuteManager;

/**
 * Created by Yuta on 2016/01.
 */

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    Button startButton;
    private Timer timer;
    private final Handler handler = new Handler();
    private DesignPagerAdapter adapter;
    ActivityState activityState = new ActivityState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.memorybutton);

        setSupportActionBar(toolbar);

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // 起動中のアプリを取得する
//                List<String> runningAppList = new ArrayList<>();
//
//                //チェックされていたアイテムをINSERTする
//                final ArrayList<String> checkedArrayList = new ArrayList<String>();
//
//                final MemoryManager memoryManager = new MemoryManager();
//                runningAppList = memoryManager.getRunningPackageName();
//
//                SharedPreferences pref = getSharedPreferences("check", MODE_PRIVATE);
//                for (String packageName : runningAppList) {
//                    if (! pref.getString(packageName, "").equals("")) {
//                        checkedArrayList.add(packageName);
//                    }
//                }
//
//                new Thread() {
//                    public void run() {
//                        memoryManager.killProcessWithinList(checkedArrayList);
//                    }
//                }.start();


                MemoryKillerExecuteManager exec = new MemoryKillerExecuteManager();
                exec.killExe(MainActivity.this);


            }
        });

//
//        toolbar.inflateMenu(R.menu.menu_main);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//
//                if (id == R.id.action_search) {
//                    Toast.makeText(MainActivity.this,"search click!!",Toast.LENGTH_LONG).show();
//                    return true;
//                }
//
//                return true;
//            }
//        });

        adapter = new DesignPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

//        //データーベースの作成オープン処理
//        ProcessManageDBHelper dbHelper = new ProcessManageDBHelper(getApplicationContext());
//        database = dbHelper.getWritableDatabase();


        //FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab) ;


        //サービスが起動指定で起動していない場合起動させる
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //第二引数は初回起動時にサービスを起動させない設定するためfalseにする
        Boolean isEnableService = preferences.getBoolean("service_switch", false);


        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> listServiceInfo = am.getRunningServices(Integer.MAX_VALUE);
        boolean found = false;
        for (ActivityManager.RunningServiceInfo curr : listServiceInfo) {
            // クラス名を比較
            if (curr.service.getClassName().equals(MemoryFreshService.class.getName())) {
                // 実行中のサービスと一致
                //Toast.makeText(this, "サービス実行中", Toast.LENGTH_LONG).show();
                //final LinearLayout coordinatorLayout = (LinearLayout)findViewById(R.id.linearCoordinator);
                //Snackbar.make(coordinatorLayout,"サービス実行中",Snackbar.LENGTH_SHORT).show();

                // create instance
                Toast toast = new Toast(getApplicationContext());

                // inflate custom view
                View view = getLayoutInflater().inflate(R.layout.toast_view_2, null);

                // set custom view
                toast.setView(view);

                // set duration
                toast.setDuration(Toast.LENGTH_LONG);

                // set position
                int margin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL, 0, margin);

                // show toast
                toast.show();

                found = true;

                break;
            }
        }


        if (found == false && isEnableService) {
            Intent intent = new Intent(MainActivity.this, MemoryFreshService.class);
            startService(intent);
            //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
            //final LinearLayout coordinatorLayout = (LinearLayout)findViewById(R.id.linearCoordinator);
            //Snackbar.make(coordinatorLayout,"サービスが停止していたため起動しました",Snackbar.LENGTH_LONG).show();

            // create instance
            Toast toast = new Toast(getApplicationContext());

            // inflate custom view
            View view = getLayoutInflater().inflate(R.layout.toast_view_1, null);

            // set custom view
            toast.setView(view);

            // set duration
            toast.setDuration(Toast.LENGTH_LONG);

            // set position
            int margin = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL, 0, margin);

            // show toast
            toast.show();

        }

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(intent);
//            }
//        });

    }


    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            //設定画面遷移時はActivityを保持する
            activityState.activityKeep();
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


    //pagerAdapterクラス
    static class DesignPagerAdapter extends FragmentPagerAdapter {

        public DesignPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;

            //case文で分岐
            switch (position) {
                case 0:
                    fragment = GraphFragment.newInstance(position);
                    break;
                case 1:
                    fragment = MainFragment.newInstance(position);
                    break;
                default:
                    fragment = new Fragment();

            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MEMORY";
                case 1:
                    return "TASKS";
                default:
                    return "TAB";
            }
        }


        public void destroyAllItem(ViewPager pager) {
            for (int i = 0; i < getCount() - 1; i++) {
                try {
                    Object obj = this.instantiateItem(pager, i);
                    if (obj != null)
                        destroyItem(pager, i, obj);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //setVisible(fals;e);


    }

    static int flag = 0;

    @Override
    protected void onStart() {
        super.onStart();
        activityState.activityDestruction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState.activityDestruction();


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!activityState.getActivityState()) {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish();
    }
}
