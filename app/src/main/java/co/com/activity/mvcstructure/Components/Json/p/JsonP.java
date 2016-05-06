package co.com.activity.mvcstructure.Components.Json.p;

import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static org.json.JSONObject.wrap;

/**
 * Created by Analista on 02/05/2016.
 */
public class JsonP {

    private String jsonString;

    public JsonP(String json) {
        jsonString = json;
    }

    public JSONObject parseJson(){
        return (JSONObject) jsonDeserialize(jsonString);
    }

    private Object jsonDeserialize(String jsonS) {
        Object jsonOA = null;
        try {

            switch (jsonString.charAt(1)) {
                case '{' :
                    jsonOA = getObject(jsonS);
                    JSONObject jsonO = ((JSONObject)jsonOA);
                    Iterator<?> keys = jsonO.keys();

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String jsonHelper = jsonO.get(key).toString();
                        if (!jsonHelper.equals("")) {
                           jsonO.put(key, jsonDeserialize(jsonHelper));
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        return wrap(jsonO);
                    }
                case '[' :
                    jsonOA = getArray(jsonS);
                    JSONArray jsonA = ((JSONArray)jsonOA);

                    for (int i = 0; i < jsonA.length(); i++) {
                        String jsonHelper = jsonA.get(i).toString();
                        if (!jsonHelper.equals("")) {
                            jsonA.put(i, jsonDeserialize(jsonHelper));
                        }
                    }
                    return jsonA;
                default :
                    return jsonOA;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wrap(jsonOA);
    }

    private JSONObject getObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getArray(String json) {
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
