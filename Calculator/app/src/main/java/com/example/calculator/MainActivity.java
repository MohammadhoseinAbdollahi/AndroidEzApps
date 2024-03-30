package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    double result = 0;
    int flag_par = 0; // 0-> + // 1->* // 2->/ // 3->- // 4->clear
    int flag_act = 1;
    double res1 = 0;
    double res2 = 0;
    int counter = 1;
    double res_show = 0;
    double decimalpoint = 1;
    int decimal_flag = 0;

    double resulty = 0;
    private double calc ( int counter, int numb) {
        if(decimal_flag==0){
            if (flag_act == 1 && counter != 0 ) {
                res1 = res1 * 10 + numb;
                return res1;
            } else if (flag_act == 1) {
                res1 = res1 + numb;
                return res1;
            }
            if (flag_act == 2 && counter != 0 ) {
                res2 = res2 * 10 + numb;
                return res2;
            } else if (flag_act == 2) {
                res2 = res2 + numb;
                return res1;
            }
        }
        else{
            if(flag_act == 1&& counter != 0) {
                res1 = res1  + (numb*decimalpoint);
                decimalpoint=decimalpoint/10;
                return res1;
            } else if (flag_act == 1) {
                res1 = res1 + numb;
                decimalpoint=decimalpoint/10;
                return res1;
            }
            if (flag_act == 2&& counter != 0) {
                res2 = res2  + (numb * decimalpoint);
                decimalpoint=decimalpoint/10;
                return res2;
            } else if (flag_act == 2) {
                res2 = res2 + numb;
                decimalpoint=decimalpoint/10;
                return res1;
            }
            decimalpoint=decimalpoint/10;
        }

        return 0;
    }
    private void decimalmaker (){
         if(decimal_flag ==0){decimalpoint=decimalpoint/10;}
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("result", result);
        savedInstanceState.putDouble("res1",  res1); // Save res1 as an integer
        savedInstanceState.putDouble("res2",  res2); // Save res2 as an integer
        savedInstanceState.putInt("flag_par", flag_par);
        savedInstanceState.putInt("flag_act", flag_act);
        savedInstanceState.putInt("counter", counter);
        savedInstanceState.putInt("decimal_flag", decimal_flag);
        savedInstanceState.putDouble("decimalpoint", decimalpoint);


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            result = savedInstanceState.getDouble("result");
            res1 = savedInstanceState.getDouble("res1");
            res2 = savedInstanceState.getDouble("res2");
            flag_par = savedInstanceState.getInt("flag_par");
            flag_act = savedInstanceState.getInt("flag_act");
            counter = savedInstanceState.getInt("counter");
            decimal_flag = savedInstanceState.getInt("decimal_flag");
            decimalpoint = savedInstanceState.getDouble("decimalpoint");
            TextView res = findViewById(R.id.result);
            if(flag_act==1){
                res.setText(String.valueOf(res1));
            }
            else if(flag_act==2){
                res.setText(String.valueOf(res2));
            }
            else if(flag_act==3){
                res.setText(String.valueOf(result));
            }


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button one = findViewById(R.id.one);
        Button two = findViewById(R.id.two);
        Button three = findViewById(R.id.three);
        Button four = findViewById(R.id.four);
        Button five = findViewById(R.id.five);
        Button six = findViewById(R.id.six);
        Button seven = findViewById(R.id.seven);
        Button eight = findViewById(R.id.eight);
        Button nine = findViewById(R.id.nine);
        Button zero = findViewById(R.id.zero);
        Button plus = findViewById(R.id.plus);
        Button min = findViewById(R.id.min);
        Button mul = findViewById(R.id.mult);
        Button div = findViewById(R.id.divide);
        Button equal = findViewById(R.id.equal);
        Button clear = findViewById(R.id.clear);
        Button dot = findViewById(R.id.decimal);
        TextView res = findViewById(R.id.result);

        one.setOnClickListener(v -> {
            res_show = calc(counter, 1);
            counter=1;
            res.setText(String.valueOf(res_show));
        });
        two.setOnClickListener(v -> {
            res_show = calc(counter, 2);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        three.setOnClickListener(v -> {
            res_show = calc(counter, 3);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        four.setOnClickListener(v -> {
            res_show = calc(counter, 4);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        five.setOnClickListener(v -> {
            res_show = calc(counter, 5);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        six.setOnClickListener(v -> {
            res_show = calc(counter, 6);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        seven.setOnClickListener(v -> {
            res_show = calc(counter, 7);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        eight.setOnClickListener(v -> {
            res_show = calc(counter, 8);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        nine.setOnClickListener(v -> {
            res_show = calc(counter, 9);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        zero.setOnClickListener(v -> {
            res_show = calc(counter, 0);
            counter=1;

            res.setText(String.valueOf(res_show));
        });

        plus.setOnClickListener(v -> {
            flag_par = 0;
            flag_act =2;
            counter=1;
            decimal_flag=0;
            //res.setText(String.valueOf(0));
        });
        min.setOnClickListener(v -> {
            flag_par = 3; // for subtraction operation
            flag_act =2;
            counter=1;
            decimal_flag=0;
           // res.setText(String.valueOf(0));

        });

        mul.setOnClickListener(v -> {
            flag_par = 1; // for multiplication operation
            flag_act =2;
            counter=1;
            decimal_flag=0;
            //res.setText(String.valueOf(0));

        });

        div.setOnClickListener(v -> {
            flag_par = 2; // for division operation
            flag_act =2;
            counter=1;
            decimal_flag=0;
           // res.setText(String.valueOf(0));


        });

        clear.setOnClickListener(v -> {
            res1 = 0;
            res2 = 0;
            counter = 1;
            flag_par = 0;
            res_show = 0;
            flag_act = 1;
            res.setText(String.valueOf(0));
            decimalpoint = 1;
            decimal_flag = 0;
            resulty=0;

        });
        dot.setOnClickListener(v -> {
            decimalmaker();
            decimal_flag=1;

        });

        equal.setOnClickListener(v -> {
            if (flag_par == 0) { // Addition
                result = res1 + res2;
            } else if (flag_par == 1) { // Multiplication
                result = res1 * res2;
            } else if (flag_par == 2) { // Division
                // Check if res2 is not zero to avoid division by zero
                if (res2 != 0) {
                    result = res1 / res2;
                } else {
                    // Handle division by zero error
                    res.setText("Error: Division by zero");
                    return;
                }
            } else if (flag_par == 3) { // Subtraction
                result = res1 - res2;
            }
            res.setText(String.valueOf(result));
            flag_act=3;
            res1 = result;
            res2 = 0;
        });

        }
    }

