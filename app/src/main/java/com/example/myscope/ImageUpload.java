package com.example.myscope;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myscope.activities.BaseActivity;

import java.io.File;

public class ImageUpload extends BaseActivity {
Button image_upload;
    final int TAKE_PICTURE = 1;
    private Uri imageUri;
    ImageView imagepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        image_upload = (Button)findViewById(R.id.imageupload);
        imagepic = (ImageView)findViewById(R.id.image);
        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });

    }
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(photo));
//        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    //Uri imageUri;
//                    Uri selectedImage = imageUri;
//                    getContentResolver().notifyChange(selectedImage, null);
                    Bitmap photo = (Bitmap) data.getExtras()
                            .get("data");

                    // Set the image in imageview for display
                    imagepic.setImageBitmap(photo);
//                    ImageView imageView = (ImageView) findViewById(R.id.image);
//                    ContentResolver cr = getContentResolver();
//                        Bitmap bitmap;
//                        try {
//                        bitmap = android.provider.MediaStore.Images.Media
//                                .getBitmap(cr, selectedImage);
//
//                        imageView.setImageBitmap(bitmap);
//                        Toast.makeText(this, "This file: " + selectedImage.toString(),
//                                Toast.LENGTH_LONG).show();

                    }
                }
        }
    }

