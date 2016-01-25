package jushin.net.memoryfresh.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yuta on 2016/01/23.
 */
public class MemoryFreshDbOpenHelper extends SQLiteOpenHelper{

    static final String DB = "sqlite_memoryfresh.db";
    static final int DB_VERSION = 1;
    static final String CREATE_TABLE = "create table mytable ( _id integer primary key autoincrement,pid integer not null, data integer not null );";
    static final String DROP_TABLE = "drop table mytable;";

    public MemoryFreshDbOpenHelper(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

//    onUpgradeメソッドではテーブル構造の再構成などの処理
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
