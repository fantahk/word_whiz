package com.example.wordwhiz;

// import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import java.util.Random;
// import android.widget.RelativeLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GamePage extends AppCompatActivity {

    /**
     *
     */
    private RelativeLayout gameLayout;
    /**
     * random - going to be used throughout for words and definitions.
     */
    private Random random;

    /**
     * currentScore- TextView that displays score
     */
    private TextView currentScore;

    /**
     * score- keeps track of score
     */
    private int score;

    /**
     * answers - array of all four buttons, will randomly assign correct one.
     */
    private Button[] answers;


    /**
     * correctAnswer - the right answer. randomly chosen.
     */
    private Button correctAnswer;

    /**
     * definition - display of definition
     */
    private TextView definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);
        // actual code starting now

        // variable initialization
        gameLayout = findViewById(R.id.gameLayout);
        random = new Random();
        currentScore = gameLayout.findViewById(R.id.currentScore);
        score = 0;
        answers = new Button[]{findViewById(R.id.answer0),
                findViewById(R.id.answer1),
                findViewById(R.id.answer2),
                findViewById(R.id.answer3)};
        correctAnswer = answers[random.nextInt(4)];
        definition = findViewById(R.id.definition);

        // not sure if 59-63 is right,, trying to figure out how to initialize game page for the first round
        currentScore.setText("Score: 0");
        answers[0].setText("New Word1" + score); // API to get random word
        answers[1].setText("New Word2" + score); // API to get random word
        answers[2].setText("New Word3" + score); // API to get random word
        answers[3].setText("New Word4" + score); // API to get random word
        newRound();
        // when right answer is chosen, call correctAnswerClicked()
        correctAnswer.setOnClickListener(unused -> correctAnswerClicked());
        for (Button answer : answers) {
            if (answer != correctAnswer) {
                answer.setOnClickListener(unused -> wrongAnswerClicked());
            }
        }
    }

    public void newRound() {
        // random word variable = API request for random word
        correctAnswer.setText("Correct Word"); // sets text of correct button to random word variable
        definition.setText("Definition: " + score);// set definition to API definition for random word variable
    }
    public void correctAnswerClicked() {
        // change button color to green later

        
        // update score
        score++;
        currentScore.setText("Score: " + score);
        // make new button the correct one
        correctAnswer.setOnClickListener(unused -> wrongAnswerClicked());
        correctAnswer = answers[random.nextInt(4)];
        correctAnswer.setOnClickListener(unused -> correctAnswerClicked());
        // go through answers array, only set text of answers that are wrong, set text to random word (moving to new round by setting new words)
        for (Button answer : answers) {
            if (answer != correctAnswer) {
                answer.setText("New Word" + score);
            }
        }
        newRound();
    }
    public void wrongAnswerClicked() {
        for (Button answer : answers) {
            if (answer != correctAnswer) {
                System.out.println("Wrong choice!");
            }
        }
    }
}
