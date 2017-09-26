package com.jph.takephoto.client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jph.takephoto.app.ITakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.MultipleCrop;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TException;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

/**
 * Created by wang.rongqiang on 2017/9/26.
 */

public class SupportPhotoManagerFragment extends Fragment implements ITakePhoto.TakeResultListener, InvokeListener, PhotoTaskCallback {
    private InvokeParam invokeParam;
    private ITakePhoto takePhoto;

    private PhotoManager mPhotoManager;
    private CropOptions mCropOptions;

    public SupportPhotoManagerFragment() {
    }

    public void setPhotoManager(PhotoManager photoManager) {
        mPhotoManager = photoManager;
    }

    public PhotoManager getPhotoManager() {
        return mPhotoManager;
    }

    public void onCreate(Bundle savedInstanceState) {
        this.getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        this.getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this.getActivity(), type, this.invokeParam, this);
    }

    public ITakePhoto getTakePhoto() {
        if (this.takePhoto == null) {
            this.takePhoto = (ITakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
            mCropOptions = new CropOptions.Builder()
                    .setAspectX(1)
                    .setAspectY(1)
                    .setWithOwnCrop(true)
                    .create();
        }
        return this.takePhoto;
    }

    @Override
    public void takeSuccess(final TResult result) {
        final TakePhotoListener takePhotoListener = mPhotoManager.getTakePhotoListener();
        if (takePhotoListener != null) {
            takePhotoListener.takeSuccess(result);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        final TakePhotoListener takePhotoListener = mPhotoManager.getTakePhotoListener();
        if (takePhotoListener != null) {
            takePhotoListener.takeFail(result, msg);
        }
    }

    @Override
    public void takeCancel() {
        final TakePhotoListener takePhotoListener = mPhotoManager.getTakePhotoListener();
        if (takePhotoListener != null) {
            takePhotoListener.takeCancel();
        }
    }

    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }

        return type;
    }

    @Override
    public void onPickFromCaptureWithCrop(Uri outPutUri) {
        getTakePhoto().onPickFromCaptureWithCrop(outPutUri, mCropOptions);
    }

    @Override
    public void onPickFromGalleryWithCrop(Uri outPutUri) {
        getTakePhoto().onPickFromGalleryWithCrop(outPutUri, mCropOptions);
    }

    @Override
    public void onPickFromCaptureWithCrop(Uri outPutUri, CropOptions options) {
        getTakePhoto().onPickFromCaptureWithCrop(outPutUri, options);
    }

    @Override
    public void onPickFromGalleryWithCrop(Uri outPutUri, CropOptions options) {
        getTakePhoto().onPickFromGalleryWithCrop(outPutUri, options);
    }

    @Override
    public void onPickMultiple(int limit) {
        getTakePhoto().onPickMultiple(limit);
    }

    @Override
    public void onPickMultipleWithCrop(int limit, CropOptions options) {
        getTakePhoto().onPickMultipleWithCrop(limit, options);
    }

    @Override
    public void onPickFromDocuments() {
        getTakePhoto().onPickFromDocuments();
    }

    @Override
    public void onPickFromDocumentsWithCrop(Uri outPutUri, CropOptions options) {
        getTakePhoto().onPickFromDocumentsWithCrop(outPutUri, options);
    }

    @Override
    public void onPickFromGallery() {
        getTakePhoto().onPickFromGallery();
    }

    @Override
    public void onPickFromCapture(Uri outPutUri) {
        getTakePhoto().onPickFromCapture(outPutUri);
    }

    @Override
    public void onCrop(Uri imageUri, Uri outPutUri, CropOptions options) throws TException {
        getTakePhoto().onCrop(imageUri, outPutUri, options);
    }

    @Override
    public void onCrop(MultipleCrop multipleCrop, CropOptions options) throws TException {
        getTakePhoto().onCrop(multipleCrop, options);
    }
}
