package com.cl.slack.camerafilter.camera;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.cl.slack.camerafilter.camerafilter.MyGLUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by slack
 * on 17/6/11 上午10:45
 */

public class CameraUtil implements Camera.PreviewCallback {

    public static CameraUtil instance = new CameraUtil();

    private int srcFrameWidth = 640;
    private int srcFrameHeight = 480;
    private Camera camera = null;
    private int curCameraIndex = -1;
    private boolean isPreviewing;
    private Queue<PreviewFrameCallback> mCallbacks = new ArrayDeque<>();

    private CameraUtil() {
    }

    public void addPreviewFramCallback(PreviewFrameCallback callback) {
        mCallbacks.add(callback);
    }

    public void removePreviewFramCallback(PreviewFrameCallback callback) {
        mCallbacks.remove(callback);
    }

    public void switchCamera() {
        int num = Camera.getNumberOfCameras();
        if (num > 1) {
            curCameraIndex = (curCameraIndex + 1) % num;
        } else {
            curCameraIndex = 0;
        }
    }

    // 根据索引初始化摄像头
    private void openCamera(int cameraIndex) {
        // 初始化并打开摄像头
        if (camera == null) {
            try {
                camera = Camera.open(cameraIndex);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            initCamera();
        }
    }

    private void initCamera() {
        Camera.Parameters params = camera.getParameters();
        if (params.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            // 自动对焦
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        try {
            params.setPreviewFormat(ImageFormat.NV21);
            params.setPreviewSize(srcFrameWidth, srcFrameHeight);

            params = camera.getParameters();
            params.setPreviewFpsRange(15 * 1000, 30 * 1000);

            camera.setParameters(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开摄像头
    public void startCamera() {
        switchCamera();
        openCamera(curCameraIndex);
    }

    public int getPreviewHeight() {
        return camera.getParameters().getPreviewSize().height;
    }

    public int getPreviewWidth() {
        return camera.getParameters().getPreviewSize().width;
    }

    private void startPreview() {
        isPreviewing = true;
        camera.startPreview();
        camera.setPreviewCallback(this);
        camera.setDisplayOrientation(90);
    }

    /**
     * 使用Surfaceview开启预览
     *
     * @param holder
     */
    public void doStartPreview(SurfaceHolder holder) {
        Log.i("slack", "doStartPreview...");
        if (isPreviewing) {
            camera.stopPreview();
            return;
        }
        if (camera != null) {
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            startPreview();
        }
    }

    public void doStartPreview() {
        Log.i("slack", "doStartPreview...");
        if (isPreviewing) {
            camera.stopPreview();
            return;
        }
        if (camera != null) {
            try {
                camera.setPreviewTexture(new SurfaceTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            startPreview();
        }
    }

    public void doStartPreview(SurfaceTexture surface) {
        Log.i("slack", "doStartPreview...");
        if (isPreviewing) {
            camera.stopPreview();
            return;
        }

        if (camera != null) {
            try {
                camera.setPreviewTexture(surface);
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
            startPreview();
        }

    }

    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int displayDegree;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayDegree = (info.orientation + degrees) % 360;
            displayDegree = (360 - displayDegree) % 360;  // compensate the mirror
        } else {
            displayDegree = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(displayDegree);
    }

    // 停止并释放摄像头
    public void stopCamera() {
        curCameraIndex = -1;
        isPreviewing = false;
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        camera.addCallbackBuffer(data);
        //
        for (PreviewFrameCallback callback : mCallbacks) {
            callback.onPreviewFrame(data);
        }
    }

    public interface PreviewFrameCallback {
        void onPreviewFrame(byte[] data);
    }
}
