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
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    // 视频帧共享存储回调接口
    private SurfaceHolder surfaceHolder;
    public int textureBuffer[];
    private boolean openCamera;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    public CameraSurfaceView(Context context, boolean openCamera) {
        super(context);
        this.openCamera = openCamera;
        if(!openCamera) {
            mHandlerThread = new HandlerThread("surface_" + System.currentTimeMillis());
            mHandlerThread.start();
            mHandler = new Handler(mHandlerThread.getLooper());
        }
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 给SurFaceHolder指定类型
    }

    public void updateCanvas(final byte[] data) {
        if(mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    int w = CameraUtil.instance.getPreviewWidth();
                    int h = CameraUtil.instance.getPreviewHeight();
                    int[] rgb = decodeYUV420SP(data, w, h);
                    Canvas canvas = surfaceHolder.lockCanvas();
                    if(canvas != null) {
                        canvas.drawBitmap(rgb, 0, w, 0, 0, w, h, false, null);
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            });
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder sh) {
        if(openCamera) {
            CameraUtil.instance.startCamera();
            CameraUtil.instance.doStartPreview(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder sh, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder sh) {
        if(openCamera) {
            CameraUtil.instance.stopCamera();
        }
    }

    private int[] decodeYUV420SP(byte[] yuv420sp, int width, int height) {

        if(textureBuffer == null) {
            int w = CameraUtil.instance.getPreviewWidth();
            int h = CameraUtil.instance.getPreviewHeight();
            textureBuffer = new int[w * h];
        }
        final int frameSize = width * height;

        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0;
                else if (r > 262143) r = 262143;
                if (g < 0) g = 0;
                else if (g > 262143) g = 262143;
                if (b < 0) b = 0;
                else if (b > 262143) b = 262143;

                textureBuffer[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) &
                        0xff00) | ((b >> 10) & 0xff);

            }
        }
        return textureBuffer;
    }

}
