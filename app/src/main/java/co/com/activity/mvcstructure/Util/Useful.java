package co.com.activity.mvcstructure.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import co.com.activity.mvcstructure.Util.Async.Tasks.ValidateService;
import co.com.activity.mvcstructure.Util.Connection.Connection;

/**
 * Created by Analista on 12/09/2015.
 */
public class Useful  {
    private Context context;
    private Connection conn;
    private SQLiteDatabase db;
    private ValidateService vs;
    public static final DecimalFormat  FORMATTER = new DecimalFormat("#.##");

    public Useful(Context cntxt){
        context = cntxt;
        conn = new Connection(context);
        db = conn.getReadableDatabase();
        vs = new ValidateService();
    }

    public boolean hasService(){
        boolean status = false;
        try {
            status = vs.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return status;
    }

    public SQLiteDatabase dbConn(){
        return db;
    }
}
