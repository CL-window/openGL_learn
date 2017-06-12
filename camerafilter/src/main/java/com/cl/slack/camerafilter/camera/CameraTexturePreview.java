package com.cl.slack.camerafilter.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack
 * on 17/6/11 上午10:41
 * 直接使用这个类作为预览，没有做任何处理
 */

public class CameraTexturePreview extends TextureView implements TextureView.SurfaceTextureListener{

    Context mContext;

    public CameraTexturePreview(Context context) {
        this(context, null);
    }

    public CameraTexturePreview(Context context, AttributeSet attrs) {
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
        openCamera(surface);
    }

    private void openCamera(SurfaceTexture surface) {
        CameraUtil.instance.startCamera();
        CameraUtil.instance.doStartPreview(surface);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        Log.i("slack", "onSurfaceTextureDestroyed...");
        CameraUtil.instance.stopCamera();
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

}
