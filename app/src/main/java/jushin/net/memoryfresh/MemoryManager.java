package jushin.net.memoryfresh;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by freedom18 on 2016/01/17.
 */
public class MemoryManager extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


//    ActivityManager activityManager;
//    ActivityManager.MemoryInfo memoryInfo;

    public MemoryManager(Context context) {

//        activityManager = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE);
//        memoryInfo = new ActivityManager.MemoryInfo();
    }

    //memory max size
    public float totalMemory() {

        float flgs = 0;//戻り値の変数

        try {
            //Linuxコマンドでメモリ情報を取得
            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");

            //取得した情報をバッファに格納
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //メモリの値を格納
            String line = null;

            //情報の分だけ繰り返し
            while ((line = br.readLine()) != null) {

                if (line.indexOf("MemTotal") != -1) {
                    line = line.replaceAll("MemTotal", "");
                    line = line.replaceAll(":", "");
                    line = line.replaceAll("kB", "");
                    line = line.replaceAll(" ", "");
                    float test = Float.parseFloat(line);
                    flgs = test;
                }

            }
        } catch (Exception e) {

        }
        return (flgs);
    }

//    public float systemMemory(){
//
//        activityManager.getMemoryInfo(memoryInfo);
//
//        return (float)(memoryInfo.availMem/1000)/1000;
//    }

    //service use in memory
//    public float serviceMemory(){
//
//        float flg = 0;//戻り値の変数
//        int i = 0;
//        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(100);
//
//        int pids[] = new int[services.size()];//プロセスのidを格納する変数
//
//        if (services != null) {
//
//
//            //serviceの数だけ繰り返し
//            for(ActivityManager.RunningServiceInfo service:services){
//                pids[i] = service.pid;//process idの取得
//                i++;
//            }
//
//
//        }
//
//        //service idから使用中memoryを取得
//        android.os.Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(pids);
//
//        //memory情報の数だけ繰り返し
//        for(android.os.Debug.MemoryInfo memory : memoryInfos){
//
//            flg += (float)memory.getTotalPss();
//
//        }
//        return flg /8/1000;
//    }

    //process use in memory
//    public float ProcessMemorySize(){
//
//        float flg = 0;//戻り値の変数
//
//        //デバイスのプロセスを取得
//        List<ActivityManager.RunningAppProcessInfo> runningApps = activityManager.getRunningAppProcesses();
//
//
//        int pids[] = new int[runningApps.size()];//プロセスのidを格納する変数
//        int i = 0;//配列を操作するための変数
//
//        //プロセスの数だけ実行
//        for(ActivityManager.RunningAppProcessInfo app : runningApps) {
//
//            pids[i] = app.pid;//プロセスidの格納
//            ++i;//increment
//        }
//
//        //process idから使用中memoryを取得
//        android.os.Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(pids);
//
//        //memory情報の数だけ繰り返し
//        for(android.os.Debug.MemoryInfo memory : memoryInfos){
//
//            flg += (float)memory.getTotalPss();
//
//        }
//        return flg /8/1000;
//    }

    //use in memory(Developer Mode:使用中のメモリに加えてprocessが確保している領域も含む)
//    public float MemorySize(){
//
//
//        //戻り値(合計メモリ-未使用メモリ)
//        return (totalMemory() - freeMemorySize());
//    }

    //unused in memory (Deveroper Mode:本当の空き容量のみ)
    public float freeMemorySize() {

        float flgs = 0;//戻り値の変数

        try {
            //Linuxコマンドでメモリ情報を取得
            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");

            //取得した情報をバッファに格納
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //メモリの値を格納
            String line = null;

            //情報の分だけ繰り返し
            while ((line = br.readLine()) != null) {

                    Log.d("MemoryView", line);
                    try{
                        //必要な情報かを調べる
                        if (line.indexOf("Inactive") != -1 || line.indexOf("Cached") != -1 || line.indexOf("MemFree") != -1 ||
                                line.indexOf("Buffers") != -1 ){
                            //不要な文字列の除去
//                            line = line.replaceAll("Inactive", "");
                            line = line.replaceAll("Inactive", "");
//                            line = line.replaceAll("Cached", "");
//                            line = line.replaceAll("MemFree", "");
//                            line = line.replaceAll("Buffers", "");
//                            line = line.replaceAll("SwapFree", "");

                            line = line.replaceAll(":", "");
                            line = line.replaceAll("kB", "");
                            line = line.replaceAll(" ", "");

                            //float型へ変換し足しこむ
                            flgs += Float.parseFloat(line);
                        }
                    }catch (Exception e){

                    }


            }
        } catch (Exception e) {

            Log.i("MemoryException", e.toString());
        }
        return (flgs);
    }


    public float useSize() {

        float flgs = 0;//戻り値の変数
        try {
            //Linuxコマンドでメモリ情報を取得
            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");

            //取得した情報をバッファに格納
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //メモリの値を格納
            String line = null;

            //情報の分だけ繰り返し
            while ((line = br.readLine()) != null) {


                String test = "";
                String test1 = "";



//                if (line.contains("Active(anon)")) {
//
//                    line = line.replaceAll("Active", "");
//                    line = line.replaceAll("anon", "");
//                    line = line.replaceAll(":", "");
//                    line = line.replaceAll("kB", "");
//                    line = line.replaceAll(" ", "");
//                    line = line.replaceAll("\\(", "");
//                    test += line.replaceAll("\\)", "");


                if(line.indexOf("Active") != -1){

                    line = line.replaceAll("Active", "");
                    line = line.replaceAll(":", "");
                    line = line.replaceAll("kB", "");
                    test1 = line.replaceAll(" ", "");
//
                    flgs += Float.parseFloat(test1);

                }
            }
        } catch (Exception e) {

        }
        return flgs;
    }

}
