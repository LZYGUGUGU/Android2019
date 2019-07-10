package com.example.tic_tok_toplist;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button regin=(Button)findViewById(R.id.Buttonin);
        final EditText name_in=(EditText)findViewById(R.id.Name);
        final EditText password_in=(EditText)findViewById(R.id.password);
        regin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                name=name_in.getText().toString();
                Intent intent = new Intent(MainActivity.this, TopListActivity.class);
                intent.putExtra("name",name);
            startActivity(intent);
            }
        });
        AnimationSet setanimation = new AnimationSet(true);
        setanimation.setRepeatMode(Animation.RESTART);
        setanimation.setRepeatCount(Animation.INFINITE);//设置为无限循环
        //旋转动画设置
        Animation rotate=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);
        //平移动画
        Animation translate =new TranslateAnimation(Animation.RELATIVE_TO_PARENT,-0.5f,Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
        translate.setDuration(1000);
        //透明度动画
        Animation alpha =new AlphaAnimation(1,0);
        alpha.setDuration(1000);
        alpha.setRepeatMode(Animation.RESTART);
        alpha.setRepeatCount(Animation.INFINITE);
        //缩放动画
        Animation scale = new ScaleAnimation(1,0.5f,1,0.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);
        scale.setRepeatMode(Animation.RESTART);
        scale.setRepeatCount(Animation.INFINITE);

        setanimation.addAnimation(alpha);
        setanimation.addAnimation(rotate);
        setanimation.addAnimation(scale);
        setanimation.addAnimation(translate);

        ImageView icon=findViewById(R.id.icon1);
        icon.startAnimation(setanimation);

        final LottieAnimationView ib=findViewById(R.id.loani);
        ib.playAnimation();
        SeekBar seekBar=findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float a=i;
                ib.setProgress(a/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });{

        }

//        PropertyValuesHolder propertyValuesHolder =  PropertyValuesHolder.ofFloat("scaleX",2);
//        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("scaleY",2);
//        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(propertyValuesHolder,propertyValuesHolder1);
//        animator.setTarget();
//        animator.start();
    }
}
