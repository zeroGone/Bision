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

public class helpFragment extends Fragment {
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
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_help,container,false);
        return viewGroup;
    }
}