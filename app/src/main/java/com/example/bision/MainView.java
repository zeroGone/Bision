package com.example.bision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainView extends AppCompatActivity {
    HorizontalScrollView deadline_scroll;
    LinearLayout deadlineView;
    LinearLayout deadline1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        deadline_scroll = (HorizontalScrollView)findViewById(R.id.deadline_scroll);
        deadline_scroll.requestDisallowInterceptTouchEvent(true);

        deadlineView = (LinearLayout)findViewById(R.id.deadline_view);
        deadline1 = (LinearLayout)findViewById(R.id.deadline1);
        deadlineView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        deadline1.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return true;
            }
        });

        deadline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainIntro.class);
                startActivity(intent);
            }
        });
    }
}
