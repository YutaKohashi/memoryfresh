package jushin.net.memoryfresh.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.util.GraphManager;
import jushin.net.memoryfresh.util.MemoryManager;


public class GraphFragment extends Fragment  {


    //グラフを扱う辺陬
    PieChart pieChart;

    //メモリ取得に使う変数
    MemoryManager manager;

    //色の表示の変数
    TextView useText;
    TextView freeText;

    //メモリの値を格納する変数
    float total,free,use;
    float[] mfree;
    float[] data;//グラフの値の指定
    String str = "";//グラフの説明変数
    GraphManager graphs;
    View v;


    Handler mHandler;
    Timer mTimer;

    String[] name = new String[]{"使用中", "未使用"};//項目(５つまで)

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //viewを取得
        v = inflater.inflate(R.layout.fragment_graph, container,false);


        //関連付け
        pieChart = (PieChart)v.findViewById(R.id.graph);
        useText = (TextView)v.findViewById(R.id.color1);
        freeText = (TextView)v.findViewById(R.id.color2);

        //インスタンス化
        graphs = new GraphManager(pieChart,true,40f);//グラフクラスのインスタンス化
        manager = new MemoryManager(this.getContext());//メモリクラスのインスタンス化

        useText.setTextColor(ColorTemplate.JOYFUL_COLORS[0]);
        freeText.setTextColor(ColorTemplate.JOYFUL_COLORS[1]);

        memView(true);

        mHandler = new Handler();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                // mHandlerを通じてUI Threadへ処理をキューイング
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        memView(false);

                    }
                });
            }
        }, 500, 500);

        return v;
    }

    private void memView(boolean flg){

        //メモリクラスのインスタンス化
        manager = new MemoryManager(this.getContext());

        //各種メモリサイズの格納(free)
        total = manager.totalMemory();
        free = manager.defaultFreeMem();
        use = (total - free);


        //----------グラフの処理----------

        data = new float[]{(use / total) * 100//グラフのデータ
                , (free / total) * 100};


        str = "全体メモリ(MB):" + Math.ceil(total) //グラフ情報
                + "\n未使用(MB)" + Math.ceil(free)
                + "\n使用(MB):" + Math.ceil((total - free));

        graphs.strart(name, data, str, flg);//グラフ描画



    }


}



