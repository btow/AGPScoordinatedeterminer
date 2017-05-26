package com.example.samsung.gps_coordinate_determiner.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.samsung.gps_coordinate_determiner.R;
import com.example.samsung.gps_coordinate_determiner.presentation.view.main.MainView;
import com.example.samsung.gps_coordinate_determiner.presentation.presenter.main.MainPresenter;

import com.arellomobile.mvp.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    public static final String TAG = "MainActivity";

    @InjectPresenter
    MainPresenter mMainPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
