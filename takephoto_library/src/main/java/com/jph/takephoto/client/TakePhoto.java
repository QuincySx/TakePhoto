package com.jph.takephoto.client;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by wang.rongqiang on 2017/9/26.
 */

public class TakePhoto {
    public static PhotoManager with(Context context) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(context);
    }

    public static PhotoManager with(FragmentActivity activity) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(activity);
    }

    public static PhotoManager with(Fragment fragment) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(fragment);
    }

    public static PhotoManager with(Activity activity) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(activity);
    }

    public static PhotoManager with(android.app.Fragment fragment) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(fragment);
    }
}
