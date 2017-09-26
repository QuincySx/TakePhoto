package com.jph.takephoto.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.MultipleCrop;
import com.jph.takephoto.model.TException;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.PermissionManager;

import java.io.File;

/**
 * Created by QuincySx on 2017/9/26.
 * 本库因为集成了权限处理，因为原生 Fragment 权限处理的 API 的版本要求太高，所以本库剔除了对原生 Fragment 的支持
 * 所以本库只支持
 * {@link android.support.v4.app.Fragment}
 * {@link android.support.v4.app.FragmentActivity}
 * {@link android.support.v7.app.AppCompatActivity}
 * 除此之外的控件本库暂不支持
 * 如果你们想在原生 Fragment 、 Activity 上使用请参照 Github 用法手动实现
 */

public class XTakePhoto implements TakePhoto {
    public static XTakePhoto with(Context context) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(context);
    }

    public static XTakePhoto with(FragmentActivity activity) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(activity);
    }

    public static XTakePhoto with(Fragment fragment) {
        PhotoClientRetriever retriever = PhotoClientRetriever.get();
        return retriever.get(fragment);
    }

    private final Context mContext;
    private TakePhoto.TakeResultListener mTakePhotoListener;
    private TakePhoto mTakePhoto;
    private CropOptions mCropOptions;

    XTakePhoto(Context context, TakePhoto takePhoto) {
        mContext = context;
        mTakePhoto = takePhoto;

        mCropOptions = new CropOptions.Builder()
                .setAspectX(1)
                .setAspectY(1)
                .setWithOwnCrop(true)
                .create();
    }

    public void setTakePhotoListener(TakePhoto.TakeResultListener takePhotoListener) {
        mTakePhotoListener = takePhotoListener;
    }

    public TakePhoto.TakeResultListener getTakePhotoListener() {
        return mTakePhotoListener;
    }

    public void onPickFromCaptureWithCrop() {
        onPickFromCaptureWithCrop(
                new File(mContext.getExternalCacheDir().getAbsolutePath()
                        + File.separator
                        + "image"
                        + System.currentTimeMillis()
                        + ".jpg"));
    }

    public void onPickFromGalleryWithCrop() {
        onPickFromGalleryWithCrop(
                new File(mContext.getExternalCacheDir().getAbsolutePath()
                        + File.separator
                        + "image"
                        + System.currentTimeMillis()
                        + ".jpg"));
    }

    public void onPickFromCaptureWithCrop(File outfile) {
        onPickFromCaptureWithCrop(Uri.fromFile(outfile));
    }

    public void onPickFromGalleryWithCrop(File outfile) {
        onPickFromGalleryWithCrop(Uri.fromFile(outfile));
    }

    public void onPickFromCaptureWithCrop(Uri outPutUri) {
        onPickFromCaptureWithCrop(outPutUri, mCropOptions);
    }

    public void onPickFromGalleryWithCrop(Uri outPutUri) {
        onPickFromGalleryWithCrop(outPutUri, mCropOptions);
    }

    public void onPickFromCaptureWithCrop(Uri outPutUri, CropOptions options) {
        mTakePhoto.onPickFromCaptureWithCrop(outPutUri, options);
    }

    public void onPickFromGalleryWithCrop(Uri outPutUri, CropOptions options) {
        mTakePhoto.onPickFromGalleryWithCrop(outPutUri, options);
    }

    public void onPickMultiple(int limit) {
        mTakePhoto.onPickMultiple(limit);
    }

    public void onPickMultipleWithCrop(int limit, CropOptions options) {
        mTakePhoto.onPickMultipleWithCrop(limit, options);
    }

    public void onPickFromDocuments() {
        mTakePhoto.onPickFromDocuments();
    }

    public void onPickFromDocumentsWithCrop(Uri outPutUri, CropOptions options) {
        mTakePhoto.onPickFromDocumentsWithCrop(outPutUri, options);
    }

    public void onPickFromGallery() {
        mTakePhoto.onPickFromGallery();
    }

    public void onPickFromCapture(Uri outPutUri) {
        mTakePhoto.onPickFromCapture(outPutUri);
    }

    public void onCrop(Uri imageUri, Uri outPutUri, CropOptions options) throws TException {
        mTakePhoto.onCrop(imageUri, outPutUri, options);
    }

    public void onCrop(MultipleCrop multipleCrop, CropOptions options) throws TException {
        mTakePhoto.onCrop(multipleCrop, options);
    }

    @Override
    public void permissionNotify(PermissionManager.TPermissionType type) {
        mTakePhoto.permissionNotify(type);
    }

    @Override
    public void onEnableCompress(CompressConfig config, boolean showCompressDialog) {
        mTakePhoto.onEnableCompress(config, showCompressDialog);
    }

    @Override
    public void setTakePhotoOptions(TakePhotoOptions options) {
        mTakePhoto.setTakePhotoOptions(options);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}

