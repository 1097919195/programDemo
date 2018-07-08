package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.asus.price.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import bean.ChildBean;
import bean.GroupBean;

/**
 * Created by asus on 2018/4/17.
 */

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GroupBean> group;
    List<ArrayList<ChildBean>> child;

    public ExpandAdapter(Context context, List<GroupBean> group, List<ArrayList<ChildBean>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return child.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return group.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return child.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isexpand, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.expand_list,viewGroup,false);
        TextView title=view.findViewById(R.id.parent_title);
        title.setText(group.get(i).getGroup());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_itm, viewGroup, false);
            TextView title = view.findViewById(R.id.tv_time);
            TextView BusCode = view.findViewById(R.id.tv_busCode);
            TextView tvTime = view.findViewById(R.id.tv_time);
            TextView tv_distance = view.findViewById(R.id.tv_distance);
            Log.e("datavvv",i+"  "+i1+"  "+child.size()+"  "+child.get(i).size());

        title.setText(child.get(0).get(0).getStation());
        BusCode.setText(child.get(i).get(i1).getBuscode());
        tvTime.setText(child.get(i).get(i1).getTimeStart());
        tv_distance.setText(child.get(i).get(i1).getChildStr());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
