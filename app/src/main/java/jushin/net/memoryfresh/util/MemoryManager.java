package jushin.net.memoryfresh.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by freedom18 on 2016/01/17.
 */
public class MemoryManager extends Activity{

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
        return (flgs/1000);
    }


    public float freeMem(){
        float flg  = 0;

        //SDK(Androidのversion)で分岐
        switch (Build.VERSION.SDK_INT){

            case 21://(android 5.0.2)
                flg = freeSizeVer5_0();
                break;
            case 22://(Android 5.1.1)
                flg = freeSizeVer5_1();
                break;
            default:
                flg =  (totalMemory() - kUseMemory());
        }
        return flg;
    }

    public float useMem(){

        float flg = 0;
        //SDK(Androidのversion)で分岐
        switch (Build.VERSION.SDK_INT){

            case 21://(android 5.0.2)
                flg = freeSizeVer5_0();
                break;
            case 22://(Android 5.1.1)
                flg = freeSizeVer5_1();
                break;
            default:
                flg = kUseMemory();
        }

        return flg;
    }


    //unused in memory(Android 5.0)
    private float freeSizeVer5_0(){

        return useMem5_0();
    }

    //unused in memory(Android 5.1)
    public float freeSizeVer5_1() {

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

                try{

                    //必要な情報かを調べる
                    if (line.indexOf("LowFree") != -1) {
                        line = line.replaceAll("LowFree", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");

                        //float型へ変換し足しこむ
                        flgs += Float.parseFloat(line);
                    }

                    if(line.indexOf("MemFree") != -1 ){
                        line = line.replaceAll("MemFree", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");
                        //float型へ変換し足しこむ
                        flgs += Float.parseFloat(line);

                    }

                    if (line.indexOf("SwapFree") != -1) {
                        line = line.replaceAll("SwapFree", "");
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
        return (flgs/1000);
    }


    public float useMem5_0(){
        float flg = 0;//戻り値の変数

        try {
            //Linuxコマンドでメモリ情報を取得
            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");

            //取得した情報をバッファに格納
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //メモリの値を格納
            String line = null;

            //情報の分だけ繰り返し
            while ((line = br.readLine()) != null) {

                if(line.indexOf("Active") != -1){

                    line = line.replaceAll("Active", "");
                    line = line.replaceAll(":", "");
                    line = line.replaceAll("kB", "");
                    line = line.replaceAll(" ", "");

                    flg += Float.parseFloat(line);

                }
            }
        } catch (Exception e) {

        }
        return flg/1000;
    }

    public float useMem5_1(){
        float flg = 0;

        return flg;
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

                if (line.contains("Active(anon)")) {

                    line = line.replaceAll("Active", "");
                    line = line.replaceAll("anon", "");
                    line = line.replaceAll(":", "");
                    line = line.replaceAll("kB", "");
                    line = line.replaceAll(" ", "");
                    line = line.replaceAll("\\(", "");
                    line += line.replaceAll("\\)", "");

                    flgs += Float.parseFloat(line);
                }
            }
        } catch (Exception e) {

        }
        return flgs;
    }

    //小橋君の使ってるメモリの取得
    public float kUseMemory(){

        float flg = 0;

        List<AndroidAppProcess> processes = ProcessManager.getRunningAppProcesses();

        try{

            for (AndroidAppProcess app : processes){

                flg += app.statm().getResidentSetSize();
            }

        }catch (Exception e){
            Log.d("Erorr",e.toString());

        }
        return flg/1000/1000;
    }



    //MemoryLogView
    public void logInfo(){


        //MemoryLog(cat /proc/meminfo)
        try{
            //Linuxコマンドでメモリ情報を取得
            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");

            //取得した情報をバッファに格納
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            float test = 0;
            //メモリの値を格納
            String line = null;

            //情報の分だけ繰り返し
            while ((line = br.readLine()) != null) {

                Log.d("MemoryView", line);
            }
        }catch (Exception e){
        }

        Log.d("MemoryView", "                                               ");
        Log.d("MemoryView", "                                               ");

        //MemoryLog(adb shell dumpsys meminfo)
        try{
            //Linuxコマンドでメモリ情報を取得
            Process process = Runtime.getRuntime().exec("adb shell dumpsys meminfo");

            //取得した情報をバッファに格納
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            float test = 0;
            //メモリの値を格納
            String line = null;

            //情報の分だけ繰り返し
            while ((line = br.readLine()) != null) {

                Log.d("MemoryView", line);
            }
        }catch (Exception e){
        }
    }

}
