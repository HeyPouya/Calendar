package ir.apptune.calendar;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Pouya on 24/11/2016.
 */

class CallingGoogleWebservice {
    static Boolean result;

    static Boolean getUrl(String url, final Context mContext) {
        AsyncHttpClient client = new SyncHttpClient();
        client.setConnectTimeout(20000);
        client.setTimeout(20000);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                result = true;

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                result = false;
            }
        });

        return result;

    }


}
