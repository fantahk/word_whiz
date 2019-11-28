package com.example.wordwhiz;

import android.content.Context;
import android.widget.Button;
import java.util.Random;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GamePage {

    /**
     * random - going to be used throughout for words and definitions.
     */
    private Random random = new Random();
    /**
     * gameLayout - the layout of the game page.
     */
    private RelativeLayout gameLayout;
    /**
     * currentScore- TextView that displays score
     */
    private TextView currentScore = gameLayout.findViewById(R.id.currentScore);
    /**
     * score- keeps track of score
     */
    private int score = 0;

    /**
     * answers - array of all four buttons, will randomly assign correct one.
     */
    private Button[] answers = {gameLayout.findViewById(R.id.answer0),
            gameLayout.findViewById(R.id.answer1),
            gameLayout.findViewById(R.id.answer2),
            gameLayout.findViewById(R.id.answer3)};

    /**
     * randomInt- used for setting random answer.
     */
    private int randomInt = random.nextInt(4);

    /**
     * definition - display of definition
     */
    private TextView definition = gameLayout.findViewById(R.id.definition);

    public void newRound() {
        Button correctAnswer = answers[randomInt];
        // random word variable = API request for random word
        correctAnswer.setText("Correct Word"); // Will turn into random word variable.
        // set definition to API definition for random word variable
    }
    public void correctAnswerClicked() {
        score++;
        currentScore.setText("Score: " + score);
       // definition.setText("Definition" + score); // Change to API stuff,, checking to make sure definition changes.
        answers[0].setText("New Word1" + score); // API to get random word
        answers[1].setText("New Word2" + score); // API to get random word
        answers[2].setText("New Word3" + score); // API to get random word
        answers[3].setText("New Word4" + score); // API to get random word
        newRound();
    }
}
