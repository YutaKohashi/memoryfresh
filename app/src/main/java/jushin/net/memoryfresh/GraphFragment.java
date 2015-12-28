package jushin.net.memoryfresh;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class GraphFragment extends Fragment {



    ArrayList<String> process_names;
    ListView listView;
    Button startButton;

    public GraphFragment() {
        // Required empty public constructor
    }

    public static GraphFragment newInstance(int position) {
        GraphFragment fragment = new GraphFragment();
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

        listView = (ListView)v.findViewById(R.id.listview);
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

            listItem.setText(processName,size / 8 / 1000 + "KB");
            listItem.setImageId(icon);

            items.add(listItem);
        }

        // adapterのインスタンスを作成カスタムレイアウトを適用
        ProcessListAdapter adapter =
                new ProcessListAdapter(getContext(),R.layout.list_item, items);

        listView.setAdapter(adapter);

        return v;
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View v = inflater.inflate(R.layout.fragment_main, container, false);
//
//        PieChart pieChart = (PieChart)findViewById(R.id.graph);
//
//        pieChart.setDrawHoleEnabled(true); // 真ん中に穴を空けるかどうか
//        pieChart.setHoleRadius(50f);       // 真ん中の穴の大きさ(%指定)
//        pieChart.setHoleColorTransparent(true);
//        pieChart.setTransparentCircleRadius(55f);
//        pieChart.setRotationAngle(270);          // 開始位置の調整
//        pieChart.setRotationEnabled(true);       // 回転可能かどうか
//        pieChart.getLegend().setEnabled(true);   //
//        pieChart.setDescription("PieChart 説明");
//        pieChart.setData(createPieChartData());
//
//        // 更新
//        pieChart.invalidate();
//        // アニメーション
//        pieChart.animateXY(2000, 2000); // 表示アニメーション
//
//    return v;
//    }



}



