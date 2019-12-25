package com.montassar.guessmoviesscenes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.montassar.guessmoviesscenes.Fragments.PlayingVideoFragment;
import com.montassar.guessmoviesscenes.Models.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private String rightAnswer;
    private ArrayList<HashMap<String,String>> answersList;
    private ArrayList<String> moviesList;
     private Button txtAnswer1,txtAnswer2,txtAnswer3,txtAnswer4,txtAnswer5,txtAnswer6;
     private HashMap<String,String> answerMap;
     private final String KEY_ANSWER_GAME="answer";
     public static int videoTime=20000;
     private int score=0;
     private SharedPreferences mSharedPreferences;
     private SharedPreferences.Editor editor;
     public  LinearLayout linearLayoutMain;
     private Movie movie;
    private StorageReference mStorageRef;
    private Dialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //===========/Hide Status Bar/===========
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //=======================================
        setContentView(R.layout.activity_main);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        moviesList = new ArrayList<>();
        answersList = new ArrayList<>();
        Collections.addAll(moviesList, getResources().getStringArray(R.array.movies));

        mSharedPreferences =getSharedPreferences("MyPref", Activity.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        score=mSharedPreferences.getInt("score",0);

        linearLayoutMain = findViewById(R.id.linear_layout_main);
        linearLayoutMain.setVisibility(View.GONE);



        txtAnswer1 = (Button) findViewById(R.id.txt_answer_1);
        txtAnswer2 = (Button) findViewById(R.id.txt_answer_2);
        txtAnswer3 = (Button) findViewById(R.id.txt_answer_3);
        txtAnswer4 = (Button) findViewById(R.id.txt_answer_4);
        txtAnswer5 = (Button) findViewById(R.id.txt_answer_5);
        txtAnswer6 = (Button) findViewById(R.id.txt_answer_6);
        txtAnswer1.setOnClickListener(this);
        txtAnswer2.setOnClickListener(this);
        txtAnswer3.setOnClickListener(this);
        txtAnswer4.setOnClickListener(this);
        txtAnswer5.setOnClickListener(this);
        txtAnswer6.setOnClickListener(this);


        //generateAnswers(rightAnswer);
        DBHelper database = new DBHelper(getApplicationContext());
        movie =database.getAnswer();
        generateAnswers(movie);
        rightAnswer = movie.getRightAnswer();
        mStorageRef.child("videos/"+movie.getVideoName()+".mp4").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(MainActivity.this, ""+uri, Toast.LENGTH_SHORT).show();
                        init(uri);
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


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ",e);
            }
        });

    }

    private void generateAnswers(Movie movie)
    {
        answerMap = new HashMap<>();
        answerMap.put(KEY_ANSWER_GAME,movie.getRightAnswer());
        answerMap.put("is_real","1");
        answersList.add(answerMap);
        answerMap = new HashMap<>();
        answerMap.put(KEY_ANSWER_GAME,movie.getWrong1());
        answerMap.put("is_real","0");
        answersList.add(answerMap);answerMap = new HashMap<>();
        answerMap.put(KEY_ANSWER_GAME,movie.getWrong2());
        answerMap.put("is_real","0");
        answersList.add(answerMap);answerMap = new HashMap<>();
        answerMap.put(KEY_ANSWER_GAME,movie.getWrong3());
        answerMap.put("is_real","0");
        answersList.add(answerMap);answerMap = new HashMap<>();
        answerMap.put(KEY_ANSWER_GAME,movie.getWrong4());
        answerMap.put("is_real","0");
        answersList.add(answerMap);answerMap = new HashMap<>();
        answerMap.put(KEY_ANSWER_GAME,movie.getWrong5());
        answerMap.put("is_real","0");
        answersList.add(answerMap);
        Collections.shuffle(answersList);
        txtAnswer1.setText(answersList.get(0).get(KEY_ANSWER_GAME));
        txtAnswer2.setText(answersList.get(1).get(KEY_ANSWER_GAME));
        txtAnswer3.setText(answersList.get(2).get(KEY_ANSWER_GAME));
        txtAnswer4.setText(answersList.get(3).get(KEY_ANSWER_GAME));
        txtAnswer5.setText(answersList.get(4).get(KEY_ANSWER_GAME));
        txtAnswer6.setText(answersList.get(5).get(KEY_ANSWER_GAME));
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

    private void init(Uri videoUri) {

        PlayingVideoFragment fragment = new PlayingVideoFragment(videoUri);
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

        showNextPopup();

    }
    private void showNextPopup()
    {
        mDialog = new Dialog(MainActivity.this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.popup_next_question);
        mDialog.setCanceledOnTouchOutside(false);
        ImageButton iBtnNext = mDialog.findViewById(R.id.iBtn_next_question);
        ImageButton iBtnYoutube = mDialog.findViewById(R.id.iBtn_play_youtube);
        TextView txtCurrentScore = mDialog.findViewById(R.id.txt_current_score);
        txtCurrentScore.setText(Integer.toString(mSharedPreferences.getInt("score",0)));
        iBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "next", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        iBtnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Youtube id "+movie.getVideoURL(), Toast.LENGTH_LONG).show();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movie.getVideoURL()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + movie.getVideoURL()));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });

        mDialog.show();
    }
}
