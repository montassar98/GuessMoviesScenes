package com.montassar.guessmoviesscenes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.montassar.guessmoviesscenes.Models.Movie;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteAssetHelper{

    private static final String TAG="DBHelper";
    private static final String DB_NAME="movies.db";
    private static final int DB_VERSION=1;
    //--------/Tables names/---------
    private static final String TAB_MOVIES="table_movies";
    private static final String TAB_MOVIES_PLAYED="movies_played";

    //-------------------------------

    //++++tab movies columns+++++//
    private static final String MOVIES_ID_COL="id";
    private static final String MOVIES_RIGHT_ANSWER_COL="right_answer";
    private static final String MOVIES_WRONG_1_COL="wrong1";
    private static final String MOVIES_WRONG_2_COL="wrong2";
    private static final String MOVIES_WRONG_3_COL="wrong3";
    private static final String MOVIES_WRONG_4_COL="wrong4";
    private static final String MOVIES_WRONG_5_COL="wrong5";
    private static final String MOVIES_VIDEO_NAME_COL="video_name";
    private static final String MOVIES_VIDEO_URL_COL="video_url";
    //++++++++++++++++++++++++++//

    private Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    public Movie getAnswer()
    {
        Movie movie = new Movie();
        SQLiteDatabase db =getWritableDatabase();
        String query="SELECT * FROM "+TAB_MOVIES+" WHERE  "+MOVIES_ID_COL+" NOT IN (SELECT "+MOVIES_ID_COL+" FROM "+TAB_MOVIES_PLAYED+") ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(query,null);
        try{
            if (cursor.moveToFirst())
            {

                    movie.setId(cursor.getString(0));
                    movie.setRightAnswer(cursor.getString(1));
                    movie.setWrong1(cursor.getString(2));
                    movie.setWrong2(cursor.getString(3));
                    movie.setWrong3(cursor.getString(4));
                    movie.setWrong4(cursor.getString(5));
                    movie.setWrong5(cursor.getString(6));
                    movie.setVideoName(cursor.getString(7));
                    movie.setVideoURL(cursor.getString(8));
                    String queryInsert="INSERT INTO "+TAB_MOVIES_PLAYED+" VALUES ("+
                            movie.getId()+","+
                            movie.getRightAnswer()+","+
                            movie.getWrong1()+","+
                            movie.getWrong2()+","+
                            movie.getWrong3()+","+
                            movie.getWrong4()+","+
                            movie.getWrong5()+","+
                            movie.getVideoName()+","+
                            movie.getVideoURL()+")";
                    //db.execSQL(queryInsert);
                ContentValues values = new ContentValues();
                values.put(MOVIES_ID_COL,movie.getId());
                values.put(MOVIES_RIGHT_ANSWER_COL,movie.getRightAnswer());
                values.put(MOVIES_WRONG_1_COL,movie.getWrong1());
                values.put(MOVIES_WRONG_2_COL,movie.getWrong2());
                values.put(MOVIES_WRONG_3_COL,movie.getWrong3());
                values.put(MOVIES_WRONG_4_COL,movie.getWrong4());
                values.put(MOVIES_WRONG_5_COL,movie.getWrong5());
                values.put(MOVIES_VIDEO_NAME_COL,movie.getVideoName());
                values.put(MOVIES_VIDEO_URL_COL,movie.getVideoURL());
                    db.insert(TAB_MOVIES_PLAYED,null,values);

            }

        }catch (SQLiteAssetException e)
        { e.printStackTrace();}
        db.close();
        cursor.close();
        Log.e(TAG, "getAnswer: "+movie.getRightAnswer()+"/"+movie.getVideoName());
        return movie ;
    }
}
