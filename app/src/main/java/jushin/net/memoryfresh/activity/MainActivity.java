package jushin.net.memoryfresh.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.database.ProcessManageDBHelper;
import jushin.net.memoryfresh.fragment.GraphFragment;
import jushin.net.memoryfresh.fragment.MainFragment;
import jushin.net.memoryfresh.memory.MemoryManager;
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

/**
 * Created by Yuta on 2016/01.
 */

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    Button startButton;
    private Timer timer;
    private final Handler handler = new Handler();
    Intent intent;
    Intent intent1;
    Intent intent2;
    Intent intent3;
    Intent intent4;
    Intent intent5;
    Intent intent6;
    Intent intent7;
    Intent intent8;
    Intent intent9;
    private DesignPagerAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.memorybutton);
        toolbar.setNavigationIcon(R.drawable.memorybutton);

        setSupportActionBar(toolbar);

        startButton = (Button)findViewById(R.id.start_button);
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

                intent = new Intent(MainActivity.this, MyService01.class);
                startService(intent);
                intent1 = new Intent(MainActivity.this, MyService02.class);
                startService(intent1);
                intent2 = new Intent(MainActivity.this, MyService03.class);
                startService(intent2);
                intent3 = new Intent(MainActivity.this, MyService04.class);
                startService(intent3);
                intent4 = new Intent(MainActivity.this, MyService05.class);
                startService(intent4);

                intent5 = new Intent(MainActivity.this, MyService06.class);
                startService(intent5);
                intent6 = new Intent(MainActivity.this, MyService07.class);
                startService(intent6);
                intent7 = new Intent(MainActivity.this, MyService08.class);
                startService(intent7);
                intent8 = new Intent(MainActivity.this, MyService09.class);
                startService(intent8);
                intent9 = new Intent(MainActivity.this, MyService10.class);
                startService(intent9);



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
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

//        //データーベースの作成オープン処理
//        ProcessManageDBHelper dbHelper = new ProcessManageDBHelper(getApplicationContext());
//        database = dbHelper.getWritableDatabase();


        //FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab) ;

        //サービスが起動指定で起動していない場合起動させる
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isEnableService = preferences.getBoolean("service_switch",true);


        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> listServiceInfo = am.getRunningServices(Integer.MAX_VALUE);
        boolean found = false;
        for (ActivityManager.RunningServiceInfo curr : listServiceInfo) {
            // クラス名を比較
            if (curr.service.getClassName().equals(MemoryFreshService.class.getName())) {
                // 実行中のサービスと一致
                //Toast.makeText(this, "サービス実行中", Toast.LENGTH_LONG).show();
                final LinearLayout coordinatorLayout = (LinearLayout)findViewById(R.id.linearCoordinator);
                Snackbar.make(coordinatorLayout,"サービス実行中",Snackbar.LENGTH_SHORT).show();
                found = true;
                break;
            }
        }
        if (found == false && isEnableService) {
            Intent intent = new Intent(MainActivity.this, MemoryFreshService.class);
            startService(intent);
            //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
            final LinearLayout coordinatorLayout = (LinearLayout)findViewById(R.id.linearCoordinator);
            Snackbar.make(coordinatorLayout,"サービスが停止していたため起動しました",Snackbar.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
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
            switch(position){
                case 0:
                    fragment = MainFragment.newInstance(position);
                    break;
//                case 1:
//                    fragment = new Fragment();
//                    break;
                case 1:
                    fragment = GraphFragment.newInstance(position);
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
                    return "TASKS";
                case 1:
                    return "MEMORY";
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

//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//
//        if (position <= getCount()) {
//            FragmentManager manager = ((Fragment) object).getFragmentManager();
//            FragmentTransaction trans = manager.beginTransaction();
//            trans.remove((Fragment) object);
//            trans.commit();
//        }
//    }






}

