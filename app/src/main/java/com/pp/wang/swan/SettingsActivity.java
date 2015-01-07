package com.pp.wang.swan;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by wang on 1/7/15.
 */
public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settingpage);
    }
}
