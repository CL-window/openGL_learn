package com.cl.slack.camerafilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cl.slack.camerafilter.camerafilter.FilterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void photoFilterClick(View view) {
        startActivity(new Intent(this, PhotoFilterActivity.class));
    }

    public void previewFilterClick(View view) {
        startActivity(new Intent(this, PreviewFilterActivity.class));
    }

    public void FilterFromWebClick(View view) {
        startActivity(new Intent(this, FilterActivity.class));
    }
}
