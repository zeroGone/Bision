package motoroi.bision;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainFragment extends Fragment {
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
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_main,container,false);

        LinearLayout[] deadline = {
                (LinearLayout)viewGroup.findViewById(R.id.deadline1),
                (LinearLayout)viewGroup.findViewById(R.id.deadline2),
                (LinearLayout)viewGroup.findViewById(R.id.deadline3),
                (LinearLayout)viewGroup.findViewById(R.id.deadline4),
                (LinearLayout)viewGroup.findViewById(R.id.deadline5),
                (LinearLayout)viewGroup.findViewById(R.id.deadline6),
                (LinearLayout)viewGroup.findViewById(R.id.deadline7),
                (LinearLayout)viewGroup.findViewById(R.id.deadline8),
                (LinearLayout)viewGroup.findViewById(R.id.deadline9),
                (LinearLayout)viewGroup.findViewById(R.id.deadline10)
        };

        for(int i=0; i<deadline.length; i++){
            deadline[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainView.onFragmentChanged(0);
                }
            });
        }

        LinearLayout[] ranking = {
                (LinearLayout)viewGroup.findViewById(R.id.ranking1),
                (LinearLayout)viewGroup.findViewById(R.id.ranking2),
                (LinearLayout)viewGroup.findViewById(R.id.ranking3),
                (LinearLayout)viewGroup.findViewById(R.id.ranking5),
                (LinearLayout)viewGroup.findViewById(R.id.ranking6),
                (LinearLayout)viewGroup.findViewById(R.id.ranking7),
                (LinearLayout)viewGroup.findViewById(R.id.ranking8),
                (LinearLayout)viewGroup.findViewById(R.id.ranking9),
                (LinearLayout)viewGroup.findViewById(R.id.ranking10)
        };

        for(int i=0; i<ranking.length; i++){
            ranking[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainView.onFragmentChanged(0);
                }
            });
        }

        return viewGroup;
    }
}
