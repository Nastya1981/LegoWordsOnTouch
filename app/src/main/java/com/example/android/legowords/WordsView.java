package com.example.android.legowords;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

    private LayoutInflater mInflater;

   // private int mPlaceSize;
    String printWord = "";
    String mWord;
    int mWordLength;
    char[] chars;
    TextView[] mLetters;

    private int _xDelta;
    private int _yDelta;

    private Button btnOk;
    private float mY;

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

        btnOk = (Button)mInflater.inflate(R.layout.container_for_letter,null);
        Drawable d = getResources().getDrawable(R.drawable.ok);
        btnOk.setBackgroundDrawable(d);
        btnOk.setOnClickListener(oclBtnOk);
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

    private void setImageView(String image) {
        mImageView.setImageResource(this
                .getResources()
                .getIdentifier(image, "drawable", getContext()
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
            mY = letter.getY();
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
            mContainerForLetters[i] = (TextView)mInflater.inflate(R.layout.container_for_letter,null);
            containerForLetters = mContainerForLetters[i];
            mLinearLayoutForLetters.addView(containerForLetters);
        }
    }

    private void equalsLetters(char TextViewLetter, TextView containerLetter, TextView letter){
        char letterInWord = chars[printWord.length()];
        if (letterInWord == TextViewLetter) {
            containerLetter.setText(letter.getText());
            containerLetter.setBackgroundColor(0xffff00);
            printWord += letter.getText().toString();
            mMainRelativeLayout.removeView(letter);
            if (printWord.length() == mWord.length()) {
                if (mLevel < 2) {
                    mLinearLayoutForLetters.addView(btnOk);
                }
            }
        }
    }
    private StatusListener mStatusListener;
    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            int access = 50;
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
                            char TextViewLetter = letter.getText().charAt(0);
                            equalsLetters(TextViewLetter, containerLetter, letter);
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

    OnClickListener oclBtnOk = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mLevel++;
            legoWord();
        }
    };

    public void setStatusListener(StatusListener statusListener) {
        mStatusListener = statusListener;
    }
    public interface StatusListener {
    }
}
