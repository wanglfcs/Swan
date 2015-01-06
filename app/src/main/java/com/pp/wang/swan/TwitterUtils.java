package com.pp.wang.swan;

import winterwell.jtwitter.Twitter;

/**
 * Created by wang on 1/6/15.
 */
public class TwitterUtils {
    private Twitter twitter;

    public Twitter getTwitter() {
        if (twitter == null)
        {
            String apiRoot = "http://yamba.marakana.com";
            twitter = new Twitter("wang", "loveping");
            twitter.setAPIRootUrl(apiRoot);
        }

        return twitter;
    }
}
