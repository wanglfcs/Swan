package com.pp.wang.swan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import winterwell.jtwitter.Twitter;

/**
 * Created by wang on 1/6/15.
 */
public class TwitterUtils {
    private Twitter twitter;
    Context context;
    public TwitterUtils(Context context)
    {
        this.context = context;
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
}
