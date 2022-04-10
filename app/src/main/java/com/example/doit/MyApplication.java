package com.example.doit;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application {
    private static Context mContext;
    private static SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        String SHARED_PREF = "sharedPref";
        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    public static Context getContext() {
        return mContext;
    }

    public static SharedPreferences getSharedPrefs() {
        return sharedPref;
    }
}
