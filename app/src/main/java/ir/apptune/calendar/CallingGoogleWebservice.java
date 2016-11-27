package ir.apptune.calendar;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * This class connects to the Internet and sends the event data that user has created inside app
 * to google calendar webServers. it returns true if the response in successful or false in failure
 * situation
 */

class CallingGoogleWebservice {
    private static Boolean result;

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
