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

    private ArrayList<Button> buttonHandlers;
    private ArrayList<Integer> orderIndexes = new ArrayList<>();
    private final ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(
       Color.GREEN, Color.RED,
       Color.YELLOW, Color.BLUE
    ));
    private final Random random = new Random();
    private TextView topText;
    private View background;
    private int iterator;
    private int lvl = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up handlers for buttons
        buttonHandlers = new ArrayList<>(Arrays.asList(
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

    private void setDefaultButtonColors() {
        buttonHandlers.forEach(x -> x.setBackgroundColor(Color.GRAY));
    }

    private void setEventListeners() {
        buttonHandlers.forEach(x -> x.setOnClickListener(view -> {
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

        topText.setText(String.format("Level %s", lvl-3));
        background.setBackgroundColor(Color.WHITE);
        orderIndexes.clear(); // to be sure it is clear

        // generate random indexes of handlers to memorise
        for (int i=0; i<lvl; i++)
            orderIndexes.add(random.nextInt(4));

        iterator = 0;
        new CountDownTimer(lvl * 1000L, 1000) {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTick(long millisUntilFinished) {

                int index = orderIndexes.get(iterator);
                buttonHandlers.get(index).setBackgroundColor(colors.get(index));

                new Handler().postDelayed(() -> {
                    setDefaultButtonColors();
                }, 500);

                iterator++;
            }

            @Override
            public void onFinish() { }

        }.start();

    }

    @SuppressLint("SetTextI18n")
    private void won() {
        Toast.makeText(this, "Won!", Toast.LENGTH_SHORT).show();
        orderIndexes.clear();
        topText.setText("You're won! Click start to go to the next lvl");
        background.setBackgroundColor(Color.GREEN);
        lvl++;
    }

    @SuppressLint("SetTextI18n")
    private void lose() {
        Toast.makeText(this, "Lose!", Toast.LENGTH_SHORT).show();
        orderIndexes.clear();
        topText.setText("You're lose! Click start to play again");
        background.setBackgroundColor(Color.RED);
        lvl = 4;
    }
    
}