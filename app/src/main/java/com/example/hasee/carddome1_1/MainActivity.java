package com.example.hasee.carddome1_1;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FrameLayout mCardMianContainer;
    private LinearLayout mCardFrontContainer,mCardBackContainer;
    private AnimatorSet mRightSet,mLeftSet;

    private boolean mIsShowBack = false; //判断是否显示背面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_main_container:
                flipCard();
                break;
        }

    }
    private void initView(){
        mCardMianContainer = (FrameLayout)findViewById(R.id.card_main_container);
        mCardFrontContainer = (LinearLayout)findViewById(R.id.card_front_container);
        mCardBackContainer = (LinearLayout)findViewById(R.id.card_back_container);

        setAnimators();
        setCameraDistance();
    }
    private void initEvent(){
        mCardMianContainer.setOnClickListener(this);
    }

    private void setAnimators(){
        mRightSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.anim_right_out);
        mLeftSet = (AnimatorSet)AnimatorInflater.loadAnimator(this,R.animator.anim_left_in);
        // 设置点击事件
       mRightSet.addListener(new AnimatorListenerAdapter() {
           @Override
           public void onAnimationStart(Animator animation) {
               super.onAnimationStart(animation);
               mCardMianContainer.setClickable(false);
           }
       });
       mLeftSet.addListener(new AnimatorListenerAdapter() {
           @Override
           public void onAnimationStart(Animator animation) {
               super.onAnimationStart(animation);
               mCardMianContainer.setClickable(true);
           }
       });
    }
    private void setCameraDistance(){
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density*distance;
        mCardFrontContainer.setCameraDistance(scale);
        mCardBackContainer.setCameraDistance(scale);
    }
    private void flipCard(){
        if (!mIsShowBack){
            mRightSet.setTarget(mCardFrontContainer);
            mLeftSet.setTarget(mCardBackContainer);
            mRightSet.start();
            mLeftSet.start();
            mIsShowBack=true;
        }else {
            mRightSet.setTarget(mCardBackContainer);
            mLeftSet.setTarget(mCardFrontContainer);
            mRightSet.start();
            mLeftSet.start();
            mIsShowBack = false;
        }
    }
}
