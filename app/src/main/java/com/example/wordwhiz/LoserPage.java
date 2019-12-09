package com.example.wordwhiz;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoserPage extends AppCompatActivity {
    private TextView loserText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loser_page);
        Button tryAgain = findViewById(R.id.tryAgain);
        Button home = findViewById(R.id.home);
        TextView gameScore = findViewById(R.id.gameScore);
        loserText = findViewById(R.id.loserText);
        RelativeLayout homeLayout = findViewById(R.id.homeLayout);
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
    public void goToGame() {
        Intent intent = new Intent(this, GamePage.class);
        startActivity(intent);

    }
    public void goToHome() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
