package co.com.activity.mvcstructure;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Hashtable;

import co.com.activity.mvcstructure.Components.Api.Api;
import co.com.activity.mvcstructure.Models.Test;
import co.com.activity.mvcstructure.Util.Useful;

public class MainActivity extends AppCompatActivity {

    private final Context CONTEXT = MainActivity.this;
    private Useful use;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Intent intent = new Intent(CONTEXT, LoginActivity.class);
        intent.putExtra("Saludo", "Hola Mundo");
        startActivity(intent);*/
        use = new Useful(CONTEXT);
        test = new Test(use.dbConn());
        test.insertIntoField("description", "some");
        Hashtable result = test.searchAll();
        Hashtable data  = (Hashtable) result.get(0);
        System.out.println("DATO: "+data.get("_id"));
        System.out.println("DATO: " + data.get("description"));
        System.out.println(test.search("_id", "description", 142093));

        test.insertIntoField("description", "love");

        test.updateRecord("description", "hello world", 2);

        Api api = new Api("http://unilever.activitymovil.net/api/?", CONTEXT);

        api.callService("fnctn=preguntas_detallado", "tkn=77surAct53", "gln=7701001002804");
        //Intent intent = new Intent(CONTEXT, LoginActivity.class);
        //startActivity(intent);
        //android.os.Process.killProcess(android.os.Process.myPid());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case (R.id.action_settings):
                Toast.makeText(CONTEXT,R.string.action_settings,Toast.LENGTH_LONG).show();
                return true;
            case (R.id.action_exit):
                Toast.makeText(CONTEXT,R.string.action_exit,Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
