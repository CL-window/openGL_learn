package com.cl.slack.camerafilter.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by slack
 * on 17/6/9 下午6:01
 * 直接使用这个类作为预览，没有做任何处理
 */

public class CameraSurfacePreview extends SurfaceView implements SurfaceHolder.Callback {

    // 视频帧共享存储回调接口
    private SurfaceHolder surfaceHolder;

    public CameraSurfacePreview(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 给SurFaceHolder指定类型
    }

    @Override
    public void surfaceCreated(SurfaceHolder sh) {
        CameraUtil.instance.startCamera();
        CameraUtil.instance.doStartPreview(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder sh, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder sh) {
        CameraUtil.instance.stopCamera();
    }

}
