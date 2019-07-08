package com.example.android_helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView t1=(TextView)findViewById(R.id.textView2);;
        final ImageView I1=(ImageView)findViewById(R.id.imageView);
        final TextView t3=(TextView)findViewById(R.id.textView6);;
        CheckBox box1 = (CheckBox) findViewById(R.id.checkBox2);
        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) t3.setText("版本 v0.1");
                else t3.setText("版本 v***");
            }
        });
        Switch open = (Switch) findViewById(R.id.switch1);
        open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) t1.setBackgroundColor(R.drawable.set__);
                 else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        t1.setBackground(getResources().getDrawable(R.drawable.settextbar));
                    }
                }
            }
        });
        Button turn_Eng;
        turn_Eng = (Button) findViewById(R.id.button_Eng);
        turn_Eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setText("  Hello World!! ");
                // getWindow().setBackgroundDrawableResource(R.drawable.americabg);
               I1.setImageResource(R.drawable.americabg);
            }
        });
        Button turn_Chn;
        turn_Chn = (Button) findViewById(R.id.button_Chn);
        turn_Chn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                I1.setImageResource(R.drawable.chinabg);
                //getWindow().setBackgroundDrawableResource(R.drawable.chinabg);
                t1.setText("  你好 世界!! ");
            }
        });
        Button turn_Fre;
        turn_Fre = (Button) findViewById(R.id.button_Fre);
        turn_Fre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                I1.setImageResource(R.drawable.frenchbg);
                //getWindow().setBackgroundDrawableResource(R.drawable.chinabg);
                t1.setText("Salut tout le monde!!");
            }
        });
    }
}