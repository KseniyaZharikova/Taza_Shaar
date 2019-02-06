package com.tazashaar.kseniya.zerowaste.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tazashaar.kseniya.zerowaste.R;
import com.tazashaar.kseniya.zerowaste.ZeroWasteApp;
import com.tazashaar.kseniya.zerowaste.data.ReceptionPoint;
import com.tazashaar.kseniya.zerowaste.interfaces.MainInterface;
import com.tazashaar.kseniya.zerowaste.ui.presenters.MainPresenter;

import java.util.List;


public class SplashActivity extends BaseActivity implements MainInterface.View {

    private MainInterface.Presenter mainPresenter;

    @Override
    protected int getViewLayout() {
        return R.layout.activity_splash;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = new MainPresenter(ZeroWasteApp.get(this).getSqLiteHelper());
        mainPresenter.bind(this);
        mainPresenter.checkNetwork(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void startActivity() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        },2000);

    }

    @Override
    public void dialogNoInternet() {
        showSimpleAlert();
    }


    @Override
    public void cameraUpdate(double lat, double lng) {

    }

    @Override
    public void showMyCurrentLocation(Double lat, Double lng) {

    }

    @Override
    public void drawReceptionPoints(List<ReceptionPoint> pointFromDatabase) {

    }

    @Override
    public void clearAllMarkersAndDrawNew(List<ReceptionPoint> list) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.unbind();
    }
}