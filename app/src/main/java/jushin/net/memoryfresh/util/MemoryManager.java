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





    //unused in memory(Android 5.1)
    public float freeSizeVer5_1() {

        float flgs = 0;//戻り値の変数
        float flgs2 = 0;
        float flgs3 = 0;
        float flgs4 = 0;


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
//                        if (line.indexOf("LowFree") != -1) {
//                            line = line.replaceAll("LowFree", "");
//                            line = line.replaceAll(":", "");
//                            line = line.replaceAll("kB", "");
//                            line = line.replaceAll(" ", "");
//
//                            //float型へ変換し足しこむ
//                            flgs += Float.parseFloat(line);
//                        }

                    if(line.indexOf("MemFree") != -1 || line.indexOf("Cached") != -1){
                        line = line.replaceAll("Cached", "");
                        line = line.replaceAll("MemFree", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");
                        //float型へ変換し足しこむ
                        flgs += Float.parseFloat(line);

                    }

//                        if (line.indexOf("SwapFree") != -1) {
//                            line = line.replaceAll("SwapFree", "");
//                            line = line.replaceAll(":", "");
//                            line = line.replaceAll("kB", "");
//                            line = line.replaceAll(" ", "");
//
//                            //float型へ変換し足しこむ
//                            flgs += Float.parseFloat(line);
//                        }

                    if (line.indexOf("Buffers") != -1) {
                        line = line.replaceAll("Buffers", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");

                        //float型へ変換し足しこむ

                        flgs2 += Float.parseFloat(line);
                    }

                    if (line.indexOf("Inactive(anon)") != -1) {
                        line = line.replaceAll("Inactive", "");
                        line = line.replaceAll("anon", "");
                        line = line.replaceAll("\\(", "");
                        line = line.replaceAll("\\)", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");

                        //float型へ変換し足しこむ
                        flgs2 += Float.parseFloat(line);
                    }

                    if (line.indexOf("(file)") != -1) {
                        line = line.replaceAll("Inactive", "");
                        line = line.replaceAll("file", "");
                        line = line.replaceAll("Active", "");
                        line = line.replaceAll("\\(", "");
                        line = line.replaceAll("\\)", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");

                        //float型へ変換し足しこむ

                        flgs3 += Float.parseFloat(line);
                    }

                    if(line.indexOf("Active") != -1){

                        line = line.replaceAll("Active", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");

                        flgs4 += Float.parseFloat(line);

                    }

                }catch (Exception e){

                }
            }
        } catch (Exception e) {

            Log.i("MemoryException", e.toString());
        }
        return ( ( (flgs - flgs2) + (flgs4 - flgs3))/1000 );
    }

    public float defaultFreeMem(){

        float flgs = 0;//戻り値の変数
//        float flgs2 = 0;

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
                    if (line.indexOf("Cached") != -1 || line.indexOf("Buffers") != -1
                            ||line.indexOf("MemFree") != -1) {
                        line = line.replaceAll("Cached", "");
                        line = line.replaceAll("Buffers", "");
                        line = line.replaceAll("MemFree", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");
                        flgs += Float.parseFloat(line);
                    }


                }catch (Exception e){

                }
            }
            br.close();
        } catch (Exception e) {
            Log.d("MemLog",e.toString());
        }
        return ((flgs)/1000);

    }

    public float[] defaultFreeMem(int number){

        float[] flgs = new float[number];//戻り値の変数

        try {
            //Linuxコマンドでメモリ情報を取得
            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");

            //取得した情報をバッファに格納
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //メモリの値を格納
            String line = null;
            int i = 0;

            //情報の分だけ繰り返し
            while ((line = br.readLine()) != null) {


                try{
                    if (line.indexOf("Cached") != -1 || line.indexOf("Buffers") != -1
                            ||line.indexOf("MemFree") != -1) {
                        line = line.replaceAll("Cached", "");
                        line = line.replaceAll("Buffers", "");
                        line = line.replaceAll("MemFree", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("kB", "");
                        line = line.replaceAll(" ", "");
                        flgs[i] = (Float.parseFloat(line) / 1000);
                    }

//

                }catch (Exception e){

                }
                i++;
            }
        } catch (Exception e) {

        }
        return ((flgs));

    }









    //MemoryLogView
    public void logInfo(){

        Log.d("MemoryView", "Version" +String.valueOf(Build.VERSION.SDK_INT));

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

        Log.d("MemoryView", "                                               ");
        Log.d("MemoryView", "                                               ");
    }



}
