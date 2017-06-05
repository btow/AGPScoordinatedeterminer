package com.example.samsung.gps_coordinate_determiner.ui.activity.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.samsung.gps_coordinate_determiner.Messager;
import com.example.samsung.gps_coordinate_determiner.R;
import com.example.samsung.gps_coordinate_determiner.presentation.presenter.main.MainPresenter;
import com.example.samsung.gps_coordinate_determiner.presentation.view.main.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * The class {@code MainActivity} enables the application to interact
 * with the operating system Android. The class extended
 * the class {@code MvpAppCompatActivity} and implemented
 * the interface {@code MainView}
 * <p>
 * <p>Created by
 *
 * @author Vladimir Bobkov
 *         on 26.05.2017</p>
 *         <p>
 *         </p>Contains the following fields:</p>
 *         <ul>
 *         <li></p>{@code TAG} - A string constant contains a tag log</p></li>
 *         <li><p>{@code mMainPresenter} - The field binding of this activity to the presenter {@code MainPresenter}</p></li>
 *         <li><p>{@code btnDetCoord} - The field binding of this activity to the {@code Button}</p></li>
 *         <li><p>{@code tvInfo} - The field binding of this activity to the {@code TextView}</p></li>
 *         </ul>
 *         <p>
 *         <p>Contains the following methods:</p>
 *         <ul>
 *         <li><p>{@code getIntent} - </p></li>
 *         <li><p>{@code onCreate} - </p></li>
 *         <li><p>{@code onCreateOptionsMenu} - </p></li>
 *         <li><p>{@code onOptionsItemSelected} - </p></li>
 *         <li><p>{@code setInfo} - </p></li>
 *         </ul>
 */
public class MainActivity extends MvpAppCompatActivity implements MainView {

    public static final String TAG = "MainActivity";
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @InjectPresenter
    MainPresenter mMainPresenter;

    @BindView(R.id.btnDetCoord)
    Button btnDetCoord;
    @BindView(R.id.tvInfo)
    TextView tvInfo;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnDetCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMainPresenter.onClickBtn(getBaseContext());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 1, 0, getString(R.string.settings));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case 1:
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setInfo(final String msg) {
        tvInfo.setText(msg);
        Messager.sendToAllRecipients(getBaseContext(), "setInfo(): msg = " + msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void requestPermissions() {
        this.requestPermissions(
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE_ASK_PERMISSIONS);
        Messager.sendToAllRecipients(getBaseContext(), "requestPermissions()");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted
                    mMainPresenter.onClickBtn(getBaseContext());
                } else {
                    //Permission Denied
                    setInfo(getString(R.string.permission_has_not_been_granted));
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        Messager.sendToAllRecipients(getBaseContext(), "onRequestPermissionsResult()");
    }
}
