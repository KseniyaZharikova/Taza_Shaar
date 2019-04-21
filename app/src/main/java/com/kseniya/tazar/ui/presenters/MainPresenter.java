package com.kseniya.tazar.ui.presenters;

import android.app.Activity;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kseniya.tazar.data.ReceptionPoint;
import com.kseniya.tazar.data.db.SQLiteHelper;
import com.kseniya.tazar.interfaces.MainInterface;
import com.kseniya.tazar.interfaces.SortedList;
import com.kseniya.tazar.utils.ConnectionUtils;
import com.kseniya.tazar.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.kseniya.tazar.BuildConfig.BASE_URL_FIREBASE;

public class MainPresenter implements MainInterface.Presenter, LocationListener {
    private MainInterface.View mainView;

    private SQLiteHelper db;

    private final String TAG = getClass().getSimpleName();


    private List<ReceptionPoint> pointList;

    public MainPresenter(SQLiteHelper sqLiteHelper) {
        db = sqLiteHelper;
        pointList = getPointFromDatabase();
    }

    @Override
    public void getPermission(Activity activity) {
//        if (PermissionUtils.Companion.isLocationEnable(activity)) {
//        }
    }

    @Override
    public void checkNetwork(Activity activity) {
        if (ConnectionUtils.isHasNetwork(activity.getApplicationContext())) {
            downloadMarkers();
        } else if (db.getReceptionPoints().size() != 0) {
            mainView.startActivity();

        } else {
            mainView.dialogNoInternet();
        }
    }



    @Override
    public void downloadMarkers() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl(BASE_URL_FIREBASE + Constants.FIREBASE_RECEPTION_POINTS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pointList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ReceptionPoint point = postSnapshot.getValue(ReceptionPoint.class);
                    pointList.add(point);
                    Log.d(TAG, "onDataChange: " + pointList.size());
                }
                saveMarkersToDb();

                mainView.startActivity();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: ");
            }
        });
    }

    private void saveMarkersToDb() {
        db.deleteReceptionPoints();
        db.saveReceptionPoints(pointList);
    }

    @Override
    public ReceptionPoint getCurrentPoint(int position) {
        if (SortedList.list.size() != 0) {
            return SortedList.list.get(position);
        }
        return pointList.get(position);
    }

    @Override
    public void bind(MainInterface.View view) {
        mainView = view;
    }

    @Override
    public void unbind() {
        mainView = null;
    }


    @Override
    public void setCheckedPoints(int category) {
        List<ReceptionPoint> list = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            if (pointList.get(i).getType() == category)
                list.add(new ReceptionPoint(pointList.get(i)));
        }
//        Log.d(TAG, "showFilteredReceptionPoints11: " + pointList.size());
//        Log.d(TAG, "showFilteredReceptionPoints11: " + SortedList.list.size());
//        mainView.clearAllMarkersAndDrawNew(list);
        SortedList.list.clear();
        SortedList.list.addAll(list);
        mainView.clearAllMarkersAndDrawNew(list);
        Log.d(TAG, "setCheckedPoints: " + list.size());
    }

    @Override
    public List<ReceptionPoint> getPointFromDatabase() {
        if (pointList != null)
            pointList.clear();
        Log.d(TAG, "getPointFromDatabase: ");
        return pointList = db.getReceptionPoints();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Loca_onLocationChanged", location.getLatitude() + " " + location.getLongitude());


    }

}
