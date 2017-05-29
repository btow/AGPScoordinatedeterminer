package com.example.samsung.gps_coordinate_determiner.presentation.view.main;

import com.arellomobile.mvp.MvpView;

/**
 * The Interface MainView to communicate class activity MainActivity
 * with a view class MainPresenter. This class extends the
 * class MvpView.
 * <p>
 * <p>Created by
 *
 * @author Vladimir Bobkov
 *         on 26.05.2017</p>
 */
public interface MainView extends MvpView {

    /**
     * The interface method to set the value of the field tvInfo
     * in activity MainActivity
     *
     * @param msg - string value to pass to the activity MainActivity
     */
    void setInfo(final String msg);

}
