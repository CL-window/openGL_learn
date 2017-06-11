package com.cl.slack.camerafilter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cl.slack.camerafilter.filter.TextureRenderer;

/**
 * 对一张图片进行滤镜处理
 * created by slack
 * on 17/6/9 下午5:20
 */
public class PhotoFilterActivity extends AppCompatActivity {

    private final static int KEY_OPEN_ALBUM = 0x01;

    private GLSurfaceView mEffectView;

    private TextureRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_filter);

        openSystemAlbum();
    }

    private void openSystemAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, KEY_OPEN_ALBUM);
    }

    private void initFilter(Bitmap bitmap) {
        renderer = new TextureRenderer();
        renderer.setImageBitmap(bitmap);
        renderer.setCurrentEffect(R.id.none);

        mEffectView = (GLSurfaceView) findViewById(R.id.effectsview);
        //mEffectView = new GLSurfaceView(this);
        mEffectView.setEGLContextClientVersion(2);
        //mEffectView.setRenderer(this);
        mEffectView.setRenderer(renderer);
        mEffectView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("slack", "menu create...");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        renderer.setCurrentEffect(item.getItemId());
        mEffectView.requestRender();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == KEY_OPEN_ALBUM && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            showPhoto(picturePath);
        }
    }

    private void showPhoto(String picturePath) {
        if(picturePath.equals(""))
            return;
        // 缩放图片, width, height 按相同比例缩放图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        // options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
        int scale = (int)( options.outWidth / (float)300);
        if(scale <= 0)
            scale = 1;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(picturePath, options);

        initFilter(bitmap);
    }
}
