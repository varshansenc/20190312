package com.example.a20190312;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button preview;
    private Button next;
    private TextView textView;
    private Button mChectButton;

    private static final String TAG="20190312";
    private static final String KEY_INDEX="index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private int mCurrentIndex = 0;
    private boolean mIsChecter;

    private Question[] mQuestionBank=new Question[] {
            new Question(R.string.question_cj,true),
            new Question(R.string.question_hh,false),
            new Question(R.string.question_xmlys,true),
            new Question(R.string.question_Canberra,true)
    };


    public void displayTrue(){
        Toast.makeText(MainActivity.this,R.string.display1,Toast.LENGTH_SHORT).show();
    }
    public void displayFalse(){
        Toast.makeText(MainActivity.this,R.string.display2,Toast.LENGTH_SHORT).show();
    }
    public void displayIsChected(){
        Toast.makeText(MainActivity.this,R.string.judgement_toast ,Toast.LENGTH_SHORT).show();
    }
    public void checkAnswer(boolean selectlAnswer){
        boolean trueAnswer=mQuestionBank[mCurrentIndex].ismAnswerTrue();
        if(mIsChecter){
            displayIsChected();
        }else{
            if(trueAnswer==selectlAnswer){
                displayTrue();
            }else {
                displayFalse();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
        }

        mTrueButton=findViewById(R.id.true_btn);
        mFalseButton=findViewById(R.id.false_btn);
        preview=findViewById(R.id.preview);
        next=findViewById(R.id.next);
        textView=findViewById(R.id.question);
        mChectButton=findViewById(R.id.chect_button);

        int question=mQuestionBank[mCurrentIndex].getmTextResId();
        textView.setText(question);

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex +
                        mQuestionBank.length - 1) % mQuestionBank.length;
                mIsChecter=false;
                int question=mQuestionBank[mCurrentIndex].getmTextResId();
                textView.setText(question);

            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex + 1) % mQuestionBank.length;
                mIsChecter=false;
                int question=mQuestionBank[mCurrentIndex].getmTextResId();
                textView.setText(question);
            }
        });

        mChectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].ismAnswerTrue();
                Intent intent=CheckActivity.newIntent(MainActivity.this,answerIsTrue);
                //startActivity(intent);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != Activity.RESULT_OK){ return; }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){ return; }
            mIsChecter = CheckActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG,"onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestory() called");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart() called");
    }

}
