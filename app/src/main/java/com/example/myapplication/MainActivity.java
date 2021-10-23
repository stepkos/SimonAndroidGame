package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Button> buttonHandlers;
    ArrayList<Integer> orderIndexes = new ArrayList<Integer>();
    Random random = new Random();

    public int iterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up handlers for buttons
        buttonHandlers = new ArrayList<Button>(Arrays.asList(
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4)
        ));

        setDefaultButtonColors();
        setEventListeners();
    }

    public void setDefaultButtonColors() {
        buttonHandlers.forEach(x -> x.setBackgroundColor(Color.GRAY));
    }

    public void setEventListeners() {
        buttonHandlers.stream().forEach(x -> x.setOnClickListener(view -> {
            if (!orderIndexes.isEmpty()) {
                if (buttonHandlers.indexOf(view) == orderIndexes.get(0)) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    orderIndexes.remove(0);
                    if (orderIndexes.isEmpty()) won();
                }
                else lose();
            }
        }));
    }

    public void start(View view) {

        orderIndexes.clear();
        for (int i=0; i<4; i++)
            orderIndexes.add(random.nextInt(4));

        iterator = 0;
        new CountDownTimer(4000, 1000) {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTick(long millisUntilFinished) {
                buttonHandlers.get(orderIndexes.get(iterator)).setBackgroundColor(Color.BLUE);

                new Handler().postDelayed(() -> {
                    setDefaultButtonColors();
                }, 500);

                iterator++;
            }

            @Override
            public void onFinish() { }

        }.start();

    }

    public void won() {
        Toast.makeText(this, "Won!", Toast.LENGTH_SHORT).show();
        orderIndexes.clear();
    }

    public void lose() {
        Toast.makeText(this, "Lose!", Toast.LENGTH_SHORT).show();
        orderIndexes.clear();
    }
    
}