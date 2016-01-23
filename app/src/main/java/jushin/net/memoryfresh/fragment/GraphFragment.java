package jushin.net.memoryfresh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;

import jushin.net.memoryfresh.util.GraphManager;
import jushin.net.memoryfresh.util.MemoryManager;
import jushin.net.memoryfresh.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class GraphFragment extends Fragment  {

    MemoryManager manager;
    //メモリの値を格納する変数
    float total,free,app,service;
    float[] data;
    String str = "";
    GraphManager graphs;
    View v;
    String[] name;

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

        v = inflater.inflate(R.layout.fragment_graph, container, false);
        PieChart pieChart = (PieChart)v.findViewById(R.id.graph);
        graphs = new GraphManager(pieChart,true,40f);



        manager = new MemoryManager(this.getContext());
        name =  new String[]{"アプリ","サービス","未使用"};//項目(５つまで)


        new Thread(new Runnable() {
            @Override
            public void run() {
                //各種メモリサイズの格納
                total = manager.totalMemory()/1000;
                app = manager.ProcessMemorySize() /8/1000;
                service = manager.serviceMemory()/8/1000;
                free = ( total - (app+service) );

            }
        }).start();




        data = new float[]{(app / total) * 100, (service / total) * 100, (free / total) * 100};//メモリ使用サイズ(型)

        str = "全体メモリ(MB):" + Math.ceil(total)+"\nアプリ(MB):" + Math.ceil(app);
        str += "\nサービス(MB):" + Math.ceil(service) +"未使用(MB)"+ Math.ceil(free);

        graphs.graphData(name, data);
        graphs.graphSettings(str);
        graphs.graphColors(name.length);
        graphs.strart();

        return v;
    }


}



