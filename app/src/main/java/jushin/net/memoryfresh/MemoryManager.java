package jushin.net.memoryfresh;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by freedom18 on 2016/01/17.
 */
public class MemoryManager extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */




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



    //unused in memory (Deveroper Mode:本当の空き容量のみ)
    public float freeMemorySize() {

        float flgs = 0;//戻り値の変数

        try {
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

                    try{

                        //必要な情報かを調べる
//                        if (line.indexOf("LowFree") != -1) {
//                            line = line.replaceAll("LowFree", "");
//
//                            line = line.replaceAll(":", "");
//                            line = line.replaceAll("kB", "");
//                            line = line.replaceAll(" ", "");
//
//                            //float型へ変換し足しこむ
//                            flgs += Float.parseFloat(line);
//                        }

                        if(line.indexOf("MemFree") != -1 ){
                            line = line.replaceAll("MemFree", "");
                            line = line.replaceAll(":", "");
                            line = line.replaceAll("kB", "");
                            line = line.replaceAll(" ", "");
                            //float型へ変換し足しこむ
                            flgs += Float.parseFloat(line);

                        }

//                        if (line.indexOf("SwapFree") != -1) {
//                            line = line.replaceAll("SwapFree", "");
//
//                            line = line.replaceAll(":", "");
//                            line = line.replaceAll("kB", "");
//                            line = line.replaceAll(" ", "");
//
//                            //float型へ変換し足しこむ
//                            flgs += Float.parseFloat(line);
//                        }

                        if(line.indexOf("Active") != -1 ){
                            line = line.replaceAll("Active", "");
                            line = line.replaceAll(":", "");
                            line = line.replaceAll("kB", "");
                            line = line.replaceAll(" ", "");
                            //float型へ変換し足しこむ
                            test += Float.parseFloat(line);

                        }

                        if(line.indexOf("Inactive(file)") != -1 ){
                            line = line.replaceAll("Inactive", "");
                            line = line.replaceAll("file", "");
                            line = line.replaceAll(":", "");
                            line = line.replaceAll("kB", "");
                            line = line.replaceAll(" ", "");
                            line = line.replaceAll("\\(", "");
                            line = line.replaceAll("\\)", "");
                            //float型へ変換し足しこむ
                            flgs += (test/Float.parseFloat(line));

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
                Log.d("MemoryView", line);


                String test = "";
                String test1 = "";



                if (line.contains("Active(anon)")) {

                    line = line.replaceAll("Active", "");
                    line = line.replaceAll("anon", "");
                    line = line.replaceAll(":", "");
                    line = line.replaceAll("kB", "");
                    line = line.replaceAll(" ", "");
                    line = line.replaceAll("\\(", "");
                    test += line.replaceAll("\\)", "");


//                if(line.indexOf("Active") != -1){
//
//                    line = line.replaceAll("Active", "");
//                    line = line.replaceAll(":", "");
//                    line = line.replaceAll("kB", "");
//                    test1 = line.replaceAll(" ", "");

                    flgs += Float.parseFloat(test);

                }
            }
        } catch (Exception e) {

        }
        return flgs;
    }

}
