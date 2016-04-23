package com.example.android.legowords;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements WordsView.StatusListener {

    private WordsView mWordsView;
    private int mLevel = 0;
    private String[] mArrayWords;
    private String[] mArrayImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWordsView = (WordsView) findViewById(R.id.word_view);
        mArrayWords = getResources().getStringArray(R.array.words);
        mArrayImages = getResources().getStringArray(R.array.images);
        mWordsView.setStatusListener(this);
        legoWord(mLevel);
    }

    public void legoWord(int level) {
        String word = mArrayWords[level];
        Drawable image = getDrawable(mArrayImages[level]);
        if (image != null && word != null) {
            mWordsView.setWord(word, image);
        } else {
            Toast.makeText(this, "Unable to load word", Toast.LENGTH_LONG).show();
        }
    }

    private Drawable getDrawable(String imageName) {
        Drawable drawable = null;
        try {
            InputStream ims = getAssets().open(imageName);
            drawable = Drawable.createFromStream(ims, null);
        } catch (IOException ignore) {
        }
        return drawable;
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this,"Congrats!",Toast.LENGTH_LONG).show();
        legoWord(++mLevel);
    }

    @Override
    public void onFailure() {

    }

//    View.OnClickListener oclBtnLetter = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            char letter = chars[printWord.length()];
//            Button button = (Button) v;
//            char buttonLetter = button.getText().charAt(0);
//            if (letter == buttonLetter) {
//                mLetters[mcountLetter].setText(button.getText().toString());
//                mcountLetter++;
//                printWord += button.getText().toString();
//                if (printWord.length() == mWord.length()){
//                    if(mLevel != 3) {
//                        mLevel++;
//                        mWordsView.removeView(button);
//                        mLinearLayoutForLetters.removeAllViews();
//                        legoWord();
//                        // mTextView.addView(buttonOk);
//                    }
//                }
//                mWordsView.removeView(button);
//            }
//        }
//    };
}
