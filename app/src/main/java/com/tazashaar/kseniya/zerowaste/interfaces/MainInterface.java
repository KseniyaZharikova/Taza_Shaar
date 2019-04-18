package com.tazashaar.kseniya.zerowaste.interfaces;

import android.app.Activity;

import com.tazashaar.kseniya.zerowaste.data.ReceptionPoint;

import java.util.List;

public interface MainInterface {
    interface View {
        void cameraUpdate(double lat, double lng);

		void showMyCurrentLocation(Double lat, Double lng);

        void drawReceptionPoints(List<ReceptionPoint> pointFromDatabase);

        void clearAllMarkersAndDrawNew(List<ReceptionPoint> list);

        void startActivity();

        void dialogNoInternet();

    }

    interface Presenter extends LifeCycle<View> {

        void downloadMarkers();

        void getCurrentLocation(Activity activity);

        void checkNetwork(Activity activity);

        void getPermission(Activity activity);

        ReceptionPoint getCurrentPoint(int position);

        void startLocationUpdates();

        void setCheckedPoints(int category);

        List<ReceptionPoint> getPointFromDatabase();
    }

}
