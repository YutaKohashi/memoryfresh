package jushin.net.memoryfresh.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService09 extends Service {
    public MyService09() {
    }

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

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
