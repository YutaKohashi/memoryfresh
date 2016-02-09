package jushin.net.memoryfresh.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.service.MemoryFreshService;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar= (Toolbar)findViewById(R.id.toolbar_settings);
        toolbar.setTitle("設定");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {

        //サービスが起動指定で起動していない場合起動させる
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isEnableService = pref.getBoolean("service_switch", true);


        super.onDestroy();
        Intent intent = new Intent(SettingsActivity.this, MemoryFreshService.class);

        //サービスを再起動させてServiceActivityのonStartCommandを実行させる
        if (isEnableService) {
            stopService(intent);
            startService(intent);
        }
    }

    @Override
    protected  void onPause(){
        super.onPause();
        finish();
    }

//    @Override
//    public void onUserLeaveHint(){
//        //ホームボタンが押された時や、他のアプリが起動した時に呼ばれる
//        //戻るボタンが押された場合には呼ばれない
//        Toast.makeText(getApplicationContext(), "Good bye!" , Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch(keyCode){
//
//            case KeyEvent.KEYCODE_BACK:
//                //戻るボタンが押された時の処理。
//                Toast.makeText(this, "back button!" , Toast.LENGTH_SHORT).show();
//                finish();
//                return true;
//        }
//        return false;
//    }


}
