package com.kapouter.konik.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.kapouter.konik.BuildConfig;
import com.kapouter.konik.R;
import com.kapouter.konik.util.Cache;

public class App extends Application {

    private static App sInstance;
    private final Cache mCache = new Cache();
    private SharedPreferences mSharedPreferences;

    public static App getInstance() {
        return sInstance;
    }

    public static void setInstance(App instance) {
        sInstance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        // SharedPreferences
        mSharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(getApplicationContext().getString(R.string.skip_sign_in), false).commit();
    }

    public static Cache getCache() {
        return sInstance.mCache;
    }

    public static SharedPreferences getSharedPreferences() {
        return sInstance.mSharedPreferences;
    }

    public static SharedPreferences.Editor editPreferences() {
        return sInstance.mSharedPreferences.edit();
    }

}
