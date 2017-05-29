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

/**
 * The class implements the logic for processing commands received
 * from the activity {@code MainActivity}. The class extended
 * of the class {@code MvpPresenter<MainView>}.
 * <p>
 * <p>Created by
 *
 * @author Vladimir Bobkov
 *         on 26.05.2017</p>
 *         <p>
 *         </p>Contains the following fields:</p>
 *         <ul>
 *         <li><p>{@code locationManager}
 *         - The field contains a link to an instance class {@code LocationManager}</p></li>
 *         <li><p>{@code mCxtView}
 *         - The field contains a link to an instance class {@code Context}</p></li>
 *         <li><p>{@code mLocationListener}
 *         - The field contains a reference to an instance of the inner class {@code LocationListener}</p>
 *         <p>This class implemented a interface {@code LocationListener}
 *         and contains the following methods:</p>
 *         <ul>
 *         <li><p>{@code onLocationChanged} - </p></li>
 *         <li><p>{@code onStatusChanged} - </p></li>
 *         <li><p>{@code onProviderEnabled} - </p></li>
 *         <li><p>{@code onProviderDisabled} - </p></li>
 *         </ul>
 *         </ul>
 *         <p>Contains the following methods:</p>
 *         <ul>
 *         <li><p>{@code onClickBtn} - to handle the event {@code Button}</p></li>
 *         <li><p>{@code getStringLocation} - to obtain the string from
 *         an instance of the class {@code location}</p></li>
 *         </ul>
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private LocationManager mLocationManager;
    private Context mCxtView;
    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if (location == null) {
                getViewState().setInfo(mCxtView.getString(R.string.location_is_not_defined));
                return;
            }
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                getViewState().setInfo(getStringLocation(location));
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            if (status != LocationProvider.AVAILABLE) {
                getViewState().setInfo(mCxtView.getString(R.string.system_is_not_available));
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
                getViewState().setInfo(mCxtView.getString(R.string.system_is_not_available));
            }
        }
    };

    public void onClickBtn(final Context context) {

        mCxtView = context;

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) mCxtView.getSystemService(LOCATION_SERVICE);
        }

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getViewState().setInfo(context.getString(R.string.system_is_not_available));
        } else {
            //noinspection MissingPermission
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,   //Тип провайдера для определения местоположения
                    1000,                           //Минимальный интервал определения в милисек
                    3.5f,                           //Минимальное расстояние для переопределения в метрах
                    mLocationListener);             //Слушатель изменения положения
        }
    }

    String getStringLocation(final Location location) {

        if (location == null) {
            return mCxtView.getString(R.string.location_is_not_defined);
        }
        float accuracy = location.getAccuracy();
        if (accuracy > 60) {
            return mCxtView.getString(R.string.given_accuracy_is_impossible);
        }
        String sFormat = mCxtView.getString(R.string.position_coordinates) + " "
                + mCxtView.getString(R.string.lat) + " = %1$.4f, "
                + mCxtView.getString(R.string.lon) + " = %2$.4f, "
                + mCxtView.getString(R.string.accuracy) + " = %3$.4f";
        return String.format(sFormat,
                location.getLatitude(),
                location.getLongitude(),
                accuracy);
    }

}
