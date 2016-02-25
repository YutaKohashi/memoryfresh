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

    int count;

    float memoryinfo[];

    Handler mHandler;
    Timer mTimer;

    //実値
    //フラグが  になった場合初期化する
    static float total1 = 0;
    static float free1 = 0;
    static float use1 = 0;

    //グラフの値
    static float total2 =0 ;
    static float free2 = 0;
    static float use2 = 0;

    //レート
    static float rate = (float)0.02;



    final String[] name = new String[]{"使用中", "未使用"};//項目(５つまで)

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
        manager = new MemoryManager();//メモリクラスのインスタンス化

        useText.setTextColor(ColorTemplate.JOYFUL_COLORS[0]);
        freeText.setTextColor(ColorTemplate.JOYFUL_COLORS[1]);

        memView(true);

        mHandler = new Handler();
        mTimer = new Timer();

//         mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                // mHandlerを通じてUI Threadへ処理をキューイング
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        memView(false);
//
//                    }
//                });
//            }
//        }, 500, 500);



        //まず2回値を取得
        //500ミリ秒ごと取得
        //10ミリ秒ごと描画

        //グラフ = グラフ * （1 - レート） + 実 * レート
        //実メモリの値をフィールドでstaticで保持

        //interpolation
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //ここで補間で描画する
                        //補間値を計算

                        total2 = total2 * (1 - rate) + total1 * rate;
                        free2 =  free2 *(1 - rate) + free1 * rate;
                        use2 = use2 * (1- rate) + use1 * rate;

                        //描画処理
                        drawGraph(total2,free2,use2);
                        count += 1;

                        if(count == 50){
                            count = 1;

                            //ここであたいを取得する
                            memoryinfo = getMemoryInfo();
                            //実値に代入
                            total1 = memoryinfo[0];
                            free1 = memoryinfo[1];
                            use1 = memoryinfo[2];

                        }
                    }
                });
            }
        },500,10);

        return v;
    }

    private void memView(boolean flg){

        //メモリクラスのインスタンス化
        manager = new MemoryManager();

        //各種メモリサイズの格納(free)
        total = manager.totalMemory();
        free = manager.defaultFreeMem();
        use = (total - free);


        //----------グラフの処理----------

        data = new float[]{(use / total) * 100//グラフのデータ
                , (free / total) * 100};


        str = "全体メモリ(MB):" + Math.ceil((int)total) //グラフ情報
                + "\n未使用(MB)" + Math.ceil(free)
                + "\n使用(MB):" + Math.ceil((use));

        graphs.strart(name, data, str, flg);//グラフ描画

    }

    //値取得メソッド
    private float[] getMemoryInfo(){
        float[] memoryinfo = new float[3];

        manager = new MemoryManager();

        total = manager.totalMemory();
        free = manager.defaultFreeMem();
        use = total-free;

        memoryinfo[0] = total;
        memoryinfo[1]= free;
        memoryinfo[2]= use;

        return memoryinfo;
    }

    //グラフ描画メソッド
    //実メモリの値をフィールドでstaticで保持
    //グラフの値を
    private void drawGraph(float total, float free, float use){

        //回転有無のフラグ
        boolean flg = false;

        float userate = (use / total) * 100;
        float freerate = (free /total) * 100;

        data = new float[]{userate, freerate};

        str = "全体メモリ(MB):" + Math.ceil(total) //グラフ情報
                + "\n未使用(MB)" + Math.ceil(free)
                + "\n使用(MB):" + Math.ceil((total - free));

        graphs.strart(name, data, str, flg);//グラフ描画

    }


}



