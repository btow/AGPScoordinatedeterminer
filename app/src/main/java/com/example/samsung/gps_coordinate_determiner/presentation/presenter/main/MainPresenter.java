package com.example.samsung.gps_coordinate_determiner.presentation.presenter.main;


import android.location.LocationManager;
import android.view.View;

import com.example.samsung.gps_coordinate_determiner.presentation.view.main.MainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.samsung.gps_coordinate_determiner.ui.activity.main.MainActivity;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private StringBuilder info;
    private LocationManager locationManager;

    public static void onClickBtn() {



    }

}
