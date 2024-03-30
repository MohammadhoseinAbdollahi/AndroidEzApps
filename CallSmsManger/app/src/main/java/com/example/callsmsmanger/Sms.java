package com.example.callsmsmanger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView msg = findViewById(R.id.msg);
        TextView number = findViewById(R.id.number2);
        number.setText(Public.prefix);
        Button prefixbutton = findViewById(R.id.prefix);
        prefixbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.back_flag = 2;
                Intent intent = new Intent(Sms.this, Changenumber.class);
                startActivity(intent);
            }
        });
        Button backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sms.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button smsbutton = findViewById(R.id.send);
        smsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(android.net.Uri.parse("smsto:" + number.getText()));
                intent.putExtra("sms_body", msg.getText().toString());
                startActivity(intent);
            }
        });
    }
}