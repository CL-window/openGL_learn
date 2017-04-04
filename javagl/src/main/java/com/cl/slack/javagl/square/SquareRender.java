package com.cl.slack.javagl.square;

import com.cl.slack.javagl.render.BaseOpenGLRenderer;
import com.cl.slack.javagl.square.Square;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slack
 * on 17/3/31 下午6:28.
 */

public class SquareRender extends BaseOpenGLRenderer {

    private Square mSquare;

    public SquareRender() {
        mSquare = new Square();
    }

    /**
     * 因为OpenGL ES从当前位置开始渲染，缺省坐标为(0,0,0)，和View port 的坐标一样，相当于把画面放在眼前，
     * 对应这种情况OpenGL不会渲染离view Port很近的画面，因此我们需要将画面向后退一点距离：
     *  gl.glTranslatef(0,0, -4);
     */

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        // Replace the current matrix with the identity matrix 重置Matrix
        gl.glLoadIdentity();
        // Translates 4 units into the screen.
        gl.glTranslatef(0, 0, -4);
        // Draw our square.
        mSquare.draw(gl); // ( NEW )

    }
}
