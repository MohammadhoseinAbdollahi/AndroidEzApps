package com.example.callsmsmanger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button callButton = findViewById(R.id.call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.back_flag = 0;
                Intent intent = new Intent(MainActivity.this, Call.class);
                startActivity(intent);
            }
        });
        Button smsButton = findViewById(R.id.sms);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.back_flag = 0;
                Intent intent = new Intent(MainActivity.this, Sms.class);
                startActivity(intent);
            }
        });
        Button changeNumberButton = findViewById(R.id.prefix2);
        changeNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.back_flag = 0;
                Intent intent = new Intent(MainActivity.this, Changenumber.class);
                startActivity(intent);
            }
        });
        Button makeintcallButton = findViewById(R.id.makeintcallbutton);
        makeintcallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.back_flag = 0;
                Intent intent = new Intent(MainActivity.this, Makeintcall.class);
                startActivity(intent);
            }
        });
    }


}