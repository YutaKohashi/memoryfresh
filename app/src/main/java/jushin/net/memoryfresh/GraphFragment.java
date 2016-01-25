package jushin.net.memoryfresh;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    float total,free,app,service,system;
    float[] data;//グラフの値の指定
    String str = "";//グラフの説明変数
    GraphManager graphs;
    View v;
    String[] name;

    //日付を取得する変数
    Calendar calendar;

    //DBに使う変数
    MemoryDB memoryDB;
    SQLiteDatabase db;
    Cursor cursor;

    //通知に使う変数
    NotificationManager notificationManager;
    Notification notification;
    Intent intent;
    PendingIntent pendingIntent;

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

        //DB関係処理
//        memoryDB = new MemoryDB(this.getContext());//インスタンス化
//        db = memoryDB.getReadableDatabase();//DBを読み込みモードで開く

        //日付取得
        calendar = Calendar.getInstance();
//        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));//時
//        String date = String.valueOf(calendar.get(Calendar.DATE));//日

        //SQL文
//        String sql ="select * from memory_db where ";
//        sql += "date='" + date + "' and hour='" + hour + "'";
//        sql += "order by date desc , hour desc";

        //DB処理
//        cursor = db.rawQuery(sql,null);//select結果取得
//        cursor.moveToNext();//参照位置を次へ
//        app = cursor.getFloat(1);
//        service = cursor.getFloat(2);
//        system = cursor.getFloat(3);
//        cursor.close();
//        db.close();
//        memoryDB.close();

        manager = new MemoryManager(this.getContext());
        name =  new String[]{"使用中","未使用"};//項目(５つまで)

        //各種メモリサイズの格納
        total = manager.totalMemory();
        free = manager.freeMemorySize();
//        app = manager.ProcessMemorySize();
//        service = manager.serviceMemory();
//        system = manager.systemMemory();
//        free = ( total - (app+service+system) );

//        data = new float[]{(app / total) * 100, (service / total) * 100, (system / total) * 100,(free / total) * 100};//メモリ使用サイズ(型)
        data = new  float[]{((total-free) / total) * 100,(free / total) * 100};

        str = "全体メモリ(MB):" + Math.ceil(total) + "未使用(MB)" + Math.ceil(free) + "使用(MB)" + Math.ceil(total-free);
//        str = "全体メモリ(MB):" + Math.ceil(total)+"\nアプリ(MB):" + Math.ceil(app);
//        str += "\nサービス(MB):" + Math.ceil(service) +"システ(MB)"+ Math.ceil(system);
//        str += "未使用(MB)" + Math.ceil(free);

        graphs.graphData(name, data);
        graphs.graphSettings(str);
        graphs.graphColors(name.length);
        graphs.strart();

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



