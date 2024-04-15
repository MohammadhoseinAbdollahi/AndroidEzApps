package com.example.camerapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.media.ExifInterface;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cameractivity extends AppCompatActivity {
    ImageView picture1;
    ImageView picture2;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cameractivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        picture1 = findViewById(R.id.Picture1);
        picture2 = findViewById(R.id.Picture2);
        Button cameraButton = findViewById(R.id.CameraButton);

            cameraButton.setOnClickListener(v -> {
                dispatchTakePictureIntent();
            });

        picture1.setOnClickListener(v -> {
            String dateOfCapture = "Today , 19:35";

                // Show a toast with the date of capture
                Toast.makeText(Cameractivity.this, "Date of Capture = " + dateOfCapture, Toast.LENGTH_LONG).show();

        });

        picture2.setOnClickListener(v -> {
            String dateOfCapture = "Yesterday , 19:30";
                Toast.makeText(Cameractivity.this, "Date of Capture = " + dateOfCapture, Toast.LENGTH_LONG).show();

        });
        picture1.setOnLongClickListener(v -> {
        // Delete the image
        picture1.setImageDrawable(null);
        // Switch picture2 into picture1
        if (picture2.getDrawable() != null) {
            picture1.setImageDrawable(picture2.getDrawable());
            picture2.setImageDrawable(null);
        }
        return true; // return true to indicate the event is consumed
            });

        picture2.setOnLongClickListener(v -> {
            // Delete the image
            picture2.setImageDrawable(null);
            // Switch picture2 into picture1
            if (picture2.getDrawable() != null) {
                picture1.setImageDrawable(picture2.getDrawable());
                picture2.setImageDrawable(null);
            }
            return true; // return true to indicate the event is consumed
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picture1 = findViewById(R.id.Picture1);
        picture2 = findViewById(R.id.Picture2);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            if (picture1.getDrawable() == null) {
                picture1.setImageBitmap(imageBitmap);
            } else if (picture2.getDrawable() == null) {

                picture2.setImageBitmap(imageBitmap);
            }else {

                picture1.setImageDrawable(picture2.getDrawable());
                picture2.setImageBitmap(imageBitmap);

            }
        }
    }

    private List<Bitmap> getLastTwoImagesFromGallery() {
        List<Bitmap> images = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.Images.ImageColumns.DATE_ADDED + " DESC");
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int count = 0;
            while (cursor.moveToNext() && count < 2) {
                String imagePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                images.add(bitmap);
                count++;
            }
            cursor.close();
        }
        return images;
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CAMERA_PERMISSION) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, start the camera
            dispatchTakePictureIntent();
        } else {
            // Permission denied, show a message to the user
            Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
        }
    }
}
    private void dispatchTakePictureIntent() {
        // Check if the camera permission has been granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, start the camera
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                // Display error state to the user
                Toast.makeText(this, "There was a problem opening the camera app.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

}

