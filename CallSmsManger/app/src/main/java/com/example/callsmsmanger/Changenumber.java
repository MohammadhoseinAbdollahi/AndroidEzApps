package com.example.callsmsmanger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Changenumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_changenumber);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Public.back_flag == 0) {

                    Intent intent = new Intent(Changenumber.this, MainActivity.class);
                    startActivity(intent);
                }
                if(Public.back_flag == 1) {
                    Intent intent = new Intent(Changenumber.this, Call.class);
                    startActivity(intent);
                }
                if(Public.back_flag == 2) {
                    Intent intent = new Intent(Changenumber.this, Sms.class);
                    startActivity(intent);
                }
                if(Public.back_flag == 3) {
                    Public.back_flag = 0;
                    Intent intent = new Intent(Changenumber.this, Makeintcall.class);
                    startActivity(intent);
                }
            }
        });
        RadioButton it= findViewById(R.id.it);
        RadioButton us= findViewById(R.id.us);
        RadioButton ir= findViewById(R.id.ir);
        RadioButton es= findViewById(R.id.es);
        if(Public.preflag == 1)
            it.setChecked(true);
        if(Public.preflag == 2)
            us.setChecked(true);
        if(Public.preflag == 3)
            ir.setChecked(true);
        if(Public.preflag == 4)
            es.setChecked(true);
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.prefix = "+39 ";
                Public.preflag = 1;
            }
        });
        us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.prefix = "+1 ";
                Public.preflag = 2;
            }
        });
        ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.prefix = "+98 ";
                Public.preflag = 3;
            }
        });
        es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.prefix = "+34 ";
                Public.preflag = 4;
            }
        });
        Button select = findViewById(R.id.select);
        select.setOnClickListener(v -> {
            Intent intent = new Intent(Changenumber.this, Makeintcall.class);
            intent.putExtra(Changenumber.EXTRA_REPLY,Public.prefix);
            setResult(RESULT_OK, intent);
            finish();
            //startActivity(intent);
        });
        Button info = findViewById(R.id.info);
        info.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://en.wikipedia.org/wiki/List_of_international_call_prefixes"));
            startActivity(intent);
        });


    }
    public final static String EXTRA_REPLY = "com.example.myapp.RETURN_MESSAGE";

}