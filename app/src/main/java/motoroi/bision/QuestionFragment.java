package motoroi.bision;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class QuestionFragment extends Fragment {
    private EditText title;
    private EditText contents;
    private CheckBox[] checkBoxes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_question,container,false);

        //id 셋팅
        title = viewGroup.findViewById(R.id.question_title);
        contents = viewGroup.findViewById(R.id.question_contents);
        checkBoxes = new CheckBox[]{
                viewGroup.findViewById(R.id.checkBox1),
                viewGroup.findViewById(R.id.checkBox2),
                viewGroup.findViewById(R.id.checkBox3),
        };
        Button sendButton = viewGroup.findViewById(R.id.sendbutton);

        //체크박스 셋팅
        for(int i=0; i<checkBoxes.length; i++){
            final int check = i;//체킹한 박스를 위한 변수
            checkBoxes[check].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//체크박스 체크할때
                    for(int i=0; i<checkBoxes.length; i++)//모두 false로 초기화하고
                        checkBoxes[i].setChecked(false);
                    checkBoxes[check].setChecked(true);//체킹한 체크박스 true로
                }
            });
        }

        //전송버튼 클릭이벤트
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //체크박스의 유형을 받아옴
                String 유형 = null;
                for(CheckBox value:checkBoxes) {
                    if (value.isChecked()) {//체크한 박스의
                        유형 = value.getText().toString();//문자열을 받아옴
                        break;//받아오고나면 끝났으므로 break
                    }
                }

                if(유형==null) 경고(0);//체크박스를 체크안했을때
                else if(title.getText().length()==0) 경고(1);//제목을 입력안했을때
                else if(contents.getText().length()==0) 경고(2);//내용을 입력안했을때
                else{
                    Intent email = new Intent(Intent.ACTION_SEND);//이메일 인텐트 생성
                    email.setType("plain/text");//타입지정
                    email.putExtra(Intent.EXTRA_EMAIL,new String[]{"dudrhs571@gmail.com"});//이메일 주소 설정
                    email.putExtra(Intent.EXTRA_SUBJECT,"["+유형+"]"+title.getText().toString());//제목 설정
                    email.putExtra(Intent.EXTRA_TEXT,contents.getText().toString());//내용 설정
                    startActivity(email);//이메일 보내기 시작
                    getFragmentManager().beginTransaction().replace(R.id.frag_container,new MainFragment()).commit();//화면전환
                }
            }
        });
        return viewGroup;
    }

    //경고 다이얼로그를 위한 메소드
    private void 경고(int id){
        AlertDialog.Builder 경고 = new AlertDialog.Builder(getContext());//알림다이얼로그 객체 생성
        경고.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();//확인누르면 다이얼로그가 꺼지도록 한다
            }
        });
        경고.setIcon(R.drawable.ic_warning);//다이얼로그 아이콘 설정
        경고.setTitle("경고");//다이얼로그 제목 설정
        //다이얼로그 내용 설정
        if(id==0) 경고.setMessage("유형을 체크해주세요");
        else if(id==1) 경고.setMessage("제목을 입력하세요");
        else if(id==2) 경고.setMessage("내용을 입력하세요");
        경고.show();//다이얼로그 띄움
    }
}
