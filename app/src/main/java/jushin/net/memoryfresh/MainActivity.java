package jushin.net.memoryfresh;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;

import jushin.net.memoryfresh.service.MemoryFreshService;

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

        //サービスが起動指定に場合に起動させる
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> listServiceInfo = am.getRunningServices(Integer.MAX_VALUE);
        boolean found = false;
        for (ActivityManager.RunningServiceInfo curr : listServiceInfo) {
            // クラス名を比較
            if (curr.service.getClassName().equals(MemoryFreshService.class.getName())) {
                // 実行中のサービスと一致
                //Toast.makeText(this, "サービス実行中", Toast.LENGTH_LONG).show();
                final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.coordinator);
                Snackbar.make(linearLayout,"サービス実行中",Snackbar.LENGTH_SHORT).show();
                found = true;
                break;
            }
        }
        if (found == false) {
            Intent intent = new Intent(MainActivity.this, MemoryFreshService.class);
            startService(intent);
            //Toast.makeText(this, "サービスが停止していたため起動しました", Toast.LENGTH_LONG).show();
            final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.coordinator);
            Snackbar.make(linearLayout,"サービスが停止していたため起動しました",Snackbar.LENGTH_SHORT).show();
        }

    }


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
//                    fragment =TeatFragment.newInstance(position);
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

            if (position == 0){
                return "Tasks";
            }else {
                return "Memory";
            }

        }
    }




}

