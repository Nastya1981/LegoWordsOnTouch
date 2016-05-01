package com.example.android.legowords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements WordsView.StatusListener{

    private WordsView mWordsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWordsView = (WordsView) findViewById(R.id.word_view);
        mWordsView.setStatusListener(this);
        mWordsView.legoWord();
    }
}
