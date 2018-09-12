package motoroi.bision;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoticeFragment extends Fragment {//공지사항 화면 코드
    private MainView mainView;
    private TextView textView;

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
        mainView.loadingOn();//로딩 애니메이션 보여주기
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_notice,container,false);
        textView=viewGroup.findViewById(R.id.notice_contents);
        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);//밑줄 긋기

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Notice").document("Notice").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                textView.setText(documentSnapshot.get("contents").toString());//디비 연결 성공 했을 경우

            }
        }).addOnFailureListener(new OnFailureListener() {//디비 연결 실패 했을 경우
            @Override
            public void onFailure(@NonNull Exception e) {
                textView.setText("데이터 연결 실패");
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {//디비 불러오기 완료 했을 경우
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mainView.loadingOff();
            }
        });
        return viewGroup;
    }
}
