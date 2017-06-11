package com.cl.slack.camerafilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.cl.slack.camerafilter.camera.CameraSurfaceView;
import com.cl.slack.camerafilter.camera.CameraTextureView;

public class PreviewFilterActivity extends AppCompatActivity {

//    private CameraGLSurfaceView mGLView = null;
    private CameraTextureView cameraView = null;
//    private CameraSurfaceView cameraView = null;
    private RelativeLayout previewLayout = null,GLpreviewLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_filter);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        RelativeLayout.LayoutParams layoutParams = null;

//        //GL绘制窗口
//        GLpreviewLayout = (RelativeLayout)findViewById(R.id.GLpreviewLayout);
//        layoutParams = new RelativeLayout.LayoutParams(640,480);
//        mGLView = new CameraGLSurfaceView(this);
//        GLpreviewLayout.addView(mGLView, layoutParams);

        //视频窗口
        previewLayout = (RelativeLayout)findViewById(R.id.previewLayout);
        layoutParams = new RelativeLayout.LayoutParams(480, 640);
        cameraView = new CameraTextureView(this);
//        cameraView = new CameraSurfaceView(this);
//        cameraView.setCameraPreviewCallback(mGLView);
        previewLayout.addView(cameraView, layoutParams);

    }
}
