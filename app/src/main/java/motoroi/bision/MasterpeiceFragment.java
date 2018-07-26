package motoroi.bision;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MasterpeiceFragment extends Fragment {
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
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_masterpeice,container,false);
        ImageButton mapButton = (ImageButton)viewGroup.findViewById(R.id.mapButton);
        final ImageView mapView = (ImageView)viewGroup.findViewById(R.id.mapView);
        final LinearLayout mainIntro = (LinearLayout)viewGroup.findViewById(R.id.main_intro);

        mapButton.setOnClickListener(new View.OnClickListener() {
            int check = 0;
            @Override
            public void onClick(View view) {
                if(check==0){
                    mainIntro.setVisibility(View.INVISIBLE);
                    mapView.setVisibility(View.VISIBLE);
                    check=1;
                }
                else{
                    mapView.setVisibility(View.INVISIBLE);
                    mainIntro.setVisibility(View.VISIBLE);
                    check=0;
                }
            }
        });
        return viewGroup;
    }
}
