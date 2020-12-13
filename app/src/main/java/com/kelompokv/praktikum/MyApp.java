package com.kelompokv.praktikum;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class MyApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Stetho.initializeWithDefaults(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this).
                enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).
                enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());
    }

    public static Context getmContext() {
        return mContext;
    }
}
