package com.example.android.legowords;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

public class WordsView extends RelativeLayout {
    private LinearLayout mLinearLayoutLetters;
    private LinearLayout mLinearLayoutForLetters;
    private RelativeLayout mMainRelativeLayout;
    private ImageView mImageView;
    private TextView[] mContainerForLetters;
    private int mcountLetter = 0;
    private LayoutInflater mInflater;


    String printWord = "";
    String mWord;
    int mWordLength;
    char[] chars;
    Button[] mLetters;

    TextView txt2;
    private int _xDelta;
    private int _yDelta;


    public WordsView(Context context) {
        super(context);
    }

    public WordsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WordsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mInflater.inflate(R.layout.word_view, this, true);
    mLinearLayoutLetters = (LinearLayout) findViewById(R.id.layout_letters);
    mLinearLayoutForLetters = (LinearLayout) findViewById(R.id.layout_container_for_letters);
    mMainRelativeLayout = (RelativeLayout) findViewById(R.id.mainLinearLayout);
    mImageView = (ImageView)findViewById(R.id.image_word);
    private StatusListener mStatusListener;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        String str = "";
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
//                _view.setX(event.getX());
//                _view.setY(event.getY());
//
                TextView containerLetter = mContainerForLetters[printWord.length()];
                if((view.getY()<containerLetter.getY()&&view.getY()+25>=containerLetter.getY())||(view.getY()>containerLetter.getY()&&view.getY()-25<=containerLetter.getY())){
                    if((view.getX()<containerLetter.getX()&&view.getX()+25>=containerLetter.getX())||(view.getX()>containerLetter.getX()&&view.getX()-25<=containerLetter.getX())){

                        char letter = chars[printWord.length()];
                        char buttonLetter = ((Button) view).getText().charAt(0);
                        if (letter == buttonLetter) {
                            containerLetter.setText(((Button) view).getText());
                            mcountLetter++;
                            printWord += ((Button) view).getText().toString();
                            mMainRelativeLayout.removeView(view);
                            if (printWord.length() == mWord.length()) {
                                if(mLevel != 2) {
                                    mLevel++;
                                    mLinearLayoutForLetters.removeAllViews();
                                    legoWord();
                                }
                            }
                        }
                        Toast.makeText(this, "Catch!", Toast.LENGTH_LONG).show();
                    }
                }
                // if(mLetters[0].getY()<view.getY()-10){
                // if(mLetters[0].getX()<view.getX()-100||mLetters[0].getX()>view.getX()+100){
                //  Toast.makeText(this, "Catch!", Toast.LENGTH_LONG).show();
                // }
                //((Button) view).setText(String.valueOf(view.getY()) + "");
                // mLetters[0].setText(String.valueOf(mLetters[0].getY())+" ");
                //Button button = (Button) view;
                //  ((Button) view).setText(String.valueOf(view.getY()) + "");
                //  mLetters[0].setText(String.valueOf(mLetters[0].getY()) + "");
                //((Button) view).setText(String.valueOf(view.getY()) + " " + String.valueOf(view.getX()) + " \n" + String.valueOf(view.getHeight()));

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams  = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                break;
        }
        mLinearLayoutLetters.invalidate();
//        _view2.setText(str);
        return true;
    }
    public void init(){
        mWord = mArrayWords[mLevel];
        mWordLength = mWord.length();
        chars = mWord.toCharArray();
        mcountLetter = 0;
        printWord= "";
    }
    public void setImageView(String image){
        mImageView.setImageResource(this
                .getResources()
                .getIdentifier(image, "drawable", getContext()
                        .getPackageName()));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void createLetters(List Letters){
        mLetters = new Button[mWordLength];
        Button letters;
        float x = 150;
        for (int i = 0; i < mLetters.length; i++) {
            mLetters[i] = (Button)getLayoutInflater().inflate(R.layout.letter,null);
            letters = mLetters[i];
            letters.setText(String.valueOf(Letters.get(i)));
            letters.setOnTouchListener(this);
            mMainRelativeLayout.addView(letters);
            letters.setX(x);
            letters.setY(700);
            letters.setId(i);
            x += 80;
        }
    }
    public void createContainerForLetters(){
        mContainerForLetters = new TextView[mWordLength];
        TextView containerForLetters;
        for (int i = 0; i < mContainerForLetters.length; i++) {
            mContainerForLetters[i] = (TextView)getLayoutInflater().inflate(R.layout.container_for_letter,null);
            containerForLetters = mContainerForLetters[i];
            containerForLetters.setBackgroundResource(R.drawable.roundrect);
            mLinearLayoutForLetters.addView(containerForLetters);
        }
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
    public interface StatusListener {
    }
}
