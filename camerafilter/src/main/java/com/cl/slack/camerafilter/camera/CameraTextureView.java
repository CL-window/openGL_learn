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
        CameraUtil.instance.startCamera();
        CameraUtil.instance.doStartPreview(mSurface);
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

    /* 让Activity能得到TextureView的SurfaceTexture
     * @see android.view.TextureView#getSurfaceTexture()
     */
    public SurfaceTexture getSurfaceTexture(){
        return mSurface;
    }

}
