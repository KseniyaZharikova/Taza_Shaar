package com.kseniya.tazar.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kseniya.tazar.R;
import com.kseniya.tazar.ZeroWasteApp;
import com.kseniya.tazar.data.ReceptionPoint;
import com.kseniya.tazar.interfaces.MainInterface;
import com.kseniya.tazar.ui.presenters.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity implements MainInterface.View {

    private MainInterface.Presenter mainPresenter;

    @BindView(R.id.splash_logo)
    ImageView splashLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        final Animation animationRotateCenter = AnimationUtils.loadAnimation(
                this, R.anim.rotation);

        animationRotateCenter.setRepeatCount(Animation.INFINITE);
        splashLogo.startAnimation(animationRotateCenter);

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

        }, 2000);

    }

    @Override
    public void dialogNoInternet() {
        showSimpleAlert();
    }


    @Override
    public void cameraUpdate(double lat, double lng) {

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