package jushin.net.memoryfresh;


import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class GraphFragment extends Fragment  {



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

        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        PieChart pieChart = (PieChart)v.findViewById(R.id.graph);
        GraphManager graphs = new GraphManager(pieChart,true,40f);

        //インスタンス化
        MemoryManager manager = new MemoryManager(this.getContext());

        //メモリの値を格納する変数
        float total,free,app,service;

        //各種メモリサイズの格納
        total = manager.totalMemory()/1000;
        app = manager.ProcessMemorySize() /8/1000;
        service = manager.serviceMemory()/8/1000;
        free = ( total - (app+service) );

        String[] name = {"アプリ","サービス","未使用"};//項目(５つまで)
        float[] data = {(app/total)*100,(service/total)*100,(free/total)*100};//メモリ使用サイズ(型)
        String str = "";

        str = "全体メモリ(MB):" + Math.ceil(total)+"\nアプリ(MB):" + Math.ceil(app);
        str += "\nサービス(MB):" + Math.ceil(service) +"未使用(MB)"+ Math.ceil(free);



        graphs.graphData(name, data);
        graphs.graphSettings(str);
        graphs.graphColors(name.length);
        graphs.strart();
        return v;

    }


}



