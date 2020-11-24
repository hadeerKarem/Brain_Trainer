package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    TextView timerTextView;
    TextView equationTextView;
    TextView scoreTextView;
    GridLayout answersGridLayout;
    Button answerButton1;
    Button answerButton2;
    Button answerButton3;
    Button answerButton4;
    TextView resultTextView;
    Button playAgainButton;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    int score = 0, x, y;
    int numberOfQuestions = 0;
    boolean gameActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        timerTextView = findViewById(R.id.timerTextView);
        equationTextView = findViewById(R.id.equationTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        answersGridLayout = findViewById(R.id.answersGridLayout);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        resultTextView = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);

        generateQuestion();
    }

    public void startGame(View view) {
        goButton.setVisibility(View.INVISIBLE);

        timerTextView.setVisibility(View.VISIBLE);
        equationTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        answersGridLayout.setVisibility(View.VISIBLE);

        startTimer();
        gameActive=true;
    }

    public void chooseAnswer(View view) {
        resultTextView.setVisibility(View.VISIBLE);
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Log.i("Info", "CORRECT");
            resultTextView.setText(R.string.correct);
            if(gameActive){
                score++;
            }
        }
        else {
            Log.i("Info", "Wrong");
            resultTextView.setText(R.string.wrong);
        }
        Log.i("Info", "No. Of questions"+numberOfQuestions);
        if (gameActive){
            numberOfQuestions++;
        }
        scoreTextView.setText(score + "/" + numberOfQuestions);
        if (gameActive) {
            generateQuestion();
        }
    }

    public void generateQuestion() {
        Random random = new Random();

        x = random.nextInt(21);
        y = random.nextInt(21);

        equationTextView.setText(Integer.toString(x) + " + " + Integer.toString(y));

        locationOfCorrectAnswer = random.nextInt(4);
        answers.clear();

        for (int i=0; i<4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(x+y);
            }
            else {
                int wrongAnswer = random.nextInt(41);
                while (wrongAnswer == x+y) {
                    wrongAnswer = random.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        answerButton1.setText(Integer.toString(answers.get(0)));
        answerButton2.setText(Integer.toString(answers.get(1)));
        answerButton3.setText(Integer.toString(answers.get(2)));
        answerButton4.setText(Integer.toString(answers.get(3)));
    }

    public void playAgain(View view) {
        timerTextView.setText("30s");
        score=0;
        numberOfQuestions=0;
        scoreTextView.setText(score+"/"+numberOfQuestions);
        resultTextView.setText("");
        generateQuestion();
        gameActive = true;
        startTimer();
        playAgainButton.setVisibility(View.INVISIBLE);
    }

    public void startTimer(){
        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long l) {
                Log.i("Info" , "Timer started");
                timerTextView.setText(String.valueOf(l/1000)+"s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                playAgainButton.setVisibility(View.VISIBLE);
                gameActive = false;
            }
        }.start();
    }
}