package com.cl.slack.javagl.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;

import com.cl.slack.javagl.render.BaseOpenGLRenderer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack
 * on 17/4/4 下午12:30.
 */

/**
 * OpenGL纹理是一种位图，可以把它粘贴到OpenGL物体的表面上。比如可以获取一张邮票的图像粘贴到正方形中
 * ，使正 方形看起来像一张邮票。要使邮票保持合适的方向，以便图像井然有序地排列，则必须获得形状的每个顶点并在正方形上标记出来，
 * 以便邮票和正方形的形状保持一 致
 */
public class TextureRender extends BaseOpenGLRenderer {

    /**
     * 处理纹理坐标与顶点坐标之间的映射关系
     */
    /**
     * xyz 2D原点在屏幕中心部分
     */
    FloatBuffer vertices;
    /**
     * 原点在 左上角 右 X+  下 Y+
     */
    FloatBuffer texture;
    ShortBuffer indices;

    private int textureId;
    private Context context;
    private String filePath;
    private GLSurfaceView glSurfaceView;
    public TextureRender(Context context,GLSurfaceView glSurfaceView, String assertFileName) {
        this.context = context;
        this.glSurfaceView = glSurfaceView;
        this.filePath = assertFileName;

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertices = byteBuffer.asFloatBuffer();
//            vertices.put( new float[] {  -80f,   -120f,0,1f,
//                                         80f,  -120f, 1f,1f,
//                                         -80f, 120f, 0f,0f,
//                                         80f,120f,   1f,0f});
//        vertices.put( new float[] {  -80f,   -120f,
//                80f,  -120f,
//                -80f, 120f,
//                80f,  120f});
        vertices.put( new float[] {  -64f,   -64f,
                64f,  -64f,
                -64f, 64f,
                64f,  64f});

//        vertices.put( new float[] {  -1f,   -1f,
//                1f,  -1f,
//                -1f, 1f,
//                1f,  1f});

        ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(6 * 2);
        indicesBuffer.order(ByteOrder.nativeOrder());
        indices = indicesBuffer.asShortBuffer();
        indices.put(new short[] { 0, 1, 2,1,2,3});

        ByteBuffer textureBuffer = ByteBuffer.allocateDirect(4 * 2 * 4);
        textureBuffer.order(ByteOrder.nativeOrder());
        texture = textureBuffer.asFloatBuffer();
        texture.put( new float[] { 0,1f,
                1f,1f,
                0f,0f,
                1f,0f});

        indices.position(0);
        vertices.position(0);
        texture.position(0);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        textureId = loadTexture(context,filePath,gl);
        // 定义显示在屏幕上的什么位置(opengl 自动转换)
        gl.glViewport(0, 0,glSurfaceView.getWidth() ,glSurfaceView.getHeight());
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-160, 160, -240, 240, 1, -1);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        //绑定纹理ID
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texture);
        // gl.glRotatef(1, 0, 1, 0);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 6,
                GL10.GL_UNSIGNED_SHORT, indices);

    }

    private int loadTexture(Context context,String fileName,GL10 gl) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(
                    fileName));
            return loadTexture(bitmap,gl);
        } catch (IOException e) {
            Log.d("TexturedRectangleTest",
                    "couldn't load asset 'bobrgb888.png'!");
            throw new RuntimeException("couldn't load asset '" + fileName
                    + "'");
        }
    }

    private int loadTexture(Bitmap bitmap,GL10 gl) {
        int textureIds[] = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        int textureId = textureIds[0];
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        bitmap.recycle();
        return textureId;
    }
}
