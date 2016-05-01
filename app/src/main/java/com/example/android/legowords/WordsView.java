package com.example.android.legowords;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordsView extends RelativeLayout {
    private LinearLayout mLinearLayoutLetters;
    private LinearLayout mLinearLayoutForLetters;
    private RelativeLayout mMainRelativeLayout;
    private ImageView mImageView;
    private TextView[] mContainerForLetters;

    private int mLevel = 0;
    private String[] mArrayWords;
    private String[] mArrayImages;

    private int mcountLetter = 0;
    private LayoutInflater mInflater;

    private int mPlaceSize;
    String printWord = "";
    String mWord;
    int mWordLength;
    char[] chars;
    TextView[] mLetters;

    TextView txt2;
    private int _xDelta;
    private int _yDelta;

    public WordsView(Context context) {
        super(context);
        init();
    }

    public WordsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WordsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.word_view, this, true);

        mLinearLayoutForLetters = (LinearLayout) findViewById(R.id.layout_container_for_letters);
        mMainRelativeLayout = (RelativeLayout) findViewById(R.id.mainLinearLayout);
        mImageView = (ImageView)findViewById(R.id.image_word);

        mArrayWords = getResources().getStringArray(R.array.words);
        mArrayImages = getResources().getStringArray(R.array.images);
            /*???*/
        mcountLetter = 0;
    }

    public void legoWord(){
        if(mLevel > 0) {
            mLinearLayoutForLetters.removeAllViewsInLayout();
        }
        printWord= "";
        mWord = mArrayWords[mLevel];
        mWordLength = mWord.length();
        chars = mWord.toCharArray();
        setImageView(mArrayImages[mLevel]);
        List <Character> shuffleLetters = shuffleLetters();
        createLetters(shuffleLetters);
        createContainerForLetters();
    }

    private void setImageView(String image){
        mImageView.setImageResource(this
                .getResources()
                .getIdentifier(mArrayImages[mLevel], "drawable", getContext()
                        .getPackageName()));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void createLetters(List Letters){
        mLetters = new TextView[mWordLength];
        TextView letter;
        int x = startPositionLetter(mLetters.length);
        for (int i = 0; i < mLetters.length; i++) {
            mLetters[i] = (TextView)mInflater.inflate(R.layout.letter,null);
            letter = mLetters[i];
            letter.setText(String.valueOf(Letters.get(i)));
            letter.setOnTouchListener(mOnTouchListener);
            mMainRelativeLayout.addView(letter);
            letter.setX(x);
            letter.setY(750);
            x += 110;
        }
    }
    private int startPositionLetter(int lengthWord){
        int x = 600 - (100 * lengthWord);
        x = (x - (10 * lengthWord)) / 2;
        return x;
    }
    public List shuffleLetters(){
        List<Character> Letters = new ArrayList<>();
        char[] chars = mWord.toCharArray();
        for (int i = 0; i < mWord.length(); i++) {
            Letters.add(chars[i]);
        }
        if(mLevel > 0) {
            Collections.shuffle(Letters);
        }
        return Letters;
    }
    public void createContainerForLetters(){
        mContainerForLetters = new TextView[mWordLength];
        TextView containerForLetters;
        for (int i = 0; i < mContainerForLetters.length; i++) {
            mContainerForLetters[i] = (TextView)mInflater.inflate(R.layout.letter,null);
            containerForLetters = mContainerForLetters[i];
            //containerForLetters.setBackgroundResource(R.drawable.roundrect);
            // mLinearLayoutForLetters.addView(containerForLetters);
            /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mPlaceSize,mPlaceSize);
            params.leftMargin = 10;
            params.rightMargin = 10;*/
            mLinearLayoutForLetters.addView(containerForLetters);
        }
    }
    private void equalsLetters(char letterInWord, char TextViewLetter, TextView containerLetter, TextView letter){
        if (letterInWord == TextViewLetter) {
            containerLetter.setText(letter.getText());
            mcountLetter++;
            printWord += letter.getText().toString();
            mMainRelativeLayout.removeView(letter);
            if (printWord.length() == mWord.length()) {
                if (mLevel != 2) {
                    mLevel++;
                    legoWord();
                }
            }
        }
    }
    private StatusListener mStatusListener;
    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            int access = 25;
            TextView letter = (TextView) view;
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    TextView containerLetter = mContainerForLetters[printWord.length()];
                    if ((view.getY() < mLinearLayoutForLetters.getY() && view.getY() + access >= mLinearLayoutForLetters.getY()) || (view.getY() > mLinearLayoutForLetters.getY() && view.getY() - access <= mLinearLayoutForLetters.getY())) {
                       if ((view.getX() < containerLetter.getX() && view.getX() + access >= containerLetter.getX()) || (view.getX() > containerLetter.getX() && view.getX() - access <= containerLetter.getX())) {
                            char letterInWord = chars[printWord.length()];
                            char TextViewLetter = letter.getText().charAt(0);
                            equalsLetters(letterInWord, TextViewLetter, containerLetter, letter);
                            //   Toast.makeText(this, "Catch!", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
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
            mMainRelativeLayout.invalidate();
            return true;
        }
    };

    public void setStatusListener(StatusListener statusListener) {
        mStatusListener = statusListener;
    }
    public interface StatusListener {
    }
}
