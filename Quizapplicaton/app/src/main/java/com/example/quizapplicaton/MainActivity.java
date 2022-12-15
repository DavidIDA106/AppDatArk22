package com.example.quizapplicaton;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView totalQuestionsTextView;
    RelativeLayout back;
    TextView questionTextView;
    Button ans1, ansx, ans2;
    Button submitBtn;

    int score = 0;
    int totalQuestions = QuestionAnswer.question.length;
    int currentQuestionsIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.question);
        ans1 = findViewById(R.id.ans_1);
        ansx = findViewById(R.id.ans_x);
        ans2 = findViewById(R.id.ans_2);
        submitBtn = findViewById(R.id.submit);

        ans1.setOnClickListener(this);
        ansx.setOnClickListener(this);
        ans2.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Antal frågor: "+totalQuestions);

        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {
        ans1.setBackgroundColor(Color.WHITE);
        ansx.setBackgroundColor(Color.WHITE);
        ans2.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId() == R.id.submit){

            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionsIndex])){
                score++;
                submitBtn.setBackgroundColor(Color.GREEN);
            } else {
                score--;
                submitBtn.setBackgroundColor(Color.RED);
            }

            currentQuestionsIndex++;
            loadNewQuestion();
        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.BLUE);
        }
    }

    void loadNewQuestion(){
        if(currentQuestionsIndex == totalQuestions){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionsIndex]);

        ans1.setText(QuestionAnswer.choices[currentQuestionsIndex][0]);
        ansx.setText(QuestionAnswer.choices[currentQuestionsIndex][1]);
        ans2.setText(QuestionAnswer.choices[currentQuestionsIndex][2]);
    }

    void finishQuiz(){
        new AlertDialog.Builder(this)
            .setTitle("Score was: " + score)
            .setMessage("Score is " + score + " out of " + totalQuestions)
            .setPositiveButton("Starta om", ((dialogInterface, i) -> restartQuiz()))
            .setCancelable(false)
            .show();

    }

    void restartQuiz(){
        score = 0;
        currentQuestionsIndex = 0;
        loadNewQuestion();
    }
}