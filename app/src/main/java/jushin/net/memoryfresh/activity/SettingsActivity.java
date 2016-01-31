package jushin.net.memoryfresh.activity;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import java.util.ArrayList;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.database.ProcessManageDBHelper;
import jushin.net.memoryfresh.service.MemoryFreshService;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar= (Toolbar)findViewById(R.id.toolbar_settings);
        toolbar.setTitle("設定");
        setSupportActionBar(toolbar);



    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent(SettingsActivity.this, MemoryFreshService.class);
        stopService(intent);
        startService(intent);

    }

}
