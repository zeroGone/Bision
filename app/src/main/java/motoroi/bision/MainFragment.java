package motoroi.bision;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {
    MainView mainView;
    List<DocumentSnapshot> allList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    Map<Object,Object>[] rankingList = new Map[5];//랭킹을 담을 Map배열
    Map<Object,Object>[] deadlineList = new Map[3];//마감임박 리스트
    ImageButton nextButton;
    ImageButton backButton;
    ImageView deadlineImageView;
    TextView deadlineName;
    TextView deadlineDays;
    int deadlineCheck = 0;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mainView = (MainView)getActivity();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mainView = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_main,container,false);

        final ImageView[] ranking = {
                viewGroup.findViewById(R.id.ranking1),
                viewGroup.findViewById(R.id.ranking2),
                viewGroup.findViewById(R.id.ranking3),
                viewGroup.findViewById(R.id.ranking4),
                viewGroup.findViewById(R.id.ranking5),
        };

        deadlineImageView=viewGroup.findViewById(R.id.deadline_image);
        deadlineName=viewGroup.findViewById(R.id.deadline_name);
        deadlineDays=viewGroup.findViewById(R.id.deadline_days);
        nextButton=viewGroup.findViewById(R.id.nextButton);
        backButton=viewGroup.findViewById(R.id.backButton);

        for(int i=0; i<ranking.length; i++)
            ranking[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainView.onFragmentChanged(0);
                }
            });

        db.collection("Bision").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {//디비 연결 성공시
                allList = documentSnapshots.getDocuments();//Bision 컬렉션에 모든  문서들을 받아옴
                //모든 intro 객체를 받을 ArrayList
                //fireStore의 object형은 HashMap 형식으로 되있다
                ArrayList<Map<Object, Object>> introList = new ArrayList<>();

                for (int i = 0; i < allList.size(); i++)
                    introList.add((Map) allList.get(i).get("intro"));//introList의 intro 객체들을 다 받는다

                for (int i = 0; i < introList.size(); i++) {//셋팅 알고리즘
                    Map<Object, Object> temp = introList.get(i);//intro 객체 하나를 temp로 저장해서
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
                }//리스트 셋팅 알고리즘 끝

                for (int i = 0; i < rankingList.length; i++) {
                    String path = rankingList[i].get("name").toString() + ".png";//랭킹에 있는 것들의 사진을 불러오기 위한 문자열 path
                    StorageReference img = storage.child(path);//저장소에 path 이미지를 참조하는 객체 생성
                    Glide.with(MainFragment.super.getContext()).using(new FirebaseImageLoader()).load(img).override(400, 400).into(ranking[i]);
                }

                setDeadline();
            }
        }).addOnFailureListener(new OnFailureListener() {//디비연결 실패시
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deadlineCheck!=2) deadlineCheck++;
                setDeadline();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deadlineCheck!=0) deadlineCheck--;
                setDeadline();
            }
        });

        return viewGroup;
    }
    private void setDeadline(){
        String path = deadlineList[deadlineCheck].get("name").toString() + ".png";//랭킹에 있는 것들의 사진을 불러오기 위한 문자열 path
        StorageReference img = storage.child(path);//저장소에 path 이미지를 참조하는 객체 생성
        Glide.with(MainFragment.super.getContext()).using(new FirebaseImageLoader()).load(img).override(500, 500).into(deadlineImageView);

        deadlineName.setText(deadlineList[deadlineCheck].get("name").toString());
        Date deadlineDay = (Date)deadlineList[deadlineCheck].get("deadline");
        Date Today = new Date();//현재 날짜,시간 구한 객체

        Long dif = deadlineDay.getTime()-Today.getTime();
        deadlineDays.setText(Long.toString(dif/(24*60*60*1000))+" Days");
    }
}
