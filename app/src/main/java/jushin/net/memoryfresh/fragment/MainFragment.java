package jushin.net.memoryfresh.fragment;


import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import jushin.net.memoryfresh.activity.MainActivity;
import jushin.net.memoryfresh.object.ListItem;
import jushin.net.memoryfresh.util.ProcessListAdapter;
import jushin.net.memoryfresh.R;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    ArrayList<String> process_names;
    ListView listView;
    Button startButton;
    Button updateButton;
    ProcessListAdapter adapter;
    List<ListItem> items;
    PackageManager pm;
    List<AndroidAppProcess> processes;

    int position;
    int y;

    private Timer mTimer = null;
    private Handler mHandler = null;

    private Runnable updateText;

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
        updateButton = (Button)v.findViewById(R.id.update_button);

        listView = (ListView)v.findViewById(R.id.listview);
        processes = ProcessManager.getRunningAppProcesses();

        items = new ArrayList<ListItem>();

        Drawable icon = null;
        pm = getContext().getPackageManager();

        for (AndroidAppProcess process : processes) {

            ListItem listItem = new ListItem();

            String processName = process.name;
            long size = 0L;
            try {
                //getResidentSetSize()メソッドの処理
                // 配列変数fieldsの０番目にはtotal program sizeが格納されていて
                //１番目にはresident sizeが格納されている

                //size       プログラムサイズの総計
                //            (/proc/[pid]/status の VmSize と同じ)
                //resident   実メモリー上に存在するページ
                //            (/proc/[pid]/status の VmRSS と同じ)
                //share      共有ページ (ファイルと関連付けられているページ)
                //           text       テキスト (コード)
                //lib        ライブラリ (Linux 2.6 では未使用)
                //data       データ + スタック
                //dt         ダーティページ (Linux 2.6 では未使用)

                size = process.statm().getResidentSetSize();  //バイトで代入される
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

            double beforeSize = (double)size /1000 /1000;

            listItem.setText(processName,String.format("%.2f",beforeSize) +"MB");
            listItem.setImageId(icon);

            items.add(listItem);
        }

        //並び替え処理（メモリ使用順：降順）
        Collections.sort(items, new MemoryUsageSort());
        Iterator<ListItem> it = items.iterator();


        // adapterのインスタンスを作成カスタムレイアウトを適用
        adapter =
                new ProcessListAdapter(getContext(),R.layout.list_item, items);

        listView.setAdapter(adapter);

        mHandler = new Handler();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        position = listView.getFirstVisiblePosition();
                        y = listView.getChildAt(0).getTop();
                        // 実行したい処理
                        Log.d("test","message");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            // TODO Auto-generated method stub
                                items = new ArrayList<ListItem>();

                                Drawable icon = null;
                                pm = getContext().getPackageManager();

                                for (AndroidAppProcess process : processes) {

                                    ListItem listItem = new ListItem();

                                    String processName = process.name;
                                    long size = 0L;
                                    try {
                                        //getResidentSetSize()メソッドの処理
                                        // 配列変数fieldsの０番目にはtotal program sizeが格納されていて
                                        //１番目にはresident sizeが格納されている

                                        //size       プログラムサイズの総計
                                        //            (/proc/[pid]/status の VmSize と同じ)
                                        //resident   実メモリー上に存在するページ
                                        //            (/proc/[pid]/status の VmRSS と同じ)
                                        //share      共有ページ (ファイルと関連付けられているページ)
                                        //           text       テキスト (コード)
                                        //lib        ライブラリ (Linux 2.6 では未使用)
                                        //data       データ + スタック
                                        //dt         ダーティページ (Linux 2.6 では未使用)

                                        size = process.statm().getResidentSetSize();  //バイトで代入される
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

                                    double beforeSize = (double)size /1000 /1000;

                                    listItem.setText(processName,String.format("%.2f",beforeSize) +"MB");
                                    listItem.setImageId(icon);

                                    items.add(listItem);
                                }

                                //並び替え処理（メモリ使用順：降順）
                                Collections.sort(items, new MemoryUsageSort());
                                Iterator<ListItem> it = items.iterator();


                                // adapterのインスタンスを作成カスタムレイアウトを適用
                                adapter =
                                        new ProcessListAdapter(getContext(),R.layout.list_item, items);

                                listView.setAdapter(adapter);
                                listView.setSelectionFromTop(position, y);

                            }
                        });
                    }
                });
            }
        }, 5000, 5000);

        return v;
    }



    public class MemoryUsageSort implements Comparator<ListItem>{

        @Override
        public int compare(ListItem lhs, ListItem rhs) {

            int no1 = Integer.parseInt(lhs.getTextProcessUses().replaceAll("[^0-9]",""));
            int no2 = Integer.parseInt(rhs.getTextProcessUses().replaceAll("[^0-9]",""));

            if(no1 < no2){
                return 1;
            }else if(no1 == no2){
                    return 0;
            }else{
                return -1;
            }

        }
    }




}
