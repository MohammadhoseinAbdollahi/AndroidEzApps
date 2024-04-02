package com.example.callsmsmanger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Makeintcall extends AppCompatActivity {
    TextView number;
    TextView prefixnumb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_makeintcall);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        number = findViewById(R.id.emrgnumber);
        prefixnumb = findViewById(R.id.prefixnumber);
//        number.setText(Public.emrgnumb);
//        prefixnumb.setText(Public.prefix);
        Button backbutton = findViewById(R.id.back);
        backbutton.setOnClickListener(v -> {
            if(Public.back_flag == 0) {
                Intent intent = new Intent(Makeintcall.this, MainActivity.class);
                startActivity(intent);
            }
            if(Public.back_flag == 1) {
                Intent intent = new Intent(Makeintcall.this, Call.class);
                startActivity(intent);
            }
            if(Public.back_flag == 2) {
                Intent intent = new Intent(Makeintcall.this, Sms.class);
                startActivity(intent);
            }
            if(Public.back_flag == 3) {
                Intent intent = new Intent(Makeintcall.this, Changenumber.class);
                startActivity(intent);
            }
        });
        Button callbutton = findViewById(R.id.callint);
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Makeintcall.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Makeintcall.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                } else {
                    makePhoneCall();
                }
            }
        });
        Button composebutton = findViewById(R.id.composeint);
        composebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(android.net.Uri.parse("tel:" + number.getText()));
                startActivity(intent);
            }
        });
        Button prefixbutton = findViewById(R.id.chprebutton);
        prefixbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.back_flag = 3;
                Intent intent = new Intent(Makeintcall.this, Changenumber.class);
                startActivityForResult(intent, CHOOSE_PREFIX_NUMB);
            }
        });
        Button emrgbutton = findViewById(R.id.emrgbutton);
        emrgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Public.back_flag = 3;
                Intent intent = new Intent(Makeintcall.this, Emergancynumber.class);
                startActivityForResult(intent, CHOOSE_EMRG_NUMB);

            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makePhoneCall() {
        TextView number = findViewById(R.id.Number);
        TextView prefixn= findViewById(R.id.prefix);
        String phoneNumber = number.getText().toString();
        String prefix = prefixnumb.getText().toString();

        if (phoneNumber.isEmpty() || prefix.isEmpty()) {
            Toast.makeText(this, "Number or prefix cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + prefixn + phoneNumber));
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_EMRG_NUMB) { // Identify activity
            if (resultCode == RESULT_OK) { // Activity succeeded
                String reply = data.getStringExtra(Emergancynumber.EXTRA_REPLY_EMRG);
                number.setText(reply);
            }}
        if (requestCode == CHOOSE_PREFIX_NUMB) { // Identify activity
            if (resultCode == RESULT_OK) { // Activity succeeded
                String reply = data.getStringExtra(Changenumber.EXTRA_REPLY);
                prefixnumb.setText(reply);
            }}
    }

    private static final int REQUEST_CALL_PERMISSION = 1;

    public static final int CHOOSE_EMRG_NUMB = 2;
    public static final int CHOOSE_PREFIX_NUMB = 3;
}

