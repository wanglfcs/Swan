package com.pp.wang.swan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import winterwell.jtwitter.Twitter;

/**
 * Created by wang on 1/6/15.
 */
public class TwitterUtils {
    private Twitter twitter;
    private static final String TAG = "TwitterUtils";
    Context context;
    PostTask postTask;
    public TwitterUtils(Context context)
    {
        this.context = context;
        postTask = new PostTask();
    }

    public Twitter getTwitter() {
        if (twitter == null)
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String username = prefs.getString("settingUserName", null);
            String password = prefs.getString("settingPassword", null);
            String apiRoot = prefs.getString("settingRootApi","http://yamba.marakana.com/api");
            twitter = new Twitter(username, password);
            twitter.setAPIRootUrl(apiRoot);
        }

        return twitter;
    }

    public void postStatus(String msg)
    {
        postTask.execute(msg);
    }

    class PostTask extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            Twitter.Status status;
            try {
                status= getTwitter().updateStatus(params[0]);
            }catch (Exception e){
                Log.e(TAG, e.toString());
                return "faild to post msg";
            }
            return status.getText();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
}
