package com.example.wordwhiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * HomePage is the page that first launches when the app is run. At the top of the screen, the rules are
 * displayed in a text box. The high score of the player is displayed below that. At the center is
 * a start button that launches GamePage. At the bottom of the screen is the word of the day, which
 * will be a random word and definition that is randomized each time the home screen is visited.
 */
public class HomePage extends AppCompatActivity {
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Button start = findViewById(R.id.start);
        start.setOnClickListener(unused -> goToGame());
    }
    public void goToGame() {
        Intent intent = new Intent(this, GamePage.class);
    }
}
