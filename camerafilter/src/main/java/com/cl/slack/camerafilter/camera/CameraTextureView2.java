package com.cl.slack.camerafilter.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by slack
 * on 17/6/11 上午10:41
 * 可以显示 但是只能显示一个，预览的没有了
 */

public class CameraTextureView2 extends TextureView implements TextureView.SurfaceTextureListener{

    private SurfaceTexture surfaceTexture;

    public CameraTextureView2(Context context) {
        this(context, null);
    }

    public CameraTextureView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSurfaceTextureListener(this);
    }
    
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                                          int height) {
        // TODO Auto-generated method stub
        Log.i("slack", "onSurfaceTextureAvailable...");
        surfaceTexture = surface;
        mDirectDrawer = new DirectDrawer(mTextureID);
        mInputWindowSurface = new WindowSurface(new EglCore(EGL14.eglGetCurrentContext(), EglCore.FLAG_RECORDABLE),
                new Surface(surfaceTexture), true);
        mInputWindowSurface.makeCurrent();
    }

    int mTextureID = -1;
    DirectDrawer mDirectDrawer;
    WindowSurface mInputWindowSurface;
    public void updateTexImageId(int id) {
        mTextureID = id;
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        Log.i("slack", "onSurfaceTextureDestroyed...");
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
//        Log.i("slack", "onSurfaceTextureUpdated...");
        ///

    }

    public void onFrameAvailable() {
        mDirectDrawer.draw();
        mInputWindowSurface.makeCurrent();
        mInputWindowSurface.setPresentationTime(surfaceTexture.getTimestamp());
        mInputWindowSurface.swapBuffers();
    }

}
