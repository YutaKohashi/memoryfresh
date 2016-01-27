package jushin.net.memoryfresh.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import jushin.net.memoryfresh.R;

public class PacageListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacage_list);

        listView = (ListView)findViewById(R.id.allAppList);

        toolbar= (Toolbar)findViewById(R.id.toolbar_allapps);
        toolbar.setTitle("アプリを選択");
        setSupportActionBar(toolbar);

        //インストールされているアプリケーションをリストで表示

    }
}
