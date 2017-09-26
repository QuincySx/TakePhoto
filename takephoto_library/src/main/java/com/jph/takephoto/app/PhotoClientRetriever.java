package com.jph.takephoto.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.bumptech.glide.util.Util;

import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by wang.rongqiang on 2017/9/26.
 */

public class PhotoClientRetriever implements Handler.Callback {
    static final String FRAGMENT_TAG = "com.littlesparkle.growler.crm.common.taskphoto.fragment";

    private static final PhotoClientRetriever INSTANCE = new PhotoClientRetriever();

    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;

    private final Handler mHandler;

    final Map<FragmentManager, SupportPhotoManagerFragment> pendingSupportPhotoManagerFragments =
            new HashMap<FragmentManager, SupportPhotoManagerFragment>();

    public PhotoClientRetriever() {
        mHandler = new Handler(Looper.getMainLooper(), this /* Callback */);
    }

    public static PhotoClientRetriever get() {
        return INSTANCE;
    }

    public XTakePhoto get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        } else if (Util.isOnMainThread() && !(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return get((FragmentActivity) context);
            }
        }
        throw new RuntimeException("只支持 V4 包 Fragment 、 FragmentActivity 以及 AppCompatActivity");
    }

    public XTakePhoto get(FragmentActivity activity) {
        if (Util.isOnBackgroundThread()) {
            return get(activity.getApplicationContext());
        } else {
            assertNotDestroyed(activity);
            FragmentManager fm = activity.getSupportFragmentManager();
            assertNotTakePhotoListener(activity);
            return supportFragmentGet(activity, fm, (TakePhoto.TakeResultListener) activity);
        }
    }

    public XTakePhoto get(Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        if (Util.isOnBackgroundThread()) {
            return get(fragment.getActivity().getApplicationContext());
        } else {
            FragmentManager fm = fragment.getChildFragmentManager();
            assertNotTakePhotoListener(fragment);
            return supportFragmentGet(fragment.getActivity(), fm, (TakePhoto.TakeResultListener) fragment);
        }
    }

    public void assertNotTakePhotoListener(Object o) {
        if (!(o instanceof TakePhoto.TakeResultListener)) {
            throw new RuntimeException("Please take the TakePhotoListener interface first");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    SupportPhotoManagerFragment getSupportRequestManagerFragment(final FragmentManager fm) {
        SupportPhotoManagerFragment current = (SupportPhotoManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = pendingSupportPhotoManagerFragments.get(fm);
            if (current == null) {
                current = new SupportPhotoManagerFragment();
                pendingSupportPhotoManagerFragments.put(fm, current);
                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
                mHandler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget();
            }
        }
        return current;
    }

    XTakePhoto supportFragmentGet(Context context, FragmentManager fm, TakePhoto.TakeResultListener takePhotoListener) {
        SupportPhotoManagerFragment current = getSupportRequestManagerFragment(fm);
        XTakePhoto photoManager = current.getPhotoManager();
        if (photoManager == null) {
            photoManager = new XTakePhoto(context, current);
            photoManager.setTakePhotoListener(takePhotoListener);
            current.setPhotoManager(photoManager);
        }
        return photoManager;
    }

    @Override
    public boolean handleMessage(Message message) {
        boolean handled = true;
        Object removed = null;
        Object key = null;
        switch (message.what) {
            case ID_REMOVE_SUPPORT_FRAGMENT_MANAGER:
                FragmentManager supportFm = (FragmentManager) message.obj;
                key = supportFm;
                removed = pendingSupportPhotoManagerFragments.remove(supportFm);
                break;
            default:
                handled = false;
        }
        if (handled && removed == null && Log.isLoggable(TAG, Log.WARN)) {
            Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + key);
        }
        return handled;
    }
}
