package com.kseniya.tazar;

import android.app.Application;
import android.content.Context;

import com.kseniya.tazar.data.db.SQLiteHelper;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class ZeroWasteApp extends Application {

private static SQLiteHelper sqLiteHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
    }

    public static ZeroWasteApp get(Context context){
        return (ZeroWasteApp) context.getApplicationContext();
    }
    public SQLiteHelper getSqLiteHelper() {
        return sqLiteHelper;
    }
}
