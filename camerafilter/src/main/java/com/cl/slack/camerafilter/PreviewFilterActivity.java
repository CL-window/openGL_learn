package com.cl.slack.camerafilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.cl.slack.camerafilter.camera.CameraGLSurfaceView;
import com.cl.slack.camerafilter.camera.CameraSurfaceView;
import com.cl.slack.camerafilter.camera.CameraTextureView;
import com.cl.slack.camerafilter.camera.CameraTextureView2;
import com.cl.slack.camerafilter.camera.CameraUtil;

public class PreviewFilterActivity extends AppCompatActivity {

    private CameraGLSurfaceView mCameraGLSurfaceView = null;
    private CameraSurfaceView mGLView = null;
    private CameraTextureView cameraView = null;
    private RelativeLayout previewLayout = null, GLpreviewLayout = null;

    private CameraTextureView2 mViewCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_filter);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RelativeLayout.LayoutParams layoutParams = null;

        //视频窗口
        previewLayout = (RelativeLayout) findViewById(R.id.previewLayout);
        layoutParams = new RelativeLayout.LayoutParams(480, 640);
        cameraView = new CameraTextureView(this);
        cameraView.addPreviewFramCallback(mPreviewFrameCallback);
        previewLayout.addView(cameraView, layoutParams);

        //GL绘制窗口
        GLpreviewLayout = (RelativeLayout) findViewById(R.id.GLpreviewLayout);
        layoutParams = new RelativeLayout.LayoutParams(480, 640);
        mViewCopy = new CameraTextureView2(this);
        mViewCopy.updateTexImageId(cameraView.getSurfaceId());
        GLpreviewLayout.addView(mViewCopy, layoutParams);

        //GL绘制窗口
//        GLpreviewLayout = (RelativeLayout) findViewById(R.id.GLpreviewLayout);
//        layoutParams = new RelativeLayout.LayoutParams(480, 640);
//        mGLView = new CameraSurfaceView(this, false);
//        GLpreviewLayout.addView(mGLView, layoutParams);



//        mCameraGLSurfaceView = (CameraGLSurfaceView) findViewById(R.id.gl_surface_view);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("slack", "menu create...");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    /**
     * CameraTextureView ---(画面)--> CameraSurfaceView
     * TODO 卡的要死
     */
    private CameraUtil.PreviewFrameCallback mPreviewFrameCallback =
            new CameraUtil.PreviewFrameCallback() {
                @Override
                public void onPreviewFrame(byte[] data) {
                    if(mViewCopy != null) {
                        mViewCopy.onFrameAvailable();
                    }
                }
            };

    @Override
    protected void onResume() {
        super.onResume();
        if(mCameraGLSurfaceView != null) {
            mCameraGLSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mCameraGLSurfaceView != null) {
            mCameraGLSurfaceView.onPause();
        }
    }
}
