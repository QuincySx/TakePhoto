package com.jph.takephoto.client;

import android.net.Uri;

import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.MultipleCrop;
import com.jph.takephoto.model.TException;

/**
 * Created by wang.rongqiang on 2017/9/26.
 */

public interface PhotoTaskCallback {
    void onPickFromCaptureWithCrop(Uri outPutUri);

    void onPickFromGalleryWithCrop(Uri outPutUri);

    void onPickFromCaptureWithCrop(Uri outPutUri, CropOptions options);

    void onPickFromGalleryWithCrop(Uri outPutUri, CropOptions options);

    void onPickMultiple(int limit);

    void onPickMultipleWithCrop(int limit, CropOptions options);

    void onPickFromDocuments();

    void onPickFromDocumentsWithCrop(Uri outPutUri, CropOptions options);

    void onPickFromGallery();

    void onPickFromCapture(Uri outPutUri);

    void onCrop(Uri imageUri, Uri outPutUri, CropOptions options) throws TException;

    void onCrop(MultipleCrop multipleCrop, CropOptions options) throws TException;
}
