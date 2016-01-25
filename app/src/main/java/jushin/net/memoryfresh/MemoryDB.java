package jushin.net.memoryfresh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by freedom18 on 2016/01/19.
 */
public class MemoryDB extends SQLiteOpenHelper{

    static final int VER = 1;
    static final String DB_NAME = "MemoryDatabase.db";


    public MemoryDB(Context c){
        super(c,DB_NAME,null,VER);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql;//DBのSQL文

        //sql文作成
        sql= "create table memory_db(";
        sql += "_id integer primary key autoincrement,";
        sql += "app real,";
        sql += "service real,";
        sql += "system real,";
        sql += "year integer,";
        sql += "month integer,";
        sql += "date integer,";
        sql += "hour integer";
        sql +=");";

        //sql文実行
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }


}
