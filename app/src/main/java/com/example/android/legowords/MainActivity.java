package com.example.android.legowords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements WordsView.StatusListener{
    private int mLevel = 0;
    private String[] mArrayWords;
    private String[] mArrayImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mArrayWords = getResources().getStringArray(R.array.words);
        mArrayImages = getResources().getStringArray(R.array.images);
        legoWord();
    }
    public void legoWord(){
        init();
        setImageView(mArrayImages[mLevel]);
        List <Character> shuffleLetters = shuffleLetters();
        createLetters(shuffleLetters);
        createContainerForLetters();
    }



}
