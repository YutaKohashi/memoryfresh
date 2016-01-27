package jushin.net.memoryfresh.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import jushin.net.memoryfresh.service.MemoryFreshService;

/**
 * Created by Yuta on 2016/01.
 */
public class MainBroadReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //端末起動時にサービスを起動する
        Log.d("Debug TEST", "Debug START");
        //BOOTCPMPLEATED
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            context.startService(new Intent(context, MemoryFreshService.class));
        }
    }


}

