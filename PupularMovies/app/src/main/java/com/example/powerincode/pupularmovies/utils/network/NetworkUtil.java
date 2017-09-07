package com.example.powerincode.pupularmovies.utils.network;

import android.os.AsyncTask;

import com.example.powerincode.pupularmovies.utils.network.models.BaseModel;
import com.example.powerincode.pupularmovies.utils.network.models.Request;

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

public class NetworkUtil {


    public final static NetworkUtil shared = new NetworkUtil();

    public <T extends BaseModel> void execute(Request request, Class<T> modelClass, Action<T> task) {
        new NetworkAsyncTask<>(modelClass, task)
                .execute(request);
    }

    public static Request getPopularMovies() {
        return new Request("discover/movie");
    }

    private class NetworkAsyncTask<T extends Action<V>, V extends BaseModel> extends AsyncTask<Request, Void, String> {
        private Class<V> mModelClass;
        private T mCallback;

        private NetworkAsyncTask(Class<V> modelClass, T cb) {
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
        protected String doInBackground(Request... requests) {
            try {
                URL url = requests[0].getURL();
                URLConnection connection = url.openConnection();
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("/A");

                if (scanner.hasNext()) {
                    return scanner.next();
                } else {
                    throw new Exception("Empty response from server.");
                }
            } catch (Exception e) {
                callError(e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            if (jsonString == null) {
                return;
            }

            Object json;
            try {
                json = new JSONTokener(jsonString).nextValue();

                V result = null;

                if (json != null) {
                    if (json instanceof JSONObject) {
                        result = mModelClass.newInstance();
                        result.parse(jsonString);
                    }
                }

                if (mCallback != null) {
                    mCallback.complete(result);
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

    public interface Action<T> {
        void start();

        void complete(T result);

        void error(Exception error);
    }
}
