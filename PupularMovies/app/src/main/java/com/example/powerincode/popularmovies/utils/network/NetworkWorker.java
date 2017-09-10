package com.example.powerincode.popularmovies.utils.network;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.powerincode.popularmovies.utils.network.models.BaseModel;
import com.example.powerincode.popularmovies.utils.network.services.Actions.Action;
import com.example.powerincode.popularmovies.utils.network.services.Actions.ActionItem;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public class NetworkWorker<T extends Action<V>, V extends BaseModel> extends AsyncTask<Uri, Void, String> {

    private final Class<V> mModelClass;
    private final T mCallback;

    public NetworkWorker(Class<V> modelClass, T cb) {
        mCallback = cb;
        this.mModelClass = modelClass;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mCallback != null) {
            mCallback.start();
        }
    }

    @Override
    protected String doInBackground(Uri... uris) {
        try {
            URL url = new URL(uris[0].toString());
            URLConnection connection = url.openConnection();
            InputStreamReader in = new InputStreamReader(connection.getInputStream());
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("/A");

            if (scanner.hasNext()) {
                return scanner.next();
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }


    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);

        if (jsonString == null) {
            //TODO: Don't wanna pass here context for localizing error message
            callError(new Exception("Connection with server is broken."));
            return;
        }

        Object json;
        try {
            json = new JSONTokener(jsonString).nextValue();

            V result;

            if (json != null) {
                if (json instanceof JSONObject) {
                    result = mModelClass.newInstance();
                    result.parse(jsonString);

                    if (mCallback != null) {
                        ActionItem<V> cb = (ActionItem<V>) this.mCallback;
                        cb.complete(jsonString, result);
                    }
                }
            }
        } catch (Exception e) {
            callError(e);
        }
    }

    private void callError(Exception e) {
        if (mCallback != null) {
            mCallback.error(e);
        }
    }
}
