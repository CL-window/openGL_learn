package com.cl.slack.javagl.es_point_line_so;

import com.cl.slack.javagl.render.BaseOpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack
 * on 17/4/4 上午9:37.
 */

public class TriangleRender extends BaseOpenGLRenderer{

    private FloatBuffer vertex;
    public TriangleRender() {
        float vertexArray[] = {
                -0.8f, -0.4f * 1.732f, 0.0f,
                0.0f, -0.4f * 1.732f, 0.0f,
                -0.4f, 0.4f * 1.732f, 0.0f,

                0.0f, -0.0f * 1.732f, 0.0f,
                0.8f, -0.0f * 1.732f, 0.0f,
                0.4f, 0.4f * 1.732f, 0.0f,
        };

        ByteBuffer vbb
                = ByteBuffer.allocateDirect(vertexArray.length*4);
        vbb.order(ByteOrder.nativeOrder());
        vertex = vbb.asFloatBuffer();
        vertex.put(vertexArray);
        vertex.position(0);
    }

    private int index;
    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -4);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);
        index++;
        index%=10;
        switch(index){
            case 0:
            case 1:
            case 2:
                gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
                break;
            case 3:
            case 4:
            case 5:
                gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 6);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
                gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 6);
                break;
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }
}
