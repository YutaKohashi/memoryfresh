package jushin.net.memoryfresh.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
 * Created by Yuta on 2016/01.
 */

public class MainFragment extends Fragment {

    ArrayList<String> process_names;
    ListView listView;
    Button startButton;
    ProcessListAdapter adapter;
    List<ListItem> items;
    PackageManager pm;
    List<AndroidAppProcess> processes;
    ListItem listItem;
    String processName;
    Drawable icon;
    double beforeSize;

    int count = 0;

    int position;
    int y;

    private Timer mTimer = null;
    private Timer mTimer2 = null;
    private Handler mHandler = null;
    private Handler mHandler2 = null;
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
    public void onPause() {
        super.onPause();

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView)v.findViewById(R.id.listview);
        processes = ProcessManager.getRunningAppProcesses();

        items = new ArrayList<ListItem>();

       icon = null;
        pm = getContext().getPackageManager();

        for (AndroidAppProcess process : processes) {

            ListItem listItem = new ListItem();

            processName = process.name;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem listItem = items.get(position);
                PackageManager pManager = getActivity().getPackageManager();
                //Intent intent = pManager.getLaunchIntentForPackage(listItem.getTextProcessName());
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + listItem.getTextProcessName()));
                startActivity(intent);

            }
        });


        mHandler = new Handler();
        mHandler2 = new Handler();
        mTimer = new Timer();
        mTimer2 = new Timer();


        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                // mHandlerを通じてUI Threadへ処理をキューイング
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        position = listView.getFirstVisiblePosition();
                        try{
                            y = listView.getChildAt(0).getTop();
                        }catch(NullPointerException ex){
                            Log.d("listView::", ex.toString());
                        }

                        // 実行したい処理
                        Log.d("thread:::","message");
                        // TODO Auto-generated method stub
                        items = new ArrayList<ListItem>();

                        icon = null;
                        pm = getContext().getPackageManager();

                        for (AndroidAppProcess process : processes) {

                            listItem = new ListItem();

                            processName = process.name;
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
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }

                            try {
                                //アプリのアイコン取得
                                icon = pm.getApplicationIcon(process.name);
                            } catch (Throwable e) {

                                e.printStackTrace();
                                //例外が発生した場合デフォルトのアイコンを適用
                                icon = getContext().getDrawable(R.drawable.ic_launcher);
                            }

                            beforeSize = (double)size /1000 /1000;

                            listItem.setText(processName,String.format("%.2f",beforeSize) +"MB");
                            listItem.setImageId(icon);




                            items.add(listItem);
                        }

                        //並び替え処理（メモリ使用順：降順）
                        Collections.sort(items, new MemoryUsageSort());


                        // adapterのインスタンスを作成カスタムレイアウトを適用
                        adapter =
                                new ProcessListAdapter(getContext(),R.layout.list_item, items);

                        listView.setAdapter(adapter);
                        try{
                            listView.setSelectionFromTop(position, y);
                        }catch(Exception ex){
                            Log.d("listView",ex.toString());
                        }


                    }
                });
            }
        }, 0, 3000);



//        // メモリ解放の実装
//        startButton = (Button)v.findViewById(R.id.start_button);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final String TAG = "test::";
//                // Integer オブジェクトの弱参照を保持する
//                Integer integer = new Integer(123456);
//                SoftReference<Integer> ref = new SoftReference<Integer>(integer);
//
//// SoftReference の中身を取得・表示
//                Integer i = ref.get();
//                Log.d(TAG, "i=" + i);
//
//// 強参照を全て無くす
//                integer = null;
//                i = null;
//
//// メモリ負荷をかける
//                try {
//                    HashMap<String, Byte[]> map = new HashMap<String, Byte[]>();
//                    for (int j = 0; j < 100000; j++) {
//                        Byte[] v = new Byte[10000];
//                        String k = String.valueOf(System.currentTimeMillis());
//                        map.put(k, v);
//                    }
//                } catch (OutOfMemoryError oome) {
//                    Log.d(TAG, "OutOfMemoryError!!");
//                }
//
//// OutOfMemoryError 発生後に中身を取得・表示
//                i = ref.get();
//                Log.d(TAG, "i=" + i);
//
//            }
//        });

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
