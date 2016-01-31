package jushin.net.memoryfresh.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.widget.ImageView;

import java.util.Timer;

public class MyService01 extends Service {

    public MyService01() {
    }

    @Override
    public void onCreate(){

    }

    Drawable drawable;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        for(int i = 0; i < 1000;i++){
            //ImageView img = new ImageView();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




}
