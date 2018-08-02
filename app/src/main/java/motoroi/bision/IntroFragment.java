package motoroi.bision;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class IntroFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView name;
    private TextView term;
    private TextView place;
    private TextView price;
    private TextView subject;
    private TextView masterpeice;
    private Button enterButton;

    MainView mainView;
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
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_intro,container,false);
        enterButton = (Button)viewGroup.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.onFragmentChanged(1);
            }
        });

        name=viewGroup.findViewById(R.id.intro_name);
        term=viewGroup.findViewById(R.id.intro_term);
        place=viewGroup.findViewById(R.id.intro_place);
        price=viewGroup.findViewById(R.id.intro_price);
        subject=viewGroup.findViewById(R.id.intro_subject);
        masterpeice=viewGroup.findViewById(R.id.intro_masterpeice);

//        db.collection("sungkonghoi university").document("skhu").collection("intro").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot doc = task.getResult().getDocuments().get(0);
//                    name.setText(doc.get("name").toString());
//                    place.setText(doc.get("place").toString());
//                    masterpeice.setText(doc.get("masterpeice").toString());
//                    subject.setText(doc.get("subject").toString());
//                    price.setText(doc.get("price").toString()+"원");
//                    term.setText(doc.get("term").toString());
//                }else enterButton.setText("서버 오류");
//            }
//        });

        return viewGroup;
    }
}
