package jushin.net.memoryfresh.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import jushin.net.memoryfresh.service.MemoryFreshService;

/**
 * Created by Yuta on 2016/01.
 */
public class MainBroadReceiver extends BroadcastReceiver{
    SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {

        pref = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean isEnableBootService = pref.getBoolean("service_boot_switch",false);
        boolean isEnableService = pref.getBoolean("service_switch",false);

        //端末起動時にサービスを起動する
       //BOOTCPMPLEATED
        //設定項目で起動時にサービス開始を設定している場合
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) && isEnableBootService && isEnableService){
            Log.d("Debug TEST", "Debug START");

            context.startService(new Intent(context, MemoryFreshService.class));
        }
    }


}

