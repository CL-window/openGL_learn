package com.cl.slack.javagl;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.cl.slack.javagl.es_point_line_so.*;
import com.cl.slack.javagl.render.BaseOpenGLRenderer;
import com.cl.slack.javagl.square.SquareRender;
import com.cl.slack.javagl.texture.TextureRender;
import com.cl.slack.javagl.transformations.*;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();


//        showOnlyBlackView();
//        showSquareView();
//        showTransView();

//        showESView();
//        showES20View();
//        showES30View();
        showTextureView();
    }

    private void showTextureView() {
        mGLSurfaceView.setRenderer(new TextureRender(this,mGLSurfaceView,"bobrgb888.png"));
    }

    private void showES30View() {
        mGLSurfaceView = new com.cl.slack.javagl.es30.MyGLSurfaceView(this);
        setContentView(mGLSurfaceView);
    }

    private void showES20View() {
        mGLSurfaceView = new com.cl.slack.javagl.es20.MyGLSurfaceView(this);
        setContentView(mGLSurfaceView);
    }

    private void showESView() {
//        mGLSurfaceView.setRenderer(new PointRender());
//        mGLSurfaceView.setRenderer(new LineRender());
//        mGLSurfaceView.setRenderer(new TriangleRender());
        mGLSurfaceView.setRenderer(new DrawIcosahedron());
    }

    private void showTransView() {
//        mGLSurfaceView.setRenderer(new TransRender());
        mGLSurfaceView.setRenderer(new TransColorRender());
    }

    private void initView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        mGLSurfaceView = new GLSurfaceView(this);
        setContentView(mGLSurfaceView);
    }

    private void showSquareView() {
        mGLSurfaceView.setRenderer(new SquareRender());
    }

    private void showOnlyBlackView(){
        mGLSurfaceView.setRenderer(new BaseOpenGLRenderer());
    }
}
