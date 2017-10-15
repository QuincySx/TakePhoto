package com.jph.takephoto.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.MultipleCrop;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TException;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

/**
 * Created by wang.rongqiang on 2017/9/26.
 */

public class SupportPhotoManagerFragment extends Fragment implements ITakePhotoHandle.TakeResultListener, InvokeListener, TakePhoto {
    private InvokeParam invokeParam;

    private TakePhoto mTakePhoto;

    private CompressConfig mConfig = null;
    private boolean mShowCompressDialog;
    private TakePhotoOptions mPhotoOptions = null;
    private PermissionManager.TPermissionType mTPermissionType;

    private XTakePhoto mPhotoManager;

    public SupportPhotoManagerFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initTakePhoto();
    }

    public void setPhotoManager(XTakePhoto photoManager) {
        mPhotoManager = photoManager;
    }

    public XTakePhoto getPhotoManager() {
        return mPhotoManager;
    }

    @Override
    public void onPickMultiple(int limit) {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickMultiple(limit);
    }

    @Override
    public void onPickMultipleWithCrop(int limit, CropOptions options) {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickMultipleWithCrop(limit, options);
    }

    @Override
    public void onPickFromDocuments() {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickFromDocuments();
    }

    @Override
    public void onPickFromDocumentsWithCrop(Uri outPutUri, CropOptions options) {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickFromDocumentsWithCrop(outPutUri, options);
    }

    @Override
    public void onPickFromGallery() {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickFromGallery();
    }

    @Override
    public void onPickFromGalleryWithCrop(Uri outPutUri, CropOptions options) {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickFromGalleryWithCrop(outPutUri, options);
    }

    @Override
    public void onPickFromCapture(Uri outPutUri) {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickFromCapture(outPutUri);
    }

    @Override
    public void onPickFromCaptureWithCrop(Uri outPutUri, CropOptions options) {
        assertNotNull(getTakePhoto());
        getTakePhoto().onPickFromCaptureWithCrop(outPutUri, options);
    }

    @Override
    public void onCrop(Uri imageUri, Uri outPutUri, CropOptions options) throws TException {
        assertNotNull(getTakePhoto());
        getTakePhoto().onCrop(imageUri, outPutUri, options);
    }

    @Override
    public void onCrop(MultipleCrop multipleCrop, CropOptions options) throws TException {
        assertNotNull(getTakePhoto());
        getTakePhoto().onCrop(multipleCrop, options);
    }

    @Override
    public void permissionNotify(PermissionManager.TPermissionType type) {
        if (getTakePhoto() == null) {
            mTPermissionType = type;
        } else {
            getTakePhoto().permissionNotify(type);
        }
    }

    @Override
    public void onEnableCompress(CompressConfig config, boolean showCompressDialog) {
        if (getTakePhoto() == null) {
            mConfig = config;
            mShowCompressDialog = showCompressDialog;
        } else {
            getTakePhoto().onEnableCompress(config, showCompressDialog);
        }
    }

    @Override
    public void setTakePhotoOptions(TakePhotoOptions options) {
        if (getTakePhoto() == null) {
            mPhotoOptions = options;
        } else {
            getTakePhoto().setTakePhotoOptions(options);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        assertNotNull(getTakePhoto());
        mTakePhoto.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        assertNotNull(getTakePhoto());
        mTakePhoto.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        assertNotNull(getTakePhoto());
        mTakePhoto.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this.getActivity(), type, this.invokeParam, this);
    }

    @Nullable
    public TakePhoto getTakePhoto() {
        return this.mTakePhoto;
    }

    private void assertNotNull(TakePhoto takePhoto) {
        if (takePhoto == null) {
            throw new NullPointerException("TakePhoto is not initialized yet");
        }
    }

    private void initTakePhoto() {
        if (this.mTakePhoto == null) {
            mTakePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
            this.mTakePhoto.onEnableCompress(mConfig, mShowCompressDialog);
            if (mPhotoOptions != null) {
                this.mTakePhoto.setTakePhotoOptions(mPhotoOptions);
            }
            if (mTPermissionType != null) {
                this.mTakePhoto.permissionNotify(mTPermissionType);
            }
        }
    }

    @Override
    public void takeSuccess(final TResult result) {
        final ITakePhotoHandle.TakeResultListener takePhotoListener = mPhotoManager.getTakePhotoListener();
        if (takePhotoListener != null) {
            takePhotoListener.takeSuccess(result);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        final ITakePhotoHandle.TakeResultListener takePhotoListener = mPhotoManager.getTakePhotoListener();
        if (takePhotoListener != null) {
            takePhotoListener.takeFail(result, msg);
        }
    }

    @Override
    public void takeCancel() {
        final ITakePhotoHandle.TakeResultListener takePhotoListener = mPhotoManager.getTakePhotoListener();
        if (takePhotoListener != null) {
            takePhotoListener.takeCancel();
        }
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }
}
