package jushin.net.memoryfresh.util;

import android.content.Context;
import android.content.Intent;

import jushin.net.memoryfresh.services.MyService01;
import jushin.net.memoryfresh.services.MyService02;
import jushin.net.memoryfresh.services.MyService03;
import jushin.net.memoryfresh.services.MyService06;
import jushin.net.memoryfresh.services.MyService07;
import jushin.net.memoryfresh.services.MyService08;

/**
 * Created by Yuta on 2016/02/07.
 */

/**
 * このクラスでサービスの実行処理を記述
 * メモリーの開放を行いときはこのクラスをインスタンス化してメソッドを呼び出す
 */
public class MemoryKillerExecuteManager {

    Intent intent;
    Intent intent1;
    Intent intent2;
    Intent intent3;
    Intent intent4;
    Intent intent5;
    Intent intent6;
    Intent intent7;
    Intent intent8;
    Intent intent9;

    public void killExe(Context context) {
        intent = new Intent(context, MyService01.class);
        context.startService(intent);
        intent1 = new Intent(context, MyService02.class);
        context.startService(intent1);
        intent2 = new Intent(context, MyService03.class);
        context.startService(intent2);
//      intent3 = new Intent(context, MyService04.class);
//      context.startService(intent3);
//      intent4 = new Intent(context, MyService05.class);
//      context.startService(intent4);

        intent5 = new Intent(context, MyService06.class);
        context.startService(intent5);
        intent6 = new Intent(context, MyService07.class);
        context.startService(intent6);
        intent7 = new Intent(context, MyService08.class);
        context.startService(intent7);
//      intent8 = new Intent(MainActivity.this, MyService09.class);
//      context.startService(intent8);
//      intent9 = new Intent(MainActivity.this, MyService10.class);
//      cotext.sstartService(intent9);
    }
}
