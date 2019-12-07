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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private String word;
    private ArrayList<String> randomWords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        TextView wordOfDay = findViewById(R.id.wordDefinition);
        //randomWordAPI();
        //wordOfDay.setText(randomWords.remove(0));
        Button start = findViewById(R.id.start);
        start.setOnClickListener(unused -> goToGame());

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.wordnik.com/v4/words.json/randomWord?hasDictionaryDef=true&maxCorpusCount=-1&minDictionaryCount=1&maxDictionaryCount=-1&minLength=5&maxLength=-1&api_key=1zm37ehk7ihwitkbl0id0hxydy2s5l9pamrav08k0bji5wjew";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            word = jsonObject.getString("word");
                            wordOfDay.setText(word);
                        } catch (JSONException e) {
                            wordOfDay.setText(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
        queue.add(jsonArrayRequest);


    }
    public void goToGame() {
        Intent intent = new Intent(this, GamePage.class);
        startActivity(intent);
    }
    public ArrayList<String> randomWordAPI() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.wordnik.com/v4/words.json/randomWords?hasDictionaryDef=true&maxCorpusCount=-1&minDictionaryCount=1" +
                "&maxDictionaryCount=1&minLength=7&maxLength=14&limit=50" +
                "&api_key=1zm37ehk7ihwitkbl0id0hxydy2s5l9pamrav08k0bji5wjew";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                System.out.println(response.length());
                                JSONObject jsonObject = response.getJSONObject(i);
                                word = jsonObject.getString("word");
                                randomWords.add(i, word);
                            } catch (JSONException e) {
                                System.out.println("rip");
                            }
                            System.out.println(randomWords);
                            System.out.println(randomWords.size());
                        }
                        System.out.println("size: " + randomWords.size());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
        queue.add(jsonArrayRequest);
        return randomWords;
    }
}
