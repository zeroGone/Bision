package motoroi.bision;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class MenuAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<Menu> DataList;
    private LayoutInflater myinf = null;

    public MenuAdapter(Context context,int groupLay,int chlidLay,ArrayList<Menu> DataList){
        this.DataList = DataList;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }
        TextView groupName = (TextView)convertView.findViewById(R.id.menu_main);
        groupName.setText(DataList.get(groupPosition).groupName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null){
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }
        TextView childName = (TextView)convertView.findViewById(R.id.menu_child);
        childName.setText(DataList.get(groupPosition).child.get(childPosition));
        if(DataList.get(groupPosition).child.get(childPosition).toString().equals("1:1문의하기")){
            childName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(),MainIntro.class);
                    context.startActivity(intent);
                }
            });
        };
        if(DataList.get(groupPosition).child.get(childPosition).toString().equals("도움말")){
            childName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(),Masterpeice.class);
                    context.startActivity(intent);
                }
            });
        };
        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.size();
    }

    @Override
    public Menu getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

}
