package com.example.samsung.gps_coordinate_determiner.presentation.presenter.main;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.samsung.gps_coordinate_determiner.R;
import com.example.samsung.gps_coordinate_determiner.presentation.view.main.MainView;

import static android.content.Context.LOCATION_SERVICE;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private LocationManager mLocationManager;
    private Context mCtx;
    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if (location == null) {
                getViewState().setInfo(mCtx.getString(R.string.location_is_not_defined));
                return;
            }
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                getViewState().setInfo(getStringLocation(location));
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            if (status != LocationProvider.AVAILABLE) {
                getViewState().setInfo(mCtx.getString(R.string.system_is_not_available));
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                //noinspection MissingPermission
                getViewState().setInfo(getStringLocation(mLocationManager.getLastKnownLocation(provider)));
            }
        }

        @Override
        public void onProviderDisabled(String provider) {

            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                getViewState().setInfo(mCtx.getString(R.string.system_is_not_available));
            }
        }
    };

    public void onClickBtn(final Context context) {

        mCtx = context;

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mCtx.getSystemService(LOCATION_SERVICE);
        }

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getViewState().setInfo(context.getString(R.string.system_is_not_available));
        } else {
            //noinspection MissingPermission
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,   //Тип провайдера для определения местоположения
                    1000,                           //Минимальный интервал определения в милисек
                    0.5f,                           //Минимальное расстояние для переопределения в метрах
                    mLocationListener);             //Слушатель изменения положения
        }
    }

    private String getStringLocation(final Location location) {

        if (location == null) {
            return mCtx.getString(R.string.location_is_not_defined);
        }
        float accuracy = location.getAccuracy();
        if (accuracy > 60) {
            return mCtx.getString(R.string.given_accuracy_is_impossible);
        }
        String sFormat = mCtx.getString(R.string.position_coordinates) + " "
                + mCtx.getString(R.string.lat) + " = %1$.4f, "
                + mCtx.getString(R.string.lon) + " = %2$.4f, "
                + mCtx.getString(R.string.accuracy) + " = %3$.4f";
        return String.format(sFormat,
                location.getLatitude(),
                location.getLongitude(),
                accuracy);
    }
}