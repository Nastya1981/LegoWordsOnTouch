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

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private LinearLayout mLinearLayoutLetters;
    private RelativeLayout mLinearLayoutForLetters;
    private RelativeLayout mMainRelativeLayout;
    private ImageView mImageView;
    private TextView[] mLetters;

    private int mLevel = 0;
    private int mcountLetter = 0;
    private String[] mArrayWords;
    private String[] mArrayImages;

    String printWord = "";
    String mWord;
    int mWordLength;
    char[] chars;
    Button[] buttons;

    TextView txt2;
    private int _xDelta;
    private int _yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLinearLayoutLetters = (LinearLayout) findViewById(R.id.layout_letters);
        mLinearLayoutForLetters = (RelativeLayout) findViewById(R.id.layout_container_for_letters);
        mMainRelativeLayout = (RelativeLayout) findViewById(R.id.mainLinearLayout);
        mImageView = (ImageView)findViewById(R.id.image_word);
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
                .getIdentifier(image, "drawable", this
                        .getPackageName()));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void createLetters(List Letters){
        buttons = new Button[mWordLength];
        float x = 150;
        for (int i = 0; i < mWordLength; i++) {
            buttons[i] = (Button)getLayoutInflater().inflate(R.layout.letter,null);
            buttons[i].setText(String.valueOf(Letters.get(i)));
            buttons[i].setOnTouchListener(this);
            mMainRelativeLayout.addView(buttons[i]);
            buttons[i].setX(x);
            buttons[i].setY(700);
            buttons[i].setId(i);
            x += 80;
        }
    }
    public void createContainerForLetters(){
        mLetters = new TextView[mWordLength];
        float x = 0;
       // RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLinearLayoutForLetters.getLayoutParams();
       /* layoutParams.leftMargin = 0;
        layoutParams.topMargin = 50;
        layoutParams.height = 100;
        layoutParams.width = 100;*/
        for (int i = 0; i < mWordLength; i++) {
            mLetters[i] = new TextView(this);
            mLetters[i].setBackgroundResource(R.drawable.roundrect);
            mLetters[i].setTextSize(55);
            mLetters[i].setWidth(100);
            mLetters[i].setHeight(100);
            mLetters[i].setId(i);
            mLinearLayoutForLetters.addView(mLetters[i]);
         //  mLetters[i].setLayoutParams(layoutParams);
            mLetters[i].setX(x);
            //mLetters[i].setY(500);
            x += 80;
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
   /* @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                initialX = (int) event.getX();
                initialY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getX();
                int currentY = (int) event.getY();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
               // mLinearLayoutLetters.seth  .height += 200;
                //view.setLayoutParams(lp);

                int left = lp.leftMargin + (currentX - initialX);
                int top = lp.topMargin + (currentY - initialY);
                int right = lp.rightMargin - (currentX - initialX);
                int bottom = lp.bottomMargin - (currentY - initialY);

                lp.rightMargin = right;
                lp.leftMargin = left;
                lp.bottomMargin = bottom;
                lp.topMargin = top;

                view.setLayoutParams(lp);
                break;
            default:
                break;
        }
        return true;
    }*/

    View.OnClickListener oclBtnLetter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            char letter = chars[printWord.length()];
            Button button = (Button) v;
            char buttonLetter = button.getText().charAt(0);
            if (letter == buttonLetter) {
                mLetters[mcountLetter].setText(button.getText().toString());
                mcountLetter++;
                printWord += button.getText().toString();
                if (printWord.length() == mWord.length()){
                    if(mLevel != 3) {
                        mLevel++;
                        mLinearLayoutLetters.removeView(button);
                        mLinearLayoutForLetters.removeAllViews();
                        legoWord();
                        // mTextView.addView(buttonOk);
                    }
                }
                mLinearLayoutLetters.removeView(button);
            }
        }
    };

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
                TextView containerLetter = mLetters[printWord.length()];
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
                        Toast.makeText(this,"Catch!",Toast.LENGTH_LONG).show();
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
}
