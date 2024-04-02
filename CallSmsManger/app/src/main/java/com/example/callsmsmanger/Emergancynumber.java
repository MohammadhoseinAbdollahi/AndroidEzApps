package com.example.callsmsmanger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Emergancynumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_emergancynumber);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RadioGroup emerga = findViewById(R.id.emrg);
        emerga.check(R.id.police);
        emerga.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.police) {
                Public.emrgnumb = "113";
            }
            if (checkedId == R.id.ambulance) {
                Public.emrgnumb = "118";
            }
            if (checkedId == R.id.firebrigade) {
                Public.emrgnumb = "115";
            }
            if(checkedId == R.id.emerg) {
                Public.emrgnumb = "112";
            }
        });
        if (Public.emrgnumb.equals("113")) {
            emerga.check(R.id.police);
        }
        if (Public.emrgnumb.equals("118")) {
            emerga.check(R.id.ambulance);
        }
        if (Public.emrgnumb.equals("115")) {
            emerga.check(R.id.firebrigade);
        }
        if (Public.emrgnumb.equals("112")) {
            emerga.check(R.id.emerg);
        }

        Button select = findViewById(R.id.select);
        select. setOnClickListener(v -> {
            Intent intent = new Intent(Emergancynumber.this, Makeintcall.class);
            intent.putExtra(Emergancynumber.EXTRA_REPLY_EMRG, Public.emrgnumb);
            setResult(RESULT_OK, intent);
            finish();
            //startActivity(intent);
        });
        Button backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Public.back_flag == 0) {
                    Intent intent = new Intent(Emergancynumber.this, MainActivity.class);
                    startActivity(intent);
                }
                if(Public.back_flag == 1) {
                    Intent intent = new Intent(Emergancynumber.this, Call.class);
                    startActivity(intent);
                }
                if(Public.back_flag == 2) {
                    Intent intent = new Intent(Emergancynumber.this, Sms.class);
                    startActivity(intent);
                }
                if(Public.back_flag == 3) {
                    Public.back_flag = 0;
                    Intent intent = new Intent(Emergancynumber.this, Makeintcall.class);
                    intent.putExtra(Emergancynumber.EXTRA_REPLY_EMRG, Public.emrgnumb);
                    setResult(RESULT_OK, intent);
                    finish();
                    //startActivity(intent);
                }
            }
        });
        
    }

    public final static String EXTRA_REPLY_EMRG = "com.example.myapp.RETURN_MESSAGE";
}