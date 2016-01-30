package jushin.net.memoryfresh;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;

import java.util.Timer;
import java.util.TimerTask;


public class GraphFragment extends Fragment  {


    PieChart pieChart;

    //メモリ取得に使う変数
    MemoryManager manager;

    //メモリの値を格納する変数
    float total,free,use;
    float[] mfree;
    float[] data;//グラフの値の指定
    String str = "";//グラフの説明変数
    GraphManager graphs;
    View v;

    Handler mHandler;
    Timer mTimer;

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
        v = inflater.inflate(R.layout.fragment_graph, container, false);

        //グラフ処理
        pieChart = (PieChart)v.findViewById(R.id.graph);//関連付け
        graphs = new GraphManager(pieChart,true,40f);//インスタンス化

        //メモリクラスのインスタンス化
        manager = new MemoryManager(this.getContext());
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

        String[] name;//項目(５つまで)

        graphs = new GraphManager(pieChart,true,40f);//インスタンス化

        //メモリクラスのインスタンス化
        manager = new MemoryManager(this.getContext());

        if(){

            name = new String[]{"使用中", "未使用"};

            //各種メモリサイズの格納(free)
            total = manager.totalMemory();
            free = manager.defaultFreeMem();
            use = (total - free);
            manager.logInfo();

            //----------グラフの処理----------

            data = new float[]{(use / total) * 100//グラフのデータ
                    , (free / total) * 100};


            str = "全体メモリ(MB):" + Math.ceil(total) //グラフ情報
                    + "\n未使用(MB)" + Math.ceil(free)
                    + "\n使用(MB):" + Math.ceil((total - free));

            graphs.strart(name, data, str, flg);//グラフ描画

        }else{

            name= new String[]{"使用中", "未使用","キャッシュ"};

            //各種メモリサイズの格納(free)a
            total = manager.totalMemory();
            mfree = manager.defaultFreeMem(name.length);
            use = (total - (mfree[0]+mfree[1]+mfree[2]));
            manager.logInfo();

            //----------グラフの処理----------

            data = new float[]{(use / total) * 100//グラフのデータ
                    , (free / total) * 100};


            str = "全体メモリ(MB):" + Math.ceil(total) //グラフ情報
                    + "\n未使用(MB)" + Math.ceil(free)
                    + "\n使用(MB):" + Math.ceil((total - free));

            graphs.strart(name, data, str, flg);//グラフ描画

        }

    }


}



