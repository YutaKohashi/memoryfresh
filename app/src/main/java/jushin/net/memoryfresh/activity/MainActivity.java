package jushin.net.memoryfresh.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.fragment.GraphFragment;
import jushin.net.memoryfresh.fragment.MainFragment;
import jushin.net.memoryfresh.service.MemoryFreshService;

/**
 * Created by Yuta on 2016/01.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DesignPagerAdapter adapter = new DesignPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab) ;

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
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator);
                Snackbar.make(coordinatorLayout,"サービス実行中",Snackbar.LENGTH_SHORT).show();
                found = true;
                break;
            }
        }
        if (found == false && isEnableService) {
            Intent intent = new Intent(MainActivity.this, MemoryFreshService.class);
            startService(intent);
            //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator);
            Snackbar.make(coordinatorLayout,"サービスが停止していたため起動しました",Snackbar.LENGTH_LONG).show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_settings){
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }



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
            return "Tab " + position;
        }
    }




}

