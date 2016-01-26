package jushin.net.memoryfresh;

import android.app.Activity;
import android.app.assist.AssistContent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;

import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class SubActiviry extends Activity {

    //DBに使う変数
    MemoryDB memoryDB;
    SQLiteDatabase db;
    ContentValues values;

    //日付に使う変数
    Calendar calendar;

    //メモリを取得する変数
    MemoryManager memoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sub_activiry);

        //インスタンス化
        memoryManager = new MemoryManager(this);
        memoryDB = new MemoryDB(this);
        values = new ContentValues();

        //日付取得
        calendar = Calendar.getInstance();

        //DBを書き込みモードで開く
        db =  memoryDB.getWritableDatabase();

        //DBに格納する値を登録
        values.put("app",memoryManager.ProcessMemorySize());
        values.put("service",memoryManager.serviceMemory());
        values.put("system",memoryManager.systemMemory());
        values.put("year",calendar.get(Calendar.YEAR));//年
        values.put("month",calendar.get(Calendar.MONTH));//月
        values.put("date",calendar.get(Calendar.DATE));//日
        values.put("hour",calendar.get(Calendar.HOUR_OF_DAY));//時間

        db.insert("memory_db",null,values);


    }

}
