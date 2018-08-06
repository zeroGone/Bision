package motoroi.bision;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.w3c.dom.Text;

public class HelpViewAdapter extends PagerAdapter {
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
        ImageView imageView =(ImageView)v.findViewById(R.id.help_imageView);
        imageView.setImageResource(images[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
