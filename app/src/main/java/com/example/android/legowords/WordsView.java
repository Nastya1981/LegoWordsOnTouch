package com.example.android.legowords;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordsView extends RelativeLayout {

    private LinearLayout mLinearLayoutForLetters;
    private TextView[] mLetters;
    private TextView[] mPlacesForLetters;

    private ImageView mImageView;

    private String mShuffledString;
    private String mOriginalString;

    private Drawable mImage;

    private int _xDelta;
    private int _yDelta;
    private LayoutInflater mInflater;
    private int mLetterSize;
    private StatusListener mStatusListener;
    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    positionCheck(view);
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = 0;
                    layoutParams.bottomMargin = 0;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            WordsView.this.invalidate();
            return true;
        }
    };
    private int mPlaceSize;

    public WordsView(Context context) {
        super(context);
        inflate();
        initMeasures();
    }

    public WordsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate();
        initMeasures();
    }

    public WordsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
        initMeasures();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WordsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate();
        initMeasures();
    }

    private void inflate() {
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.word_view, this, true);
        mImageView = (ImageView) findViewById(R.id.image_word);
        mLinearLayoutForLetters = (LinearLayout) findViewById(R.id.layout_container_for_letters);
    }

    public void setWord(String word, Drawable image) {
        mOriginalString = word;
        mShuffledString = shuffle(word);
        mImage = image;
        invalidateWord();
    }

    private void invalidateWord() {
        this.removeAllViews();
        inflate();
        mImageView.setImageDrawable(mImage);
        applyLetters();
        applyPlacesForLetters();
    }

    public void applyLetters() {
        mLetters = new TextView[mShuffledString.length()];
        float x = 0;
        for (int i = 0; i < mLetters.length; i++) {
            TextView letter = (TextView) mInflater.inflate(R.layout.letter, null);
            mLetters[i] = letter;
            String ch = String.valueOf(mShuffledString.charAt(i));
            letter.setText(ch);
            letter.setTag(ch);
            letter.setOnTouchListener(mOnTouchListener);
            x += letter.getWidth();
            letter.setX(x);
            letter.setY(this.getHeight() - letter.getHeight());
            this.addView(letter);
        }
    }

    public void applyPlacesForLetters() {
        mPlacesForLetters = new TextView[mShuffledString.length()];
        for (int i = 0; i < mPlacesForLetters.length; i++) {
            TextView place = (TextView) mInflater.inflate(R.layout.place_for_letter, null);
            mPlacesForLetters[i] = place;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mPlaceSize,mPlaceSize);
            params.leftMargin = 10;
            params.rightMargin = 10;
            mLinearLayoutForLetters.addView(place,params);
        }
    }

    public String shuffle(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    private void initMeasures() {
        mLetterSize = getResources().getDimensionPixelSize(R.dimen.letter_size);
        mPlaceSize = getResources().getDimensionPixelSize(R.dimen.place_size);
    }

    private void positionCheck(View view) {
        String ch = (String) view.getTag();
        int index = mOriginalString.indexOf(ch);
        TextView firstEmptyPlaceForLetter = mPlacesForLetters[index];


        if ((view.getY() < firstEmptyPlaceForLetter.getY() && view.getY() + 25 >= firstEmptyPlaceForLetter.getY()) || (view.getY() > firstEmptyPlaceForLetter.getY() && view.getY() - 25 <= firstEmptyPlaceForLetter.getY())) {
            if ((view.getX() < firstEmptyPlaceForLetter.getX() && view.getX() + 25 >= firstEmptyPlaceForLetter.getX()) || (view.getX() > firstEmptyPlaceForLetter.getX() && view.getX() - 25 <= firstEmptyPlaceForLetter.getX())) {

//                char letter = chars[printWord.length()];
//                char buttonLetter = ((Button) view).getText().charAt(0);
//                if (letter == buttonLetter) {
//                    firstEmptyPlaceForLetter.setText(((Button) view).getText());
//                    mcountLetter++;
//                    printWord += ((Button) view).getText().toString();
//                    mMainRelativeLayout.removeView(view);
//                    if (printWord.length() == mWord.length()) {
//
//                    }
//                }
            }
        }
    }

    private void setLetter() {

        if (mStatusListener != null) {
            mStatusListener.onSuccess();
        }
    }

    public void setStatusListener(StatusListener statusListener) {
        mStatusListener = statusListener;
    }

    public interface StatusListener {
        void onSuccess();

        void onFailure();
    }
}
