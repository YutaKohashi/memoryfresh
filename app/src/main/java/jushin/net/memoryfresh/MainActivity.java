package jushin.net.memoryfresh;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> process_names;
    ListView listView;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ツールバーをアクションバーとしてセット
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listview);
        List<AndroidAppProcess> processes = ProcessManager.getRunningAppProcesses();
        //process_names = new ArrayList<String>();
        List<IconTextArrayItem> items = new ArrayList<IconTextArrayItem>();

        Drawable icon = null;
        PackageManager pm = this.getPackageManager();

        for (AndroidAppProcess process : processes) {
            String processName = process.name;
            long size = 0L;
            try {
                size = process.statm().getSize();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //process_names.add(processName + ":" + size / 8 / 1000 + "KB");

            try {
                //アプリのアイコン取得
                icon = pm.getApplicationIcon(process.name);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            items.add(new IconTextArrayItem(icon, processName + ":" + size / 8 / 1000 + "KB"));
        }



        // アダプタ生成
        ListAdapter adapter = new ProcessListAdapter(this, R.layout.list_item, items);

        // アダプタ設定
        listView.setAdapter(adapter);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_expandable_list_item_1, process_names);
//
//        listView.setAdapter(adapter);
    }
}
