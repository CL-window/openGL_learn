package com.cl.slack.javagl.es_point_line_so;

import com.cl.slack.javagl.render.BaseOpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack
 * on 17/4/4 上午9:30.
 */

/**
 * 首先是使用FloatBuffer 存放三个顶点坐标。
 * 使用glColor4f(float red, float green, float blue, float alpha) 将当前颜色设为红色。
 * glPointSize(float size) 可以用来设置绘制点的大小。
 * 使用glEnableClientState 打开Pipeline 的Vectex 顶点“开关”
 * 使用glVertexPointer 通知OpenGL ES图形库顶点坐标。
 * 使用GL_POINTS 模式使用glDrawArrays绘制3个顶点。
 */

public class PointRender extends BaseOpenGLRenderer{

    private FloatBuffer vertex;

    public PointRender() {

        float[] vertexArray = new float[]{
                -0.8f , -0.4f * 1.732f , 0.0f ,
                0.8f , -0.4f * 1.732f , 0.0f ,
                0.0f , 0.4f * 1.732f , 0.0f ,
        };

        ByteBuffer vbb
                = ByteBuffer.allocateDirect(vertexArray.length*4);
        vbb.order(ByteOrder.nativeOrder());
        vertex = vbb.asFloatBuffer();
        vertex.put(vertexArray);
        vertex.position(0);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        gl.glPointSize(8f);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        gl.glDrawArrays(GL10.GL_POINTS, 0, 3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
