package com.jph.takephoto.model;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Author: JPH
 * Date: 2016/8/11 17:01
 */
public class TContextWrap {
    private Activity activity;
    private Fragment supportFragment;

    public static TContextWrap of(Activity activity) {
        return new TContextWrap(activity);
    }

    public static TContextWrap of(Fragment fragment) {
        return new TContextWrap(fragment);
    }

    private TContextWrap(Activity activity) {
        this.activity = activity;
    }

    private TContextWrap(Fragment fragment) {
        this.supportFragment = fragment;
        this.activity = fragment.getActivity();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Fragment getSupportFragment() {
        return supportFragment;
    }

    public void setSupportFragment(Fragment fragment) {
        this.supportFragment = fragment;
    }
}
