package jushin.net.memoryfresh.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sakamotoyuuta on 16/01/30.
 */
public class ProcessManageDBHelper extends SQLiteOpenHelper {
    static final String DB = "memory_fresh.db";
    static  final int DB_VERSION = 1;
    static  final String CREATE_TABLE = "CREATE TABLE freshtable " +
            "( _id INTEGER PRIMARY KEY autoincrement, package text not null);";
    static final String DROP_TABLE = "DROP TABLE freshtable";

    public ProcessManageDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ProcessManageDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public ProcessManageDBHelper(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //データベースに格納しているパッケージ名をリストで返す
    public ArrayList<String> slectCheckBoxEnables (Context context){

        ArrayList<String> packageName = new ArrayList<String>();

        SQLiteDatabase database;
        ProcessManageDBHelper dbHelper = new ProcessManageDBHelper(context);
        database = dbHelper.getReadableDatabase();

        String sql = "SELECT package FROM freshtable;";
        //SQL文の実行
        Cursor cursor = database.rawQuery(sql , null);

        //カーソル開始位置を先頭にする
        cursor.moveToFirst();


        //（.moveToFirstの部分はとばして）for文を回す
        for (int i = 1; i <= cursor.getCount(); i++) {
            //SQL文の結果から、必要な値を取り出す
            packageName.add(cursor.getString(0));
            cursor.moveToNext();
        }

        return packageName;
    }

}
