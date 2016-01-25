package jushin.net.memoryfresh.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import jushin.net.memoryfresh.R;
import jushin.net.memoryfresh.activity.MainActivity;

public class MemoryFreshService extends Service {
    NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.d("Debug TEST", "onCreate");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Debug TEST", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("Debug TEST", "onDestroy");
        super.onDestroy();
        mNM.cancel(1);

        //ユーザー操作により[設定 > アプリ > 実行中]から Service が停止された場合に再起動させる
        startService(new Intent(this, MemoryFreshService.class));
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
