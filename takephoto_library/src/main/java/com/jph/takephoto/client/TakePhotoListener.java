package com.jph.takephoto.client;

import com.jph.takephoto.model.TResult;

/**
 * Created by wang.rongqiang on 2017/9/26.
 */

public interface TakePhotoListener {
    public void takeSuccess(TResult result);

    public void takeFail(TResult result, String msg);

    public void takeCancel();
}
