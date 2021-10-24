package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Button> buttonHandlers;
    ArrayList<Integer> orderIndexes = new ArrayList<Integer>();
    Random random = new Random();
    TextView topText;
    View background;
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

        topText = findViewById(R.id.topText);
        background = findViewById(R.id.background);

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

        topText.setText("");
        background.setBackgroundColor(Color.WHITE);
        orderIndexes.clear(); // to be sure it is clear

        // generate random indexes of handlers to memorise
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
        topText.setText("You're won! Click start to play again");
        background.setBackgroundColor(Color.GREEN);
    }

    public void lose() {
        Toast.makeText(this, "Lose!", Toast.LENGTH_SHORT).show();
        orderIndexes.clear();
        topText.setText("You're lose! Click start to play again");
        background.setBackgroundColor(Color.RED);
    }
    
}