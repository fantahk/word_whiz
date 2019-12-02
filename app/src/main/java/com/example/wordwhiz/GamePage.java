package com.example.wordwhiz;

// import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import java.util.Random;
// import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GamePage extends AppCompatActivity {

    /**
     * random - going to be used throughout for words and definitions.
     */
    private Random random = new Random();

    /**
     * currentScore- TextView that displays score
     */
    private TextView currentScore = findViewById(R.id.currentScore);

    /**
     * score- keeps track of score
     */
    private int score = 0;

    /**
     * answers - array of all four buttons, will randomly assign correct one.
     */
    private Button[] answers = {findViewById(R.id.answer0),
            findViewById(R.id.answer1),
            findViewById(R.id.answer2),
            findViewById(R.id.answer3)};

    /**
     * randomInt- used for setting random answer.
     */
    private int randomInt = random.nextInt(4);

    /**
     * correctAnswer - the right answer. randomly chosen.
     */
    private Button correctAnswer = answers[randomInt];

    /**
     * definition - display of definition
     */
    private TextView definition = findViewById(R.id.definition);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);
        // actual code starting now

        // not sure if 59-63 is right,, trying to figure out how to initialize game page for the first round
        currentScore.setText("Score: 0");
        answers[0].setText("New Word1" + score); // API to get random word
        answers[1].setText("New Word2" + score); // API to get random word
        answers[2].setText("New Word3" + score); // API to get random word
        answers[3].setText("New Word4" + score); // API to get random word
        newRound();
        // when right answer is chosen, call correctAnswerClicked()
        correctAnswer.setOnClickListener(unused -> correctAnswerClicked());
    }

    public void newRound() {
        // random word variable = API request for random word
        correctAnswer.setText("Correct Word"); // sets text of correct button to random word variable
        definition.setText("Definition: " + score);// set definition to API definition for random word variable
    }
    public void correctAnswerClicked() {
        // change button color to green
        correctAnswer.setBackgroundColor(Color.GREEN);
        // update score
        score++;
        currentScore.setText("Score: " + score);
        // go through answers array, only set text of answers that are wrong, set text to random word (moving to new round by setting new words)
        for (Button answer : answers) {
            if (answer != correctAnswer) {
                answer.setText("New Word" + score);
            }
        }
        newRound();
    }
}
