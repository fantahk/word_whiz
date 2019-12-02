package com.example.wordwhiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class LoserPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loser_page);
        Button tryAgain = findViewById(R.id.tryAgain);
        Button home = findViewById(R.id.home);
        tryAgain.setOnClickListener(unused -> goToGame());
        home.setOnClickListener(unused -> goToHome());
    }
    public void goToGame() {
        Intent intent = new Intent(this, GamePage.class);

    }
    public void goToHome() {
        Intent intent = new Intent(this, HomePage.class);
    }
}
