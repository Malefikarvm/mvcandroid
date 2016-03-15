package co.com.activity.mvcstructure.Util.Async.Tasks;

import android.os.AsyncTask;

import co.com.activity.mvcstructure.Util.WebService.WebService;

/**
 * Created by Analista on 14/09/2015.
 */
public class ValidateService extends AsyncTask<Void, Void, Boolean> {

    private WebService ws;

    public ValidateService(){
        ws = new WebService();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return ws.validateService();
    }
}
