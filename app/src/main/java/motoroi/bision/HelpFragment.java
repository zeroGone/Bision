package motoroi.bision;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HelpFragment extends Fragment {//도움말 화면코드

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_help, container, false);

        ViewPager viewPager = viewGroup.findViewById(R.id.help_viewpager);//뷰페이저 객체 초기화
        HelpViewAdapter helpViewAdapter = new HelpViewAdapter(getContext());//뷰페이저의 어댑터 객체
        viewPager.setAdapter(helpViewAdapter);//뷰페이저 객체 어댑터 설정
        return viewGroup;
    }

    public static class HelpViewAdapter extends PagerAdapter {//어댑터
        private int[] images = {
                R.drawable.help_image1,
                R.drawable.help_image2,
                R.drawable.help_image3,
                R.drawable.help_image4,
        };
        private Context context;
        private LayoutInflater inflater;
        public HelpViewAdapter(Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==(LinearLayout)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.help_layout,container,false);
            ImageView imageView =v.findViewById(R.id.help_imageView);
            imageView.setImageResource(images[position]);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.invalidate();
        }
    }
}
