package jushin.net.memoryfresh.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.MainActivity;
import jushin.net.memoryfresh.database.ProcessManageDBHelper;
import jushin.net.memoryfresh.memory.MemoryManager;
import jushin.net.memoryfresh.services.MyService01;
import jushin.net.memoryfresh.services.MyService02;
import jushin.net.memoryfresh.services.MyService03;
import jushin.net.memoryfresh.services.MyService04;
import jushin.net.memoryfresh.services.MyService05;
import jushin.net.memoryfresh.services.MyService06;
import jushin.net.memoryfresh.services.MyService07;
import jushin.net.memoryfresh.services.MyService08;
import jushin.net.memoryfresh.services.MyService09;
import jushin.net.memoryfresh.services.MyService10;

public class MemoryFreshService extends Service {

    private final String TAG = "MemoryFreshService";
    private Timer timer;
    SharedPreferences pref;
    int killintercal;

    private Timer mainTimer;					//タイマー用
    private MainTimerTask mainTimerTask;		//タイマタスククラスー
    private int count = 0;						//カウント
    private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ


    NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //タイマーインスタンス生成
        this.mainTimer = new Timer();
        //タスククラスインスタンス生成
        this.mainTimerTask = new MainTimerTask();
        //タイマースケジュール設定＆開始
        try{
            if(pref.getInt("timer_kill", 0) != 0){
                this.mainTimer.schedule(mainTimerTask, 0,pref.getInt("timer_kill", 0));
            }

        }catch (Exception e){

        }

        return START_STICKY;
    }


    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            //ここに定周期で実行したい処理を記述します
            mHandler.post( new Runnable() {
                public void run() {

                    //メモリ解放処理
                    Intent intent = new Intent(MemoryFreshService.this, MyService01.class);
                    startService(intent);
                    Intent intent1 = new Intent(MemoryFreshService.this, MyService02.class);
                    startService(intent1);
                    Intent intent2 = new Intent(MemoryFreshService.this, MyService03.class);
                    startService(intent2);
                    Intent intent3 = new Intent(MemoryFreshService.this, MyService04.class);
                    startService(intent3);
                    Intent intent4 = new Intent(MemoryFreshService.this, MyService05.class);
                    startService(intent4);

                    Intent intent5 = new Intent(MemoryFreshService.this, MyService06.class);
                    startService(intent5);
                    Intent intent6 = new Intent(MemoryFreshService.this, MyService07.class);
                    startService(intent6);
                    Intent intent7 = new Intent(MemoryFreshService.this, MyService08.class);
                    startService(intent7);
                    Intent intent8 = new Intent(MemoryFreshService.this, MyService09.class);
                    startService(intent8);
                    Intent intent9 = new Intent(MemoryFreshService.this, MyService10.class);
                    startService(intent9);


                }
            });
        }
    }

//    //サービス起動のたびに呼び出される
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        super.onStartCommand(intent, flags, startId);
//
//        timer = new Timer();
//
//        pref= getSharedPreferences("user_data", MODE_PRIVATE);
//        killintercal = pref.getInt("timer_kill", 10001);
//
//        //非同期、別スレッド定期的に実行する処理
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                if(pref.getInt("timer_kill", 0) != 0){
//
//                    // 特定のアプリが起動中にメモリの解放を行なう。
//                    List<String> packageNameList = new ArrayList<String>();
//
//                    // データベースから取得する
//                    SQLiteDatabase sqLiteDatabase;
//                    ProcessManageDBHelper dbHelper = new ProcessManageDBHelper(getApplicationContext());
//                    sqLiteDatabase = dbHelper.getReadableDatabase();
//                    //packageNameList.addAll();
//
//
//                    // メモリの解放を必要とするか確認する
//                    boolean needFresh = false;
//
//                    MemoryManager memoryManager = new MemoryManager();
//
//                    // 起動中のパッケージ名を取得する
//                    List<String> runningPackageList = memoryManager.getRunningPackageName();
//                    for (String packageName : runningPackageList) {
//                        if (packageNameList.contains(packageName)) {
//                            needFresh = true;
//                            break;
//                        }
//                    }
//
//                    // 必要に応じてメモリの解放を行なう。
//                    if (needFresh) {
//                        memoryManager.killProcessWithinList(packageNameList);
//                    }
//                }
//
//            }
//        }, 0, 1000);
//
//        return START_STICKY;
//    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mNM.cancel(1);

        //タイマーをキャンセル

        // タイマー停止
        if( mainTimer != null ){
            mainTimer.cancel();
            mainTimer = null;
        }


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //ユーザー操作により[設定 > アプリ > 実行中]から Service が停止された場合に再起動させる
        //設定画面で向こうにしている場合はは再起動しない
        if (prefs.getBoolean("service_switch", true)) {
            startService(new Intent(this, MemoryFreshService.class));
        }


    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void showNotification() {
        Log.d("Debug TEST", "showNotification");
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0,
                new Intent(this, MainActivity.class), 0);

        Notification notif = new Notification.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getText(R.string.servicestarted))
                .setSmallIcon(R.drawable.memorybutton)
                .setContentIntent(contentIntent)
                .build();
        //常駐させる
        notif.flags = Notification.FLAG_ONGOING_EVENT;
        mNM.notify(1, notif);
    }
}
