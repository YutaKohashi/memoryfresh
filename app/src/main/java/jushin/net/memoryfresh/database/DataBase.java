package jushin.net.memoryfresh.database;

/**
 * Created by Yuta on 2016/01/26.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends ContextWrapper {
    private final static String DB_NAME = "MemoryFresh.db";
    private final static String DB_TABLE = "memoryfresh";
    private final static String DB_COLUMUS =
            "(_id integer primary key autoincrement, processname text, " +
                    "processsize integer, year integer,date string";
    private final static int DB_VERSION = 1;
    private SQLiteDatabase mySQLiteDatabase = null;

    //コンストラクタ
    public DataBase(Context base) {
        super(base);
        mySQLiteDatabase = new DBHelper(base).getWritableDatabase();
    }

    //データベースクローズ
    public void closeDB() {
        mySQLiteDatabase.close();
        mySQLiteDatabase = null;
    }

    //データ読込
    public String[] readDB(String key) {
        String[] columus = new String[] {"_id", "processname", "processsize","date"};
        Cursor cursor = mySQLiteDatabase.query
                (DB_TABLE, columus,  "item1='" + key + "'", null, null, null, null);
        if (cursor.moveToFirst()) {
            columus[0] = cursor.getString(0);
            columus[1] = cursor.getString(1);
            columus[2] = cursor.getString(2);
            columus[3] = cursor.getString(3);
        } else {
            columus = null;
        }
        cursor.close();
        return columus;
    }

    //データ更新
    public void updateDB(String[] columus) {
        ContentValues values = new ContentValues();
        values.put( "_id", columus[0]);
        values.put( "processname", columus[1]);
        values.put( "processsize", columus[2]);
        values.put( "date", columus[2]);
        if (mySQLiteDatabase.update
                (DB_TABLE, values, "_id='" + columus[0] + "'", null) == 0) {
            mySQLiteDatabase.insert(DB_TABLE, null, values);
        }
    }

    //データ削除　オーバーロード
    public void deleteDB() {
        //全件削除
        mySQLiteDatabase.delete(DB_TABLE, null, null);
    }
    public void deleteDB(String key) {
        //１件削除
        mySQLiteDatabase.delete(DB_TABLE,  "_id='" + key + "'", null);
    }

    //データ件数取得
    public int getCount() {
        int count = 0;
        Cursor cursor = null;
        String[] columus = new String[] {"_id", "processname", "processsize","date"};
        cursor = mySQLiteDatabase.query
                (DB_TABLE, columus,  null, null, null, null, null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }

    //インナークラス　データベースヘルパー
    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL
                    ("CREATE TABLE IF NOT EXISTS " + DB_TABLE +  " " + DB_COLUMUS);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }
}