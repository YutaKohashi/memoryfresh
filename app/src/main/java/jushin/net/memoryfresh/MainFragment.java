package jushin.net.memoryfresh;


import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    ArrayList<String> process_names;
    ListView listView;
    Button startButton;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(int position) {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) v.findViewById(R.id.listview);
        List<AndroidAppProcess> processes = ProcessManager.getRunningAppProcesses();

        List<ListItem> items = new ArrayList<ListItem>();

        Drawable icon = null;
        PackageManager pm = getContext().getPackageManager();

        for (AndroidAppProcess process : processes) {

            ListItem listItem = new ListItem();

            String processName = process.name;
            long size = 0L;
            try {
                size = process.statm().getSize();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                //アプリのアイコン取得
                icon = pm.getApplicationIcon(process.name);
            } catch (PackageManager.NameNotFoundException e) {

                e.printStackTrace();
                //例外が発生した場合デフォルトのアイコンを適用
                icon = getContext().getDrawable(R.drawable.ic_launcher);
            }

            listItem.setText(processName, size / 8 / 1000 + "KB");
            listItem.setImageId(icon);

            items.add(listItem);
        }

        // adapterのインスタンスを作成カスタムレイアウトを適用
        ProcessListAdapter adapter =
                new ProcessListAdapter(getContext(), R.layout.list_item, items);

        listView.setAdapter(adapter);

        // startbutton
        startButton = (Button) getActivity().findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String TAG = "test::";
                // Integer オブジェクトの弱参照を保持する
                Integer integer = new Integer(123456);
                SoftReference<Integer> ref = new SoftReference<Integer>(integer);

// SoftReference の中身を取得・表示
                Integer i = ref.get();
                Log.d(TAG, "i=" + i);

// 強参照を全て無くす
                integer = null;
                i = null;

// メモリ負荷をかける
                try {
                    HashMap<String, Byte[]> map = new HashMap<String, Byte[]>();
                    for (int j = 0; j < 100000; j++) {
                        Byte[] v = new Byte[10000];
                        String k = String.valueOf(System.currentTimeMillis());
                        map.put(k, v);
                    }
                } catch (OutOfMemoryError oome) {
                    Log.d(TAG, "OutOfMemoryError!!");
                }

// OutOfMemoryError 発生後に中身を取得・表示
                i = ref.get();
                Log.d(TAG, "i=" + i);

            }
        });

        return v;
    }

}
