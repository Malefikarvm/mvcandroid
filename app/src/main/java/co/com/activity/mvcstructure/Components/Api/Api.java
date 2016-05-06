package co.com.activity.mvcstructure.Components.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import co.com.activity.mvcstructure.Components.Json.p.JsonP;

/**
 * Created by Analista on 02/05/2016.
 */
public class Api {

    private String url;

    private Context context;

    public Api(String url, Context cntxt) {
        context = cntxt;
        this.url = url;
    }

    public String callService(String... params) {
        String partialUrl = url;
        HttpURLConnection conn;
        String jsonString = "";
        for(String param : params) {
            partialUrl += "&" + param;
        }
        try {
            URL finalUrl = new URL(partialUrl);
            Log.i("URL",  partialUrl);
            URLConnection urlConn = (URLConnection) finalUrl.openConnection();
            conn = (HttpURLConnection) urlConn;
            conn.setRequestMethod("GET");
            AsyncUrl async = new AsyncUrl(conn);
            jsonString = async.execute().get();

            JsonP jsonP = new JsonP(jsonString);
            JSONObject jsonObject = jsonP.parseJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "ERROR";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private class AsyncUrl extends AsyncTask<Void, Void, String> {

        private HttpURLConnection conn;

        public AsyncUrl(HttpURLConnection conn) {
            this.conn = conn;
        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonString = "";
            try {
                conn.connect();
                BufferedReader rd = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                jsonString = rd.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonString;
        }
    }
}
