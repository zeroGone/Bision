package motoroi.bision;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private FirebaseFirestore db;
    protected List<DocumentSnapshot> allList;
    protected ArrayList<Map<Object, Object>> allintrolist;
    protected Map<Object,Object>[] rankingList = new Map[5];//랭킹을 담을 Map배열
    protected Map<Object,Object>[] deadlineList = new Map[3];//마감임박 리스트
    protected Map<Object,Object>[] getRankingList(){
        return rankingList;
    }
    protected Map<Object,Object>[] getDeadlineList(){
        return deadlineList;
    }
    protected ArrayList<Map<Object,Object>> getAllIntrolist(){
        return allintrolist;
    }
    private Dialog loadingDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        loadingOn(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        db= FirebaseFirestore.getInstance();
        db.collection("Bision").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                allList = documentSnapshots.getDocuments();

                allintrolist = new ArrayList<>();

                for (int i = 0; i < allList.size(); i++)
                    allintrolist.add((Map) allList.get(i).get("intro"));//introList의 intro 객체들을 다 받는다

                for (int i = 0; i < allintrolist.size(); i++) {//셋팅 알고리즘
                    Map<Object, Object> temp = allintrolist.get(i);//intro 객체 하나를 temp로 저장해서
                    int rank = Integer.parseInt(temp.get("ranking").toString());//객체의 랭킹을 저장
                    if (temp.get("deadline") != null) {//만약 마감날짜가 있으면
                        Date deadlineDate = (Date) temp.get("deadline");//마감날짜를 받아서
                        Long deadline = deadlineDate.getTime();//검사를 위해 Long형으로 받고
                        for (int j = 0; j < deadlineList.length; j++) {//마감날짜 리스트를 하나씩 검사
                            if (deadlineList[j] == null){
                                deadlineList[j] = temp;//마감날짜 리스트가 비어있으면 temp를 집어 넣는다
                                break;
                            } else {
                                Date deadlintTemp = (Date) deadlineList[j].get("deadline");
                                if (deadline <= deadlintTemp.getTime()) {//마감 리스트에 있는 값들과 비교해서 현재 마감날짜가 더작을시
                                    for (int z = 2; z > j; z--)
                                        deadlineList[z] = deadlineList[z - 1];//마감리스트들 뒤로하나씩땡기고
                                    deadlineList[j] = temp;//자리에 삽입
                                    break;
                                }
                            }
                        }
                    }

                    for (int j = 0; j < rankingList.length; j++) {
                        if (rankingList[j] == null) rankingList[j] = temp;
                        else if (rank >= Integer.parseInt(rankingList[j].get("ranking").toString())) {
                            for (int z = 4; z > j; z--) rankingList[z] = rankingList[z - 1];
                            rankingList[j] = temp;
                            break;
                        }
                    }
                    loadingOff();
                }//리스트 셋팅 알고리즘 끝

                //메인프래그먼트 소환
                if(savedInstanceState==null) getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MainFragment()).commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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
            case R.id.menu_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new HelpFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else  getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new MainFragment()).commit();
    }//기기 뒤로가기버튼 눌렀을때 실행되는 메소드

    public void onFragmentChange(String index, Map map){
        if(index.equals("intro")){
            IntroFragment introFragment = new IntroFragment();
            introFragment.setMap(map);
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,introFragment).commit();
        } else if(index.equals("masterpeice")) getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new MasterpeiceFragment()).commit();
    }

    public void dbUpdate(Map map){
        //1.파라미터 map이 들어있는 문서를 찾고
        String docName="";
        for(int i=0; i<allList.size(); i++){
            Map temp = (Map)allList.get(i).get("intro");
            if(map.get("name").toString().equals(temp.get("name").toString())) docName=allList.get(i).getId();
        }
        Long rank = (Long)map.get("ranking");
        rank++;
        map.put("ranking",rank);
        db.collection("Bision").document(docName).update("intro",map);
    }

    public void loadingOn(Activity activity){
        loadingDialog = new Dialog(activity);
        loadingDialog .getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog .requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog .setContentView(R.layout.loading_dialog);
        ImageView aniView = (ImageView)loadingDialog.findViewById(R.id.loadingView);
        aniView.setBackgroundResource(R.drawable.loading_list);
        AnimationDrawable ani = (AnimationDrawable)aniView.getBackground();
        ani.start();
        loadingDialog .show();
    }

    public void loadingOff(){
        loadingDialog.dismiss();
    }
}
