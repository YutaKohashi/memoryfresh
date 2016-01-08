package jushin.net.memoryfresh;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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

        pieChart.setDrawHoleEnabled(true); // 真ん中に穴を空けるかどうか
        pieChart.setHoleRadius(50f);       // 真ん中の穴の大きさ(%指定)
        pieChart.setHoleColorTransparent(true);
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setRotationAngle(270);          // 開始位置の調整
        pieChart.setRotationEnabled(true);       // 回転可能かどうか
        pieChart.getLegend().setEnabled(true);   //
        pieChart.setDescription(" PieChart 説明");
        pieChart.setData(createPieChartData());

        // 更新
        pieChart.invalidate();
        // アニメーション
        pieChart.animateXY(2000, 1000); // 表示アニメーション

        return v;

    }

    // pieChartのデータ設定
    private PieData createPieChartData() {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        xVals.add("A");
        xVals.add("B");
        xVals.add("C");

        yVals.add(new Entry(20, 0));
        yVals.add(new Entry(30, 0));
        yVals.add(new Entry(50,0));

        PieDataSet dataSet = new PieDataSet(yVals, "Data");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(1f);

        // 色の設定
        colors.add(ColorTemplate.COLORFUL_COLORS[0]);
        colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        colors.add(ColorTemplate.COLORFUL_COLORS[2]);
        dataSet.setColors(colors);
        dataSet.setDrawValues(true);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());

        // テキストの設定
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        return data;



    }


//



}



