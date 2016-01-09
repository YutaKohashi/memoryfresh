package jushin.net.memoryfresh;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import java.util.ArrayList;


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

        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        PieChart pieChart = (PieChart)v.findViewById(R.id.graph);
        GraphManager graphs = new GraphManager(pieChart,true,40f);


        String[] name = {"OS","アプリ","その他"};//項目(５つまで)
        float[] data = {10.00f,20.00f,30.00f};//メモリ使用サイズ(型s) 先輩変更点


        String str = "種類ごとのメモリの使用率";




        graphs.graphData(name, data);
        graphs.graphSettings(str);
        graphs.graphColors(name.length);
        graphs.strart();
        return v;

    }



}



