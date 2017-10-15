package com.jph.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jph.takephoto.app.ITakePhotoHandle;
import com.jph.takephoto.app.XTakePhoto;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.cboy.me
 * GitHub:https://github.com/crazycodeboy
 * Eamil:crazycodeboy@gmail.com
 */
public class SimpleFragment extends Fragment implements ITakePhotoHandle.TakeResultListener {
    private CustomHelper customHelper;
    private XTakePhoto mXTakePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.common_layout, container, false);
        customHelper = CustomHelper.of(contentView);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mXTakePhoto = XTakePhoto.with(this);
    }

    public void onClick(View view) {
        customHelper.onClick(view, mXTakePhoto);
    }

    @Override
    public void takeCancel() {
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeSuccess(TResult result) {
        showImg(result.getImages());
        Toast.makeText(getContext(), result.getImage().getOriginalPath(), Toast.LENGTH_SHORT).show();
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent(getContext(), ResultActivity.class);
        intent.putExtra("images", images);
        startActivity(intent);
    }
}
