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
    private android.app.Fragment fragment;

    public static TContextWrap of(Activity activity) {
        return new TContextWrap(activity);
    }

    public static TContextWrap of(Fragment fragment) {
        return new TContextWrap(fragment);
    }

    public static TContextWrap of(android.app.Fragment fragment) {
        return new TContextWrap(fragment);
    }

    private TContextWrap(Activity activity) {
        this.activity = activity;
    }

    private TContextWrap(Fragment fragment) {
        this.supportFragment = fragment;
        this.activity = fragment.getActivity();
    }

    private TContextWrap(android.app.Fragment fragment) {
        this.fragment = fragment;
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

    public android.app.Fragment getFragment() {
        return fragment;
    }

    public void setSupportFragment(Fragment fragment) {
        this.supportFragment = fragment;
    }

    public void setFragment(android.app.Fragment fragment) {
        this.fragment = fragment;
    }
}
