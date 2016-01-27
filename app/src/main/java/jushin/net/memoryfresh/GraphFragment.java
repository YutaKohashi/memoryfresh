package jushin.net.memoryfresh;


import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;

import java.util.Calendar;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class GraphFragment extends Fragment  {


    private NotificationManager mManager;
    private int number = 0;

    //メモリ取得に使う変数
    MemoryManager manager;

    //メモリの値を格納する変数
    float total,free,use;
    float[] data;//グラフの値の指定
    String str = "";//グラフの説明変数
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

        //viewを取得
        v = inflater.inflate(R.layout.fragment_graph, container, false);

        //グラフ処理
        PieChart pieChart = (PieChart)v.findViewById(R.id.graph);//関連付け
        graphs = new GraphManager(pieChart,true,40f);//インスタンス化

        //メモリクラスのインスタンス化
        manager = new MemoryManager(this.getContext());

        //各種メモリサイズの格納
        total = manager.totalMemory();
        free = manager.freeMemorySize();
        use = manager.useSize();
        free = total - use;
//        free = (total - manager.useSize());

        //----------グラフの処理----------
        name =  new String[]{"使用中","未使用"};        //項目(５つまで)
        data = new  float[]{(use / total) * 100//グラフのデータ
                ,(free / total) * 100};


        str = "全体メモリ(MB):" + Math.ceil(total/1000) //グラフ情報
                + "\n未使用(MB)" + Math.ceil(free/1000)
                + "\n使用(MB):" + Math.ceil((total-free)/1000);

        graphs.strart(name,data,str);//グラフ描画

        return v;
    }

//    private void notification() {
//
//
//    }
//    private void sendNotification() {
//        Notification.Builder noticeBuilder = new Notification.Builder(this);
//        noticeBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
//        noticeBuilder.setTicker("ticker");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            noticeBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
//        }
//        noticeBuilder.setContentTitle("title");
//        noticeBuilder.setContentInfo("info");
//        noticeBuilder.setContentText("text");
//        Notification notice = noticeBuilder.build();
//        NotificationManager manager =this.getContext().getSystemService(this.getContext().NOTIFICATION_SERVICE);
//        manager.notify(1, notice);// 1 はアプリ内の通知uniqueID。削除時に指定
//    }


}



