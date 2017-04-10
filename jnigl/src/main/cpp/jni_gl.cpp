#include <jni.h>
#include <GLES2/gl2.h>

# ifdef Debug
#include <android/log.h>
#define LOG_TAG "jni"
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
# endif

unsigned int vbo[2];
float positions[12] = {1, -1, 0, 1, 1, 0, -1, -1, 0, -1, 1, 0};
short indices[4] = {0, 1, 2, 3};


extern "C" {

JNIEXPORT void JNICALL
Java_com_cl_slack_jnigl_OpenGLJniLib_create(JNIEnv *env, jclass type) {

#ifdef Debug
    LOGE("create...");
# endif
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

}

JNIEXPORT void JNICALL
Java_com_cl_slack_jnigl_OpenGLJniLib_init(JNIEnv *env, jclass type, jint width, jint height) {

#ifdef Debug
    LOGE("init...");
# endif
    //

}

JNIEXPORT void JNICALL
Java_com_cl_slack_jnigl_OpenGLJniLib_draw(JNIEnv *env, jclass type) {

#ifdef Debug
    LOGE("draw...");
# endif
    glClear(GL_COLOR_BUFFER_BIT);

}

}
