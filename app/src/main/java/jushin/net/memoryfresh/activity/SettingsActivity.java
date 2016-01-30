package jushin.net.memoryfresh.activity;


import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import java.util.ArrayList;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.database.ProcessManageDBHelper;

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

        //チェックされていたアイテムをINSERTする
        ArrayList<String> checkedArrayList = new ArrayList<String>;

        //チェックボックスをつけたアプリケーションのパッケージ名を保存
        SQLiteDatabase database;
        ProcessManageDBHelper dbHelper = new ProcessManageDBHelper(getApplicationContext());
        database = dbHelper.getReadableDatabase();
        //一旦データベースの内容を全て削除
        //第一引数：テーブル名
        //第二引数、第三引数：SQLのWHERE条件文を作成するための引数。
        database.delete("freshtable", null, null);


        //checkedArrayList = slectCheckBoxEnables(getApplicationContext());

        for(String packageName: checkedArrayList){
            String sql = "INSERT (package) INTO freshtable VALUES('" + packageName + "')";

        }
    }
}
