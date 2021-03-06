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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Map;

public class IntroFragment extends Fragment{
    private TextView name;
    private TextView term;
    private TextView place;
    private TextView price;
    private TextView subject;
    private TextView masterpeice;
    private Button enterButton;
    private ImageView ImageView;
    private Map map;
    private MainView mainView;

    protected void setMap(Map map){
        this.map=map;
    }

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
        enterButton = viewGroup.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.onFragmentChange("masterpeice",map);
            }
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map_view);
        mainView.mapSet(supportMapFragment,map.get("latitude").toString(),map.get("longitude").toString());

        ImageView=viewGroup.findViewById(R.id.intro_image);
        name=viewGroup.findViewById(R.id.intro_name);
        term=viewGroup.findViewById(R.id.intro_term);
        place=viewGroup.findViewById(R.id.intro_place);
        price=viewGroup.findViewById(R.id.intro_price);
        subject=viewGroup.findViewById(R.id.intro_subject);
        masterpeice=viewGroup.findViewById(R.id.intro_masterpeice);

        name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        name.setText(map.get("name").toString());

        if (map.get("price").toString().equals("0"))  price.setText("가격 : 무료");
        else  price.setText("가격 : "+map.get("price").toString()+" 원");

        place.setText("장소 : "+map.get("place").toString());
        masterpeice.setText("대표작 : "+map.get("masterpiece").toString());
        subject.setText("주제 : "+map.get("subject").toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date start = (Date)map.get("startdate");
        String startDate = dateFormat.format(start);
        String endDate = null;
        if(map.get("deadline")!=null){
            Date deadline = (Date)map.get("deadline");
            endDate=dateFormat.format(deadline);
            term.setText("기간 : "+startDate+"~"+endDate);
        }else term.setText(startDate+" 개관");

        String path = map.get("name").toString() + ".png";//랭킹에 있는 것들의 사진을 불러오기 위한 문자열 path
        StorageReference img = MainFragment.storage.child(path);//저장소에 path 이미지를 참조하는 객체 생성
        Glide.with(IntroFragment.super.getContext()).using(new FirebaseImageLoader()).load(img).crossFade(0).override(600,600).into(ImageView);;

        return viewGroup;
    }
}
