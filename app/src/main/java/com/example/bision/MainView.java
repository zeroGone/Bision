package com.example.bision;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainView extends AppCompatActivity {
    HorizontalScrollView deadline_scroll;
    LinearLayout deadlineView;
    LinearLayout deadline1;
    LinearLayout deadline3;
    Button deadline1_button;
    ImageView deadline2_imageView;
    Button menu_button;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        deadline_scroll = (HorizontalScrollView)findViewById(R.id.deadline_scroll);
        deadline_scroll.requestDisallowInterceptTouchEvent(true);

        deadlineView = (LinearLayout)findViewById(R.id.deadline_view);
        deadline1 = (LinearLayout)findViewById(R.id.deadline1);
        deadline1_button=(Button)findViewById(R.id.deadline1_button);
        deadline1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),  MainIntro.class);
                startActivity(intent);
            }
        });
        deadline2_imageView=(ImageView)findViewById(R.id.deadline2_image);

        deadline2_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),  MainIntro.class);
                startActivity(intent);
            }
        });

        deadline3=(LinearLayout)findViewById(R.id.deadline3);
        deadline3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),  MainIntro.class);
                startActivity(intent);
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.menu_draw);
        drawer.requestDisallowInterceptTouchEvent(true);
        menu_button=(Button)findViewById(R.id.menuButton);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerOpen(Gravity.LEFT)) drawer.closeDrawer(Gravity.LEFT);
                else drawer.openDrawer(Gravity.LEFT);
            }
        });
    }
}
