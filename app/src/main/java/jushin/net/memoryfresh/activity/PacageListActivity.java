package jushin.net.memoryfresh.activity;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jushin.net.memoryfresh.R;

import jushin.net.memoryfresh.memory.MemoryManager;
import jushin.net.memoryfresh.object.AllAppsListItem;
import jushin.net.memoryfresh.util.AppAllListAdapter;

public class PacageListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    AppAllListAdapter adapter;
    ArrayList<AllAppsListItem> items;
    ProgressDialog progressDialog;
    Thread thread;
    PackageManager pm;
    List<ApplicationInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacage_list);

        listView = (ListView) findViewById(R.id.allAppList);

        toolbar = (Toolbar) findViewById(R.id.toolbar_allapps);
        toolbar.setTitle("アプリを選択");
        setSupportActionBar(toolbar);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("検索中");
        this.progressDialog.show();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //インストールされているアプリケーションをリストで表示
                pm = getPackageManager();
                //検索条件
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                list = pm.getInstalledApplications(0);
                items = new ArrayList<AllAppsListItem>();

                for (ApplicationInfo info : list) {
                    AllAppsListItem item = new AllAppsListItem();

                    String AppName = info.loadLabel(pm).toString();  //アプリケーション名
                    String PackageName = info.packageName;
                    item.setAppName(AppName);
                    item.setPackaeName(PackageName);
                    item.setImage(info.loadIcon(pm));

                    items.add(item);
                }
                adapter = new AppAllListAdapter(PacageListActivity.this, R.layout.item_allapps, items);
                return null;
            }

            protected void onPostExecute(Void result) {
                listView.setAdapter(adapter);
                progressDialog.dismiss();
            }

        }.execute(new Void[0]);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        // 起動中のアプリを取得する
        List<String> runningAppList = new ArrayList<>();

        //チェックされていたアイテムをINSERTする
        ArrayList<String> checkedArrayList = new ArrayList<String>();

        MemoryManager memoryManager = new MemoryManager();
        runningAppList = memoryManager.getRunningPackageName();

        SharedPreferences pref = getSharedPreferences("check", MODE_PRIVATE);
        for (String packageName : runningAppList) {
            if (! pref.getString(packageName, "").equals("")) {
                checkedArrayList.add(packageName);
            }
        }

//        //チェックボックスをつけたアプリケーションのパッケージ名を保存
//        SQLiteDatabase database;
//        ProcessManageDBHelper dbHelper = new ProcessManageDBHelper(getApplicationContext());
//        database = dbHelper.getReadableDatabase();
//        //一旦データベースの内容を全て削除
//        //第一引数：テーブル名
//        //第二引数、第三引数：SQLのWHERE条件文を作成するための引数。
//        database.delete("freshtable", null, null);
//
//
//        //checkedArrayList
//
//
//        for(String packageName: checkedArrayList){
//            String sql = "INSERT (package) INTO freshtable VALUES('" + packageName + "')";
//            database.execSQL(sql);
//
//        }
  }




//        //インストールされているアプリケーションをリストで表示
//       pm = getPackageManager();
//        //検索条件
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        list = pm.getInstalledApplications(0);
//
//        items = new ArrayList<AllAppsListItem>();

//        //検索
//        //List<ResolveInfo> list = pm.queryIntentActivities(intent,0);
//        List<ApplicationInfo> list = pm.getInstalledApplications(0);
//
//        items = new ArrayList<AllAppsListItem>();
//        // ArrayAdapter にアプリ名を追加
//        for (ApplicationInfo info : list) {
//            AllAppsListItem item = new AllAppsListItem();
//
//            String AppName = info.loadLabel(pm).toString();  //アプリケーション名
//            String PackageName =  info.packageName;
//            item.setAppName(AppName);
//            item.setPackaeName(PackageName);
//            item.setImage(info.loadIcon(pm));
//
//            items.add(item);
//        }
//
//        //ListViewを設定するAdapterを作成
//        adapter = new AppAllListAdapter(PacageListActivity.this,R.layout.item_allapps,items);
//
//        listView.setAdapter(adapter);




    /**
     * バックグラウンド処理を行うクラス
     */



}
