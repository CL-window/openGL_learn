package com.cl.slack.jnigl;

/**
 * Created by slack
 * on 17/3/30 下午3:53.
 */

public class OpenGLJniLib {

    static {
        System.loadLibrary("jni_gl");
    }

    public static native void init(int width,int height);

    public static native void create();

    public static native void draw();

}
