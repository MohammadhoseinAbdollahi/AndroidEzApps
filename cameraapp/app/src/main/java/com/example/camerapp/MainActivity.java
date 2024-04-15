package com.example.camerapp;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button cameraButton = findViewById(R.id.CameraButton);
        Button settingsButton = findViewById(R.id.SettingButton);
        cameraButton.setOnClickListener(v -> {
            // GO TO CAMERA ACTIVITY
             Intent intent = new Intent(this, Cameractivity.class);
             startActivity(intent);
            });
        settingsButton.setOnClickListener(v -> {
            // GO TO SETTINGS ACTIVITY
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });

    }
}