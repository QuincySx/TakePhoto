package com.jph.takephoto.app;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by quincysx on 2017/10/15.
 */

public interface ITakePhotoAttrApp {
    void onCreate(Bundle savedInstanceState);

    void onSaveInstanceState(Bundle outState);

    /**
     * 处理拍照或从相册选择的图片或裁剪的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
