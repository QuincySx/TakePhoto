package com.jph.takephoto.client;

import android.content.Context;
import android.net.Uri;

import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.MultipleCrop;
import com.jph.takephoto.model.TException;

import java.io.File;

/**
 * Created by wang.rongqiang on 2017/9/26.
 */

public class PhotoManager {
    private final Context mContext;
    private TakePhotoListener mTakePhotoListener;
    private PhotoTaskCallback mPhotoTaskCallback;

    public PhotoManager(Context context, PhotoTaskCallback photoTaskCallback) {
        mContext = context;
        mPhotoTaskCallback = photoTaskCallback;
    }

    public void setTakePhotoListener(TakePhotoListener takePhotoListener) {
        mTakePhotoListener = takePhotoListener;
    }

    public TakePhotoListener getTakePhotoListener() {
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
        mPhotoTaskCallback.onPickFromCaptureWithCrop(outPutUri);
    }

    public void onPickFromGalleryWithCrop(Uri outPutUri) {
        mPhotoTaskCallback.onPickFromGalleryWithCrop(outPutUri);
    }

    public void onPickFromCaptureWithCrop(Uri outPutUri, CropOptions options) {
        mPhotoTaskCallback.onPickFromCaptureWithCrop(outPutUri, options);
    }

    public void onPickFromGalleryWithCrop(Uri outPutUri, CropOptions options) {
        mPhotoTaskCallback.onPickFromGalleryWithCrop(outPutUri, options);
    }

    public void onPickMultiple(int limit) {
        mPhotoTaskCallback.onPickMultiple(limit);
    }

    public void onPickMultipleWithCrop(int limit, CropOptions options) {
        mPhotoTaskCallback.onPickMultipleWithCrop(limit, options);
    }

    public void onPickFromDocuments() {
        mPhotoTaskCallback.onPickFromDocuments();
    }

    public void onPickFromDocumentsWithCrop(Uri outPutUri, CropOptions options) {
        mPhotoTaskCallback.onPickFromDocumentsWithCrop(outPutUri, options);
    }

    public void onPickFromGallery() {
        mPhotoTaskCallback.onPickFromGallery();
    }

    public void onPickFromCapture(Uri outPutUri) {
        mPhotoTaskCallback.onPickFromCapture(outPutUri);
    }

    public void onCrop(Uri imageUri, Uri outPutUri, CropOptions options) throws TException {
        mPhotoTaskCallback.onCrop(imageUri, outPutUri, options);
    }

    public void onCrop(MultipleCrop multipleCrop, CropOptions options) throws TException {
        mPhotoTaskCallback.onCrop(multipleCrop, options);
    }
}
