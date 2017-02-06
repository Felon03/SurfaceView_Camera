package com.example.preview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CaptureActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        imageView = (ImageView) findViewById(R.id.preview_img);
        Intent intent = getIntent();
        if (intent != null) {
            byte[] bis = intent.getByteArrayExtra("bitmap");
            bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            /* 旋转图片*/
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotaBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            imageView.setImageBitmap(rotaBitmap);
        }


    }
}
