package com.cl.slack.decodeencodemp4;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cl.slack.decodeencodemp4.decode.EncodeDecodeSurface;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startDecode(View view) {
        String src = new File(Environment.getExternalStorageDirectory(),"slack/test.mp4").getAbsolutePath();
        String desc = new File(Environment.getExternalStorageDirectory(),"slack/test_out.mp4").getAbsolutePath();
        EncodeDecodeSurface test = new EncodeDecodeSurface(src,desc);
        try {
            test.testEncodeDecodeSurface();
        } catch (Throwable a) {
            a.printStackTrace();
        }
    }
}
