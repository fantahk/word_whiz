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
    /**
     * word - stores the word for the word of the day
     */
    private String word;
    /**
     * wordDefinition - stores the definition that corresponds with word
     */
    private String wordDefinition;
    /**
     * wordOfDay - a text box that displays the word of the day
     */
    private TextView wordOfDay;
    /**
     * definition - a text box that displays the definition corresponding with the word of the day
     */
    private TextView definition;
    /**
     * start - button that takes the user to GamePage
     */
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


    /**
     * This function makes an API request and requests an array of words. One word is picked and set
     * as the word of the day. Then, definitionAPI(word) is called with that word, fetching the definition
     * corresponding with that word.
     */
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
    }


    /**
     *
     * @param word - the word of the day, passed into this function by wordAPI()
     * @return the definition corresponding to the word of the day
     * This function makes an API request, fetching the definition of the word and setting it to the
     * appropriate text box. Any instances of HTML code from the response is taken out of the string
     * to include readability of the definition.
     */
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

                                if (wordDefinition.contains("<em>")) {
                                    wordDefinition = wordDefinition.replace("<em>", "");
                                    wordDefinition = wordDefinition.replace("</em>", "");
                                }
                                if (wordDefinition.contains("<xref>")) {
                                    wordDefinition = wordDefinition.replace("<xref>", "");
                                    wordDefinition = wordDefinition.replace("</xref>", "");
                                }


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
