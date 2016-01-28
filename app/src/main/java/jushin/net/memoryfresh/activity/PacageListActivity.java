package jushin.net.memoryfresh.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.object.AllAppsListItem;
import jushin.net.memoryfresh.util.AppAllListAdapter;

public class PacageListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    AppAllListAdapter adapter;
    ArrayList<AllAppsListItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacage_list);

        listView = (ListView)findViewById(R.id.allAppList);

        toolbar= (Toolbar)findViewById(R.id.toolbar_allapps);
        toolbar.setTitle("アプリを選択");
        setSupportActionBar(toolbar);

        //インストールされているアプリケーションをリストで表示
        PackageManager pm = getPackageManager();
        //検索条件
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        //検索
        List<ResolveInfo> list = pm.queryIntentActivities(intent,0);

        // ArrayAdapter にアプリ名を追加
        for (ResolveInfo info : list) {
            AllAppsListItem item = new AllAppsListItem();

            String AppName = info.loadLabel(pm).toString();
            item.setAppName(AppName);
            item.setPackaeName(AppName);
            item.setImage(info.loadIcon(pm));

            items.add(item);
        }

        //ListViewを設定するAdapterを作成
        adapter = new AppAllListAdapter(PacageListActivity.this,R.layout.item_allapps,items);

        listView.setAdapter(adapter);
    }
}
