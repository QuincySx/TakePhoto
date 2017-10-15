package com.jph.takephoto.app;

import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.permission.PermissionManager;

/**
 * Created by quincysx on 2017/10/15.
 */

public interface ITakePhotoOptions {
    void permissionNotify(PermissionManager.TPermissionType type);

    /**
     * 启用图片压缩
     *
     * @param config             压缩图片配置
     * @param showCompressDialog 压缩时是否显示进度对话框
     */
    void onEnableCompress(CompressConfig config, boolean showCompressDialog);

    /**
     * 设置TakePhoto相关配置
     *
     * @param options
     */
    void setTakePhotoOptions(com.jph.takephoto.model.TakePhotoOptions options);

}
