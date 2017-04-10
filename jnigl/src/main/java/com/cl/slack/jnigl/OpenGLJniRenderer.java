package com.cl.slack.jnigl;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack
 * on 17/3/30 下午3:52.
 */

public class OpenGLJniRenderer implements GLSurfaceView.Renderer{

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        OpenGLJniLib.create();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        OpenGLJniLib.init(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        OpenGLJniLib.draw();
    }

}
