package com.cl.slack.camerafilter.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

/**
 * Created by slack
 * on 17/6/11 上午10:41
 */

public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener{

    Context mContext;
    SurfaceTexture mSurface;
    private int textureBuffer[];

    public CameraTextureView(Context context) {
        this(context, null);
    }

    public CameraTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        this.setSurfaceTextureListener(this);
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                                          int height) {
        // TODO Auto-generated method stub
        Log.i("slack", "onSurfaceTextureAvailable...");
        mSurface = surface;
        textureBuffer = new int[width * height];
        openCamera();
    }

    private void openCamera() {
        CameraUtil.instance.startCamera();
        CameraUtil.instance.doStartPreview(mSurface);
    }

    private CameraUtil.PreviewFrameCallback mPreviewFrameCallback;
    public void addPreviewFramCallback(CameraUtil.PreviewFrameCallback callback) {
        mPreviewFrameCallback = callback;
        CameraUtil.instance.addPreviewFramCallback(callback);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        Log.i("slack", "onSurfaceTextureDestroyed...");
        CameraUtil.instance.stopCamera();
        CameraUtil.instance.removePreviewFramCallback(mPreviewFrameCallback);
        return true;
    }
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                                            int height) {
        // TODO Auto-generated method stub
        Log.i("slack", "onSurfaceTextureSizeChanged...");
    }
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        Log.i("slack", "onSurfaceTextureUpdated...");
        ///

    }

    private int[] decodeYUV420SP(byte[] yuv420sp, int width, int height) {

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

    /* 让Activity能得到TextureView的SurfaceTexture
     * @see android.view.TextureView#getSurfaceTexture()
     */
    public SurfaceTexture getSurfaceTexture(){
        return mSurface;
    }

}
