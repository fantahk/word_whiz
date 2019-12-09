package com.example.wordwhiz;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is the Loser Page. The Loser Page is where the user goes once he presses the wrong button. The Loser Page has two buttons try again and home. Try again takes you back to the game page to start another round. The Home button takes you to the home page.
 */
public class LoserPage extends AppCompatActivity {

    private RelativeLayout homeLayout;
    /**
     * Loser text used to display different texts based on the score of the user.
     */
    private TextView loserText;
    /**
     * game score - is the users latest score.
     */
    private TextView gameScore;
    /**
     * home - takes user back to home page.
     */
    private Button home;
    /**
     * try again - takes you back to game page for another round.
     */
    private Button tryAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loser_page);
        tryAgain = findViewById(R.id.tryAgain);
        home = findViewById(R.id.home);
        gameScore = findViewById(R.id.gameScore);
        loserText = findViewById(R.id.loserText);
        homeLayout = findViewById(R.id.homeLayout);
        gameScore.setText("Final Score : " + GamePage.score);
        GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFd93030, 0xFF962323});
        gradient.setCornerRadius(0f);
        if (GamePage.score <= 3) {
            loserText.setText("Looks like you need some work, better try again!");
        } else if (GamePage.score > 3 && GamePage.score <= 7) {
            loserText.setText("You're getting better! Try again to become a true word whiz!");
        } else if (GamePage.score > 7 && GamePage.score <= 11) {
            loserText.setText("WOW, you're basically a word whiz! Try again?");
        } else {
            loserText.setText("Ben Nordick-level skills have been unlocked. Congrats on becoming a true word whiz!");
        }
        homeLayout.setBackgroundDrawable(gradient);
        tryAgain.setOnClickListener(unused -> goToGame());
        home.setOnClickListener(unused -> goToHome());
    }

    /** this function takes you to the game page once try again button is clicked above.
     *
     */
    public void goToGame() {
        Intent intent = new Intent(this, GamePage.class);
        startActivity(intent);

    }

    /** this function takes you to the home page once the home button is clicked above.
     *
     */
    public void goToHome() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
