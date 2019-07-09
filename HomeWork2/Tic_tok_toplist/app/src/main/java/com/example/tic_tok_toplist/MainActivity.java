package com.example.tic_tok_toplist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }
}
