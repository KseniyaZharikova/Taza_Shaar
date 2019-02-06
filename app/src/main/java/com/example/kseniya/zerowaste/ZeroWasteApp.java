package com.example.kseniya.zerowaste;

import android.app.Application;
import android.content.Context;

import com.example.kseniya.zerowaste.data.db.SQLiteHelper;
import com.facebook.drawee.backends.pipeline.Fresco;

public class ZeroWasteApp extends Application {

private static SQLiteHelper sqLiteHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        Fresco.initialize(this);

    }

    public static ZeroWasteApp get(Context context){
        return (ZeroWasteApp) context.getApplicationContext();
    }
    public SQLiteHelper getSqLiteHelper() {
        return sqLiteHelper;
    }
}
