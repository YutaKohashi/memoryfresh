package jushin.net.memoryfresh.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.MainActivity;
import jushin.net.memoryfresh.memory.MemoryManager;

public class MemoryFreshService extends Service {

    private final String TAG = "MemoryFreshService";
    private Timer timer;

    NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();
    }

    //サービス起動のたびに呼び出される
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);

        timer = new Timer();
        //非同期、別スレッド定期的に実行する処理
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // 特定のアプリが起動中にメモリの解放を行なう。
                List<String> packageNameList = new ArrayList<String>();

                // データベースから取得する

                // メモリの解放を必要とするか確認する
                boolean needFresh = false;

                MemoryManager memoryManager = new MemoryManager();

                // 起動中のパッケージ名を取得する
                List<String> runningPackageList = memoryManager.getRunningPackageName();
                for (String packageName : runningPackageList) {
                    if (packageNameList.contains(packageName)) {
                        needFresh = true;
                        break;
                    }
                }

                // 必要に応じてメモリの解放を行なう。
                if (needFresh) {
                    memoryManager.killProcessWithinList(packageNameList);
                }


            }
        }, 0, 1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mNM.cancel(1);

        //タイマーをキャンセル
        timer.cancel();

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
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(contentIntent)
                .build();
        //常駐させる
        notif.flags = Notification.FLAG_ONGOING_EVENT;
        mNM.notify(1, notif);
    }
}
