package com.jph.takephoto.permission;

import com.jph.takephoto.app.ITakePhotoHandle;
import com.jph.takephoto.app.ITakePhotoOptions;
import com.jph.takephoto.model.InvokeParam;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TakePhotoInvocationHandler implements InvocationHandler {
    private ITakePhotoHandle delegate;
    private InvokeListener listener;

    public static TakePhotoInvocationHandler of(InvokeListener listener) {
        return new TakePhotoInvocationHandler(listener);
    }

    private TakePhotoInvocationHandler(InvokeListener listener) {
        this.listener = listener;
    }

    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param delegate
     * @return
     */
    public Object bind(ITakePhotoHandle delegate) {
        this.delegate = delegate;
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        PermissionManager.TPermissionType type = listener.invoke(new InvokeParam(proxy, method, args));
        if (proxy instanceof ITakePhotoHandle) {
            if (!PermissionManager.TPermissionType.NOT_NEED.equals(type)) {
                ((ITakePhotoOptions) proxy).permissionNotify(type);
            }
        }
        return method.invoke(delegate, args);
    }
}