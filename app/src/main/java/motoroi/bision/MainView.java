package motoroi.bision;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer =findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.getMenu().findItem(R.id.menu_settings_sound).setActionView(R.layout.nav_settings);
        navView.getMenu().findItem(R.id.menu_settings_vibration).setActionView(R.layout.nav_settings);
        navView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MainFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MainFragment()).commit();
            return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_question:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new QuestionFragment()).commit();
                break;
            case R.id.menu_notice:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new NoticeFragment()).commit();
                break;
        }
        if(item.getItemId()==R.id.menu_settings_sound||item.getItemId()==R.id.menu_settings_vibration);
        else drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public void onFragmentChanged(int index){
        if(index==0)
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new IntroFragment()).commit();
        else if(index==1)
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MasterpeiceFragment()).commit();
    }
}
