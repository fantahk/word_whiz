package com.example.wordwhiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
        TextView wordOfDay = findViewById(R.id.wordDefinition);
        RequestQueue myRequest = Volley.newRequestQueue(this);
        String url = "https://api.wordnik.com/v4/words.json/randomWord?hasDictionaryDef=true&maxCorpusCount=-1&minDictionaryCount=1&maxDictionaryCount=-1&minLength=7&maxLength=14&api_key=YOURAPIKEY";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        wordOfDay.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wordOfDay.setText("That didn't work!");
            }
        });
        Button start = findViewById(R.id.start);
        start.setOnClickListener(unused -> goToGame());
    }
    public void goToGame() {
        Intent intent = new Intent(this, GamePage.class);
        startActivity(intent);
    }
}
