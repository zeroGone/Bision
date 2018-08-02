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
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {
    MainView mainView;
    List<DocumentSnapshot> allList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    Map<Object,Object>[] rankingList = new Map[5];//랭킹을 담을 Map배열
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

                for(int i=0; i<allList.size(); i++)
                    introList.add((Map)allList.get(i).get("intro"));//introList의 intro 객체들을 다 받는다



                for(int i=0; i<introList.size(); i++){//랭킹 셋팅 알고리즘
                    int rank = Integer.parseInt(introList.get(i).get("ranking").toString());
                    for(int j=0; j<rankingList.length; j++){
                        if(rankingList[j]==null) rankingList[j]=introList.get(i);
                        else if(rank>=Integer.parseInt(rankingList[j].get("ranking").toString())){
                            for(int z=4; z>j; z--) rankingList[z]=rankingList[z-1];
                            rankingList[j]=introList.get(i);
                            break;
                        }
                    }
                }

                for(int i=0; i<rankingList.length; i++){
                    String path = rankingList[i].get("name").toString()+".jpg";
                    Log.d(this.getClass().getName(),path);
                    StorageReference img = storage.child(path);
                    Glide.with(MainFragment.super.getContext()).using(new FirebaseImageLoader()).load(img).override(300,300).into(ranking[i]);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {//디비연결 실패시
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return viewGroup;
    }
}
