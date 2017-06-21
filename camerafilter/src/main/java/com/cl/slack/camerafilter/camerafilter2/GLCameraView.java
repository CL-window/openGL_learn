package com.cl.slack.camerafilter.camerafilter2;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.cl.slack.camerafilter.camerafilter2.base.FilterManager;
import com.cl.slack.camerafilter.camerafilter2.entity.FilterInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by xiang on 28/12/2016.
 */

public class GLCameraView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {


    private SurfaceTexture surfaceTexture;

    private Camera mCamera;
    private ByteBuffer mGLPreviewBuffer;
    private int mPreviewRotation = 90;

    private FilterManager filterManager;

    private Context mContext;

    private int mTextureId = 0;

    private SurfaceTexture mSurfaceTexture;

    private final float[] mSTMatrix = new float[16];

    private int mIncomingWidth = 640, mIncomingHeight = 480;

    private int mSurfaceWidth, mSurfaceHeight;

    private float mMvpScaleX = 1f, mMvpScaleY = 1f;

    private int mCamId = Camera.CameraInfo.CAMERA_FACING_FRONT;


    private TextureFrameAvailableListener mListener;

    private IntBuffer mGLFboBuffer;

    private ByteBuffer mRGBABuffer;

    private int mPrefixedSizeWidth = 320;

    private int mPrefixedSizeHeight = 240;


    public interface TextureFrameAvailableListener {

        void onFrameDataAvailable(byte[] buffer, int width, int height);

    }

    public GLCameraView(Context context) {
        this(context, null);
    }

    public GLCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        filterManager = FilterManager.builder().context(mContext)
                .defaultFilter(new FilterInfo(false, 1))
                .build();

        mGLFboBuffer = IntBuffer.allocate(mSurfaceWidth * mSurfaceHeight * 4);

        mRGBABuffer = ByteBuffer.allocate(mSurfaceWidth * mSurfaceHeight * 4);
    }


    public void setListenner(TextureFrameAvailableListener listener) {
        mListener = listener;
    }


    public void setCameraId(int id) {
        mCamId = id;
    }

    public int getCameraId() {
        return mCamId;
    }

    public boolean startCamera() {

        if (mCamera != null) {
            return false;
        }

        if (mCamId > (Camera.getNumberOfCameras() - 1) || mCamId < 0) {
            return false;
        }

        try {
            mCamera = Camera.open(mCamId);
        } catch (Exception e) {
            e.printStackTrace();
            mCamera = null;
            return false;
        }

        Camera.Parameters params = mCamera.getParameters();

        Camera.Size size = mCamera.new Size(mIncomingWidth, mIncomingHeight);

        if (!params.getSupportedPreviewSizes().contains(size) || !params.getSupportedPictureSizes().contains(size)) {
            return false;
        }

        params.setPreviewFormat(ImageFormat.NV21);
        params.setPreviewSize(mIncomingWidth, mIncomingHeight);

        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);

        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
            stopCamera();
            return false;
        }

        mCamera.startPreview();

        return true;
    }

    public void stopCamera() {

        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        Log.i("slack", "onSurfaceCreated...");
        filterManager.initialize();

        mTextureId = filterManager.createTexture();

        mSurfaceTexture = new SurfaceTexture(mTextureId);

        mSurfaceTexture.setOnFrameAvailableListener(this);

        startCamera();
    }

    public void setCameraPreviewSize(int width, int height) {
        mIncomingWidth = width;
        mIncomingHeight = height;

        float scaleHeight = mSurfaceWidth / (width * 1f / height * 1f);
        float surfaceHeight = mSurfaceHeight;

        mMvpScaleX = 1f;
        mMvpScaleY = scaleHeight / surfaceHeight;
        filterManager.scaleMVPMatrix(mMvpScaleX, mMvpScaleY);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        Log.i("slack", "onSurfaceChanged...");
        filterManager.updateSurfaceSize(width, height);

        mSurfaceWidth = width;
        mSurfaceHeight = height;

        setCameraPreviewSize(width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glViewport(0, 0, mSurfaceWidth, mSurfaceHeight);

        mSurfaceTexture.updateTexImage();

        mSurfaceTexture.getTransformMatrix(mSTMatrix);

        filterManager.drawFrame(mTextureId, mSTMatrix, mIncomingWidth, mIncomingHeight);

        mGLFboBuffer.rewind();

        /**
         * 第一个参数表示“设置像素的对齐值”，第二个参数表示实际设置为多少。这里像素可以单字节对齐（实际上就是不使用对齐）、
         * 双字节对齐（如果长度为奇数，则再补一个字节）、四字节对齐（如果长度不是四的倍数，则补为四的倍数）、八字节对齐。
         * 分别对应alignment的值为1, 2, 4, 8。实际上，默认的值是4，正好与BMP文件的对齐方式相吻合。
         */
        gl.glPixelStorei(GL10.GL_PACK_ALIGNMENT, 1);
        // see http://blog.csdn.net/ghost129/article/details/4409565
        gl.glReadPixels(0, 0, mSurfaceWidth, mSurfaceHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, mGLFboBuffer);

        Log.e("slack", "after glreadPixels " + mGLFboBuffer.remaining() + " " + mGLFboBuffer.capacity());


        //Log.e(" =====","after flip " + mGLFboBuffer.remaining() + " " + mGLFboBuffer.capacity());

        if (mListener != null) {

            mRGBABuffer.clear();
            mRGBABuffer.rewind();
            mRGBABuffer.asIntBuffer().put(mGLFboBuffer);

            //mListener.onFrameDataAvailable(filterManager.getGLFboBuffer(),mIncomingWidth,mIncomingHeight);
            mListener.onFrameDataAvailable(mRGBABuffer.array(), mSurfaceWidth, mSurfaceHeight);
            Log.e("slack", "mRGBABuffer lenght " + mRGBABuffer.array().length);
        }

    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
