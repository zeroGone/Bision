package motoroi.bision;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        LinearLayout[] deadline = {
                (LinearLayout)findViewById(R.id.deadline1),
                (LinearLayout)findViewById(R.id.deadline2),
                (LinearLayout)findViewById(R.id.deadline3),
                (LinearLayout)findViewById(R.id.deadline4),
                (LinearLayout)findViewById(R.id.deadline5),
                (LinearLayout)findViewById(R.id.deadline6),
                (LinearLayout)findViewById(R.id.deadline7),
                (LinearLayout)findViewById(R.id.deadline8),
                (LinearLayout)findViewById(R.id.deadline9),
                (LinearLayout)findViewById(R.id.deadline10)
        };

        LinearLayout[] ranking = {
                (LinearLayout)findViewById(R.id.ranking1),
                (LinearLayout)findViewById(R.id.ranking2),
                (LinearLayout)findViewById(R.id.ranking3),
                (LinearLayout)findViewById(R.id.ranking4),
                (LinearLayout)findViewById(R.id.ranking5),
                (LinearLayout)findViewById(R.id.ranking6),
                (LinearLayout)findViewById(R.id.ranking7),
                (LinearLayout)findViewById(R.id.ranking8),
                (LinearLayout)findViewById(R.id.ranking9),
                (LinearLayout)findViewById(R.id.ranking10)
        };

        for(int i=0; i<deadline.length; i++){
            deadline[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainIntro.class);
                    startActivity(intent);
                }
            });

            ranking[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainIntro.class);
                    startActivity(intent);
                }
            });
        }

        Button menuButton = (Button)findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout)findViewById(R.id.menu_draw);
                if(drawer.isDrawerOpen(Gravity.LEFT)) drawer.closeDrawer(Gravity.LEFT);
                else drawer.openDrawer(Gravity.LEFT);
            }
        });

        ArrayList<Menu> DataList = new ArrayList<Menu>();
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.menu_view);
        Menu menu1 = new Menu("공지사항");
        Menu menu2 = new Menu("고객센터/도움말");
        menu2.child.add("1:1문의하기");
        menu2.child.add("도움말");
        Menu menu3 = new Menu("환경설정");
        DataList.add(menu1);
        DataList.add(menu2);
        DataList.add(menu3);

        MenuAdapter adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_main,R.layout.menu_child,DataList);
        listView.setAdapter(adapter);
    }
}
