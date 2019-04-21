package com.kseniya.tazar.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kseniya.tazar.R;
import com.kseniya.tazar.ZeroWasteApp;
import com.kseniya.tazar.data.ReceptionPoint;
import com.kseniya.tazar.interfaces.CheckBoxInterface;
import com.kseniya.tazar.interfaces.MainInterface;
import com.kseniya.tazar.interfaces.SortedList;
import com.kseniya.tazar.ui.fragments.ChoseFragment;
import com.kseniya.tazar.ui.presenters.MainPresenter;
import com.kseniya.tazar.utils.Constants;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kseniya.tazar.BuildConfig.MAP_BOX_KEY;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener, MainInterface.View, CheckBoxInterface, MapboxMap.OnMarkerClickListener, PermissionsListener, OnLocationClickListener, OnCameraTrackingChangedListener {

    private MainInterface.Presenter mainPresenter;
    private MapboxMap map;
    private Marker marker;
    private List<Marker> mMarkerList;
    private LocationComponent locationComponent;
    PermissionsManager permissionsManager = new PermissionsManager(this);

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.myLocation)
    ImageView myLocation;

    @BindView(R.id.mainActivityRelativeLayout)
    RelativeLayout mainActivityRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), MAP_BOX_KEY);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(ZeroWasteApp.get(this).getSqLiteHelper());
        mainPresenter.bind(this);
        mainPresenter.getPermission(this);
        myLocation.setVisibility(View.INVISIBLE);
        getHeight();
        mMarkerList = new ArrayList<>();
        initMap(savedInstanceState);
    }

    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    public static Icon drawableToIcon(@NonNull Context context, @DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(context.getResources(), id, context.getTheme());
        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(vectorDrawable).getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return IconFactory.getInstance(context).fromBitmap(bitmap);
    }

    @SuppressLint("NewApi")
    @Override
    public void drawReceptionPoints(List<ReceptionPoint> pointFromDatabase) {
        for (int i = 0; i < pointFromDatabase.size(); i++) {

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(pointFromDatabase.get(i).getLatitude()), Double.parseDouble(pointFromDatabase.get(i).getLongitude())))
                    .icon(drawableToIcon(this, Constants.PointsType(pointFromDatabase.get(i).getType()))));
            mMarkerList.add(marker);
        }
    }

    @Override
    public void clearAllMarkersAndDrawNew(List<ReceptionPoint> list) {
        if (mMarkerList != null) {

            for (int i = 0; i < mMarkerList.size(); i++) {
                map.removeMarker(mMarkerList.get(i));
            }
            mMarkerList.clear();

            drawReceptionPoints(list);
        }


    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Create and customize the LocationComponent's options
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .elevation(5)
                    .accuracyAlpha(.6f)
                    .build();

            // Get an instance of the component
            locationComponent = map.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build();

            // Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            //locationComponent.setCameraMode(CameraMode/);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

            // Add the location icon click listener
            locationComponent.addOnLocationClickListener(this);

            // Add the camera tracking listener. Fires if the map camera is manually moved.
           // locationComponent.addOnCameraTrackingChangedListener(this);


        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }

        cameraUpdate(Constants.LAT, Constants.LNG);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            map.getStyle(this::enableLocationComponent);
        } else {

            finish();
        }
    }

    @Override
    public void onMapReady(@NotNull MapboxMap mapboxMap) {
        map = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, this::enableLocationComponent);

        replaceFragment(new ChoseFragment());
       // map.setOnMarkerClickListener(this);
        myLocation.setOnClickListener(this);
        cameraUpdate(Constants.LAT, Constants.LNG);

//        if (PermissionUtils.Companion.isLocationEnable(this)) {
//
//            myLocation.setVisibility(View.VISIBLE);
//        }


    }


    public void cameraUpdate(double lat, double lng) {
        if (map != null) {
            Log.d("Loca_cameraUpdate", lat + " " + lng);
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(lat - 0.01, lng))
                    .bearing(0)
                    .zoom(10).tilt(11).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        }
    }

    public void cameraUpdateInfo(double lat, double lng) {
        if (map != null) {
            Log.d("Loca_cameraUpdate", lat + " " + lng);
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(lat - 0.002, lng))
                    .bearing(0)
                    .zoom(15).tilt(17).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position));


        }
    }

    @Override
    public void cameraUpdatePOintsInfo() {
        if (map != null) {
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(Constants.LAT - 0.01, Constants.LNG ))
                    .bearing(0)
                    .zoom(11).tilt(13).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position));


        }
    }

    @Override
    public void onCheckBoxClicked(int tag) {
        mainPresenter.setCheckedPoints(tag);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        int pos = mMarkerList.lastIndexOf(marker);
        if (pos > -1) {
            cameraUpdate(marker.getPosition().getLatitude(), marker.getPosition().getLongitude());
            showItemByClickMarker(mainPresenter.getCurrentPoint(pos));
        }
        return false;
    }

    @Override
    public void showAllPoints() {
        SortedList.list.clear();
        clearAllMarkersAndDrawNew(mainPresenter.getPointFromDatabase());
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void startActivity() {

    }

    @Override
    public void dialogNoInternet() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void zoomCameraToMarker(@NotNull ReceptionPoint item) {
        List<ReceptionPoint> list = new ArrayList<>();
        list.add(item);
        clearAllMarkersAndDrawNew(list);
        cameraUpdateInfo(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
    }


    public void getHeight() {

        final ViewTreeObserver observer = mainActivityRelativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(() -> Constants.HIGHT_OF_ACTIVITY = mainActivityRelativeLayout.getHeight());

    }

    @Override
    public void drawPointsByType() {
        clearAllMarkersAndDrawNew(SortedList.list);
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationComponentClick() {
//        if (locationComponent.getLastKnownLocation() != null) {
//            Toast.makeText(this, "asaSAS",
//                    locationComponent.getLastKnownLocation().getLatitude(),
//                    locationComponent.getLastKnownLocation().getLongitude()), Toast.LENGTH_LONG).
//            show();
//        }
    }

    @Override
    public void onCameraTrackingDismissed() {

    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {

    }
}