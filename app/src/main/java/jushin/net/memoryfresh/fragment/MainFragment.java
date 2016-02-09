package jushin.net.memoryfresh.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.Timer;

import jushin.net.memoryfresh.object.ListItem;
import jushin.net.memoryfresh.util.ActivityState;
import jushin.net.memoryfresh.util.ProcessListAdapter;
import jushin.net.memoryfresh.R;


/**
 * Created by Yuta on 2016/01.
 */

public class MainFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener{

    ArrayList<String> process_names;
    ListView listView;
    Button startButton;
    ProcessListAdapter adapter;
    List<ListItem> items;
    PackageManager pm;
    List<AndroidAppProcess> processes;

    String processName;
    Drawable icon;
    double beforeSize;

    int count = 0;

    int position;
    int y;

    ActivityState activityState = new ActivityState();

    private SwipeRefreshLayout mSwipeRefreshLayout;


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


    ListItem listItem;
   ProgressDialog progressDialog;
    KProgressHUD prog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout);


        // 色設定
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                R.color.colorPrimary, R.color.colorPrimaryDeepDark,
                R.color.colorAccentDark);

        // Listenerをセット
        mSwipeRefreshLayout.setOnRefreshListener(this);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setIndeterminate(true);
//
//        progressDialog.setCancelable(false);
//
//        progressDialog.show();

        prog = KProgressHUD.create(getActivity());

        prog
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("お待ち下さい")
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        onRefresh();

        return v;
    }




    @Override
    public void onRefresh() {
        // 更新処理を実装する
        // ここでは単純に5秒後にインジケータ非表示
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                listView = (ListView) getActivity().findViewById(R.id.listview);

                processes = ProcessManager.getRunningAppProcesses();
                items = new ArrayList<ListItem>();

                icon = null;
                pm = getContext().getPackageManager();

                for (AndroidAppProcess process : processes) {

                    ListItem listItem = new ListItem();

                    processName = process.name;

                    //procesNameがサービスの名前ならばArrayListに含めない
                    //jushin.net.memoryfresh:testprocess

                    if(processName.matches("jushin.net.memoryfresh:testprocess"+ ".*")){
                        continue;
                    }

                    long size = 0L;
                    try {

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

                    double beforeSize = (double) size / 1000 / 1000;

                    listItem.setText(processName, String.format("%.2f", beforeSize) + "MB");
                    listItem.setImageId(icon);

                    items.add(listItem);
                }

                //並び替え処理（メモリ使用順：降順）
                Collections.sort(items, new MemoryUsageSort());

                // adapterのインスタンスを作成カスタムレイアウトを適用
                adapter =
                        new ProcessListAdapter(getContext(), R.layout.list_item, items);


//
//        listView.setAdapter(adapter);
                listView.setAdapter(adapter);
                try {
                    listView.setSelectionFromTop(position, y);
                } catch (Exception ex) {
                    Log.d("listView", ex.toString());
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        activityState.activityKeep();
                        ListItem listItem = items.get(position);
                        PackageManager pManager = getActivity().getPackageManager();
                        //Intent intent = pManager.getLaunchIntentForPackage(listItem.getTextProcessName());
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + listItem.getTextProcessName()));
                        startActivity(intent);

                    }
                });
                // 更新が終了したらインジケータ非表示
                mSwipeRefreshLayout.setRefreshing(false);
                //progressDialog.dismiss();
                prog.dismiss();
            }
        }, 5000);
    }


    public class MemoryUsageSort implements Comparator<ListItem> {

        @Override
        public int compare(ListItem lhs, ListItem rhs) {

            int no1 = Integer.parseInt(lhs.getTextProcessUses().replaceAll("[^0-9]", ""));
            int no2 = Integer.parseInt(rhs.getTextProcessUses().replaceAll("[^0-9]", ""));

            if (no1 < no2) {
                return 1;
            } else if (no1 == no2) {
                return 0;
            } else {
                return -1;
            }

        }
    }




}

