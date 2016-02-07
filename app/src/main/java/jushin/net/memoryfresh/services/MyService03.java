package jushin.net.memoryfresh.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.MainActivity;
import jushin.net.memoryfresh.memory.MemoryManager;
import jushin.net.memoryfresh.service.MemoryFreshService;

public class MyService03 extends Service {

    NotificationManager mNM;
    public MyService03() {
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("Debug TEST_service3", "onCreate");
//        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        showNotification();
    }
//    private void showNotification() {
//        Log.d("Debug TEST", "showNotification3");
//        Notification notif = new Notification.Builder(this)
//                .setContentTitle("MyService03")
//                .setSmallIcon(R.drawable.ic_launcher)
//
//                .build();
//        //常駐させる
//        notif.flags = Notification.FLAG_ONGOING_EVENT;
//        mNM.notify(1, notif);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // 起動中のアプリを取得する
        //       List<String> runningAppList = new ArrayList<>();

        //チェックされていたアイテムをINSERTする
        final ArrayList<String> checkedArrayList = new ArrayList<String>();

        final MemoryManager memoryManager = new MemoryManager();
//        runningAppList = memoryManager.getRunningPackageName();
//
//        SharedPreferences pref = getSharedPreferences("check", MODE_PRIVATE);
//        for (String packageName : runningAppList) {
//            if (! pref.getString(packageName, "").equals("")) {
//                checkedArrayList.add(packageName);
//            }
//        }

        new Thread() {
            public void run() {
                memoryManager.killProcessWithinList(checkedArrayList);
            }
        }.start();

        new Timer().schedule(new TimerTask()
        {
            public void run()
            {
                stopSelf();
                Log.d("MyService3 : ", "サービス停止");
            }
        }, 12000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Debug TEST_service3", "onDestroy");
//        mNM.cancel(1);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
