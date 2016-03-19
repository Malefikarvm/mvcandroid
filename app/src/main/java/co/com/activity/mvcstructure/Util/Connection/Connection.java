package co.com.activity.mvcstructure.Util.Connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Analista on 12/09/2015.
 */
public class Connection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mvcstructure.db";

    public Connection(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE test(_id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT)");
        db.execSQL("CREATE TABLE usuarios(_id INTEGER PRIMARY KEY AUTOINCREMENT, IdUsuario TEXT, Contrasena TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.w("test",
                "Upgrading database, which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS usarios");
        onCreate(db);
    }
}
