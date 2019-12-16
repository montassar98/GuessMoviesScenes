package com.montassar.guessmoviesscenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.montassar.guessmoviesscenes.Fragments.PlayingVideoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String rightAnswer;
    private ArrayList<HashMap<String,String>> answersList;
    private ArrayList<String> moviesList;
     private TextView txtAnswer1,txtAnswer2,txtAnswer3,txtAnswer4,txtAnswer5,txtAnswer6;
     private HashMap<String,String> answerMap;
     private final String KEY_ANSWER_GAME="answer";
     public static int videoTime=10000;
     private int score=0;
     private SharedPreferences mSharedPreferences;
     private SharedPreferences.Editor editor;
     public static LinearLayout linearLayoutMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //===========/Hide Status Bar/===========
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //=======================================
        setContentView(R.layout.activity_main);
        moviesList = new ArrayList<>();
        answersList = new ArrayList<>();
        Collections.addAll(moviesList, getResources().getStringArray(R.array.movies));

        mSharedPreferences =getSharedPreferences("MyPref", Activity.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        score=mSharedPreferences.getInt("score",0);

        linearLayoutMain = findViewById(R.id.linear_layout_main);
        linearLayoutMain.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                linearLayoutMain.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "handler\n"+
                                        mSharedPreferences.getInt("score",0), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },videoTime);


        txtAnswer1 = (TextView) findViewById(R.id.txt_answer_1);
        txtAnswer2 = (TextView) findViewById(R.id.txt_answer_2);
        txtAnswer3 = (TextView) findViewById(R.id.txt_answer_3);
        txtAnswer4 = (TextView) findViewById(R.id.txt_answer_4);
        txtAnswer5 = (TextView) findViewById(R.id.txt_answer_5);
        txtAnswer6 = (TextView) findViewById(R.id.txt_answer_6);
        txtAnswer1.setOnClickListener(this);
        txtAnswer2.setOnClickListener(this);
        txtAnswer3.setOnClickListener(this);
        txtAnswer4.setOnClickListener(this);
        txtAnswer5.setOnClickListener(this);
        txtAnswer6.setOnClickListener(this);
        rightAnswer = "Avengers: End Game";

        generateAnswers(rightAnswer);
        init(R.raw.test2);

    }

    private void generateAnswers(String rightAnswer) {
        answerMap = new HashMap<>();
        answerMap.put(KEY_ANSWER_GAME,rightAnswer);
        answerMap.put("is_real","1");
        answersList.add(answerMap);
        for (int i=0;i<5;i++)
        {
            answerMap = new HashMap<>();
            answerMap.put(KEY_ANSWER_GAME,moviesList.get( new Random().nextInt((15) + 1)));
            answerMap.put("is_real","0");
            answersList.add(answerMap);
        }
        Collections.shuffle(answersList);
        txtAnswer1.setText(answersList.get(0).get(KEY_ANSWER_GAME));
        txtAnswer2.setText(answersList.get(1).get(KEY_ANSWER_GAME));
        txtAnswer3.setText(answersList.get(2).get(KEY_ANSWER_GAME));
        txtAnswer4.setText(answersList.get(3).get(KEY_ANSWER_GAME));
        txtAnswer5.setText(answersList.get(4).get(KEY_ANSWER_GAME));
        txtAnswer6.setText(answersList.get(5).get(KEY_ANSWER_GAME));
    }

    private void init(int videoId) {

        PlayingVideoFragment fragment = new PlayingVideoFragment(videoId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String answerClicked="";
        switch (id)
        {
            case R.id.txt_answer_1:
                answerClicked=txtAnswer1.getText().toString();
                verifyAnswer(answerClicked);
                break;
            case R.id.txt_answer_2:
                    answerClicked=txtAnswer2.getText().toString();
                    verifyAnswer(answerClicked);
                break;
            case R.id.txt_answer_3:
                    answerClicked=txtAnswer3.getText().toString();
                    verifyAnswer(answerClicked);
               break;
            case R.id.txt_answer_4:
                 answerClicked=txtAnswer4.getText().toString();
                 verifyAnswer(answerClicked);
                break;
            case R.id.txt_answer_5:
                    answerClicked=txtAnswer5.getText().toString();
                     verifyAnswer(answerClicked);
               break;
            case   R.id.txt_answer_6:
                   answerClicked=txtAnswer6.getText().toString();
                   verifyAnswer(answerClicked);
                break;
               /*
                break;*/
        }
    }

    private void verifyAnswer(String answerClicked) {
        if (answerClicked.equals(rightAnswer))
        {
            editor.putInt("score",mSharedPreferences.getInt("score",0)+100);
            editor.commit();
            Toast.makeText(this, "Right Answer \n"+score, Toast.LENGTH_LONG).show();


        }
        else Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
}
