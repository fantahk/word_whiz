package com.example.wordwhiz;

// import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
// import android.widget.RelativeLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GamePage extends AppCompatActivity {

    /**
     *
     */
    private RelativeLayout gameLayout;
    /**
     * random - going to be used throughout for words and definitions.
     */
    private Random random;

    /**
     * currentScore- TextView that displays score
     */
    public static TextView currentScore;

    /**
     * score- keeps track of score
     */
    public static int score;

    /**
     * answers - array of all four buttons, will randomly assign correct one.
     */
    private Button[] answers;

    /**
     * definitions - array of definitions
     */
    private ArrayList<String> definitions;
    /**
     * defWords - word variable for definition function
     */
    private String defWord;
    /**
     * correctAnswer - the right answer. randomly chosen.
     */
    private Button correctAnswer;
    /**
     * word - string for random word using API.
     */
    private String word;

    private ArrayList<String> randomWords;
    /**
     * definition - display of definition
     */
    private TextView definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);
        // actual code starting now

        // variable initialization
        gameLayout = findViewById(R.id.gameLayout);
        random = new Random();
        currentScore = gameLayout.findViewById(R.id.currentScore);
        score = 0;
        answers = new Button[]{findViewById(R.id.answer0),
                findViewById(R.id.answer1),
                findViewById(R.id.answer2),
                findViewById(R.id.answer3)};
        correctAnswer = answers[random.nextInt(4)];
        definition = findViewById(R.id.definition);
        randomWords = new ArrayList<String>();
        definitions = new ArrayList<String>();

        // not sure if 59-63 is right,, trying to figure out how to initialize game page for the first round
        currentScore.setText("Score: 0");
        randomWordAPI();
//        newRound();
        // when right answer is chosen, call correctAnswerClicked()
        correctAnswer.setOnClickListener(unused -> correctAnswerClicked());
        for (Button answer : answers) {
            if (answer != correctAnswer) {
                answer.setOnClickListener(unused -> wrongAnswerClicked());
            }
        }
        //randomWordAPI();
    }

    public void newRound() {
        // random word variable = API request for random word
        //String correctWord = ;
        correctAnswer.setText(randomWords.remove(0)); // sets text of correct button to random word variable
        //setDefinition();
    }

    public void correctAnswerClicked() {
        // change button color to green later


        // update score
        score++;
        currentScore.setText("Score: " + score);
        // make new button the correct one
        correctAnswer.setOnClickListener(unused -> wrongAnswerClicked());
        correctAnswer = answers[random.nextInt(4)];
        correctAnswer.setOnClickListener(unused -> correctAnswerClicked());
        // go through answers array, only set text of answers that are wrong, set text to random word (moving to new round by setting new words)
        //randomWordAPI();
        randomWordAPI();
        //System.out.println(randomWords.size());
        // for (Button answer : answers) {
        //   if (answer != correctAnswer) {
        //     answer.setText(randomWords.remove(0));
        //}
        //}
        //newRound();
    }

    public void wrongAnswerClicked() {
        for (Button answer : answers) {
            if (answer != correctAnswer) {
                Intent intent = new Intent(this, LoserPage.class);
                startActivity(intent);
            }
        }
    }

    public void setWrongWord() {
        for (Button answer : answers) {
            if (answer != correctAnswer) {
                answer.setText(randomWords.remove(0));
            }
        }
    }

    public void setDefinition() {
        definition.setText(definitions.remove(0));
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
                                JSONObject jsonObject1 = response.getJSONObject(0);
                                defWord = jsonObject1.getString("text");
                                definitions.add(0, defWord);
                                setDefinition();
                                System.out.println("test array: " + definitions);
                                System.out.println("test size: " + definitions.size());
                            } catch (Exception e){
                                System.out.println("rip");
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("rip error");
            }
        });
        queue2.add(jsonArrayRequest2);
        return defWord;
    }

    public ArrayList<String> randomWordAPI() {
        // ArrayList<String> newRandomWords = new ArrayList<String>();
        // String randomWord = new String();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.wordnik.com/v4/words.json/randomWords?hasDictionaryDef=true&maxCorpusCount=-1&minDictionaryCount=1" +
                "&maxDictionaryCount=1&minLength=7&maxLength=14&limit=36" +
                "&api_key=1zm37ehk7ihwitkbl0id0hxydy2s5l9pamrav08k0bji5wjew";
        //JSONObject randomWord = Request.Method.GET;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            //JSONObject object = new JSONObject();
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                word = jsonObject.getString("word");
                                randomWords.add(0, word);
                            } catch (JSONException e) {
                                System.out.println("rip");
                            }
                            System.out.println("words array size: " + randomWords.size());

                        }
                        setWrongWord();
                        definitionAPI(randomWords.get(0));
                        newRound();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
        return randomWords;
    }
}
