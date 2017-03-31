package com.cl.slack.javagl;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.cl.slack.javagl.render.BaseOpenGLRenderer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showOnlyBlackView();
    }

    private void showOnlyBlackView(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)

        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new BaseOpenGLRenderer());
        setContentView(view);
    }
}
