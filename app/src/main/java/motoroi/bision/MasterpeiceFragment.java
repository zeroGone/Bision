package motoroi.bision;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MasterpeiceFragment extends Fragment implements BeaconConsumer{
    MainView mainView;
    TextView contents;
    MediaPlayer player = new MediaPlayer();
    SeekBar controller;
    TextView musicCurrentTime;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    Runnable current = new Runnable() {
        @Override
        public void run() {
            try{
                musicCurrentTime.setText(simpleDateFormat.format(player.getCurrentPosition()));
                musicCurrentTime.post(current);
            }catch (IllegalStateException e){
                handler.removeCallbacks(this);
            }
        }
    };
    Handler handler = new Handler();

    BeaconManager beaconManager;
    private List<Beacon> beaconList = new ArrayList<>();

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mainView = (MainView)getActivity();
    }

    @Override
    public void onDetach(){
        player.release();//플레이어 해제
        mainView = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView.loadingOn(getActivity());
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_masterpeice,container,false);
        ImageButton mapButton = (ImageButton)viewGroup.findViewById(R.id.mapButton);
        final ImageView mapView = (ImageView)viewGroup.findViewById(R.id.mapView);
        final LinearLayout mainIntro = (LinearLayout)viewGroup.findViewById(R.id.main_intro);
        contents = (TextView) viewGroup.findViewById(R.id.masterpeice_contents);

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

        ImageButton playButton = (ImageButton)viewGroup.findViewById(R.id.play_button);//재생버튼
        ImageButton stopButton = (ImageButton)viewGroup.findViewById(R.id.stop_button);//정지버튼
        ImageButton rewindButton = (ImageButton)viewGroup.findViewById(R.id.rewind_button);//되감기버튼
        ImageButton forwardButton = (ImageButton)viewGroup.findViewById(R.id.forward_button);//빨리감기버튼
        controller = (SeekBar)viewGroup.findViewById(R.id.music_controller);//재생컨트롤러
        final TextView musicSize = (TextView)viewGroup.findViewById(R.id.music_size);//음악 총시간
        musicCurrentTime = (TextView)viewGroup.findViewById(R.id.music_current);//현재재생시간
        //재생버튼 클릭설정
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.start();
                Thread();
                current.run();
            }
        });
        //정지버튼 클릭설정
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.pause();
            }
        });
        //되감기버튼 클릭설정
        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(player.getCurrentPosition()-2000);//2초뒤로
            }
        });
        //빨리감기버튼 클릭설정
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(player.getCurrentPosition()+2000);//2초앞으로
            }
        });

        StorageReference mp3 = FirebaseStorage.getInstance().getReference().child("mp3").child("뚜두뚜두.mp3");//디비에서 음악불러옴
        mp3.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                try {
                    player.setDataSource(task.getResult().toString());//uri로 받아서 셋팅
                    player.prepare();
                    controller.setMax(player.getDuration());//컨트롤러 길이 셋팅
                    musicSize.setText(simpleDateFormat.format(player.getDuration()));
                    musicCurrentTime.setText(simpleDateFormat.format(player.getCurrentPosition()));
                    mainView.loadingOff();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        controller.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) player.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


//        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
//        beaconManager = BeaconManager.getInstanceForApplication(this.getContext());
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
//        beaconManager.bind(this);

        return viewGroup;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                if(collection.size()>0)
                    Log.d(this.getClass().getName(),"비콘:"+collection.iterator().next().getId2());
            }
        });
        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId",null,null,null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Context getApplicationContext() {
        return this.getContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }

    //뮤직 컨트롤러를 위한 쓰레드 메소드
    public void Thread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(player.isPlaying()) {
                    try {
                        Thread.sleep(1000);
                        controller.setProgress(player.getCurrentPosition());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e){
                        handler.removeCallbacks(this);
                        break;
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
