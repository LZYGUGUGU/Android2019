package com.example.tic_tok_toplist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TopListActivity extends MainActivity {
    RecyclerView recyclerView;
    String ranking[]={"1.","2.","3.","4.","5.","6.","7.","8.","9.","10.",};
    String content[]={
            "青青园中葵",
            "朝露待日晞",
            "阳春布德泽",
            "万物生光辉",
            "常恐秋节至",
            "焜黄华叶衰",
            "百川东到海",
            "何时复西归",
            "少壮不努力",
            "老大徒伤悲"
    };
    String number[]={
            "11111111","11111110","11111101","11111011","11110111","11011111","11101111","10111111","1111111","1111110"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toplist);
        String name= getIntent().getStringExtra("name");
        TextView topbar=findViewById(R.id.topbar);
        topbar.setText("欢迎！"+name);
        recyclerView = (RecyclerView) findViewById(R.id.myrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    class MyAdapter extends  RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewTypr){
            MyHolder myHolder = new MyHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.listforone, parent, false));
            return  myHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyHolder mm = (MyHolder) holder;
            mm.te1.setText(ranking[position]);
            mm.te2.setText(content[position]);
//            mm.te3.setText(number[position]);
        }

        @Override
        public int getItemCount() {
            return ranking.length;
        }
        class MyHolder extends RecyclerView.ViewHolder {

            TextView te1, te2,te3;

            public MyHolder(final View itemView) {
                super(itemView);
                te1 = itemView.findViewById(R.id.ranking);
                te2 = itemView.findViewById(R.id.content);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(TopListActivity.this,"click",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }
}
