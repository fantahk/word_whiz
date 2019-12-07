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
    private String wordDefinition;
    private TextView wordOfDay;
    private TextView definition;
    private Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        wordOfDay = findViewById(R.id.wordDefinition);
        definition = findViewById(R.id.definition);
        start = findViewById(R.id.start);
        start.setOnClickListener(unused -> goToGame());
        wordAPI();
    }




    public void wordAPI() {
        RequestQueue queue1 = Volley.newRequestQueue(this);
        String url1 = "https://api.wordnik.com/v4/words.json/randomWords?hasDictionaryDef=true&maxCorpusCount=-1&minDictionaryCount=1" +
                "&maxDictionaryCount=1&minLength=7&maxLength=14&limit=50" +
                "&api_key=1zm37ehk7ihwitkbl0id0hxydy2s5l9pamrav08k0bji5wjew";
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            word = jsonObject.getString("word");
                            wordOfDay.setText("Word of The Day: " + word);
                            definitionAPI(word);
                        } catch (JSONException e) {
                            wordOfDay.setText(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                wordOfDay.setText(error.getMessage());
            }
        });
        queue1.add(jsonArrayRequest1);
        //return word;
    }





    public String definitionAPI(String word) {
        RequestQueue queue2 = Volley.newRequestQueue(this);
        String url2 = "https://api.wordnik.com/v4/word.json/" + word + "/definitions?limit=3&includeRelated=false&useCanonical=false&includeTags=false&api_key=1zm37ehk7ihwitkbl0id0hxydy2s5l9pamrav08k0bji5wjew";
        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                wordDefinition = jsonObject.getString("text");
                                definition.setText("Definition: " + wordDefinition);
                            } catch (JSONException e) {
                                definition.setText(e.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                definition.setText(error.getMessage());
            }
        });
        queue2.add(jsonArrayRequest2);
        return wordDefinition;
    }




    public void goToGame() {
        Intent intent = new Intent(this, GamePage.class);
        startActivity(intent);
    }
}
