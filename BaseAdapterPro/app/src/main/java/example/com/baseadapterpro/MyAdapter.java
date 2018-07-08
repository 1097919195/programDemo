package example.com.baseadapterpro;

/**
 * Created by asus-pc on 2017/8/20.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Bean> mDatas;

    public MyAdapter(Context context, List<Bean> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        //判断是否复用
        if (view == null) {

            view = mInflater.inflate(R.layout.item_listview, viewGroup, false);

            holder = new ViewHolder();

            holder.mTille = view.findViewById(R.id.title);
            holder.mDesc = view.findViewById(R.id.desc);
            holder.mTime = view.findViewById(R.id.time);
            holder.mPhone = view.findViewById(R.id.phone);

            //Tag从本质上来讲是就是相关联的view的额外的信息。它们经常用来存储一些view的数据
            //View中的setTag(Onbect)表示给View添加一个格外的数据，以后可以用getTag()将这个数据取出来。
            view.setTag(holder);
        } else {
            holder= (ViewHolder) view.getTag();
        }

        Bean bean = mDatas.get(i);
        holder.mTille.setText(bean.getTitle());
        holder.mDesc.setText(bean.getDesc());
        holder.mTime.setText(bean.getTime());
        holder.mPhone.setText(bean.getPhone());

        return view;
    }

    private class ViewHolder {
        TextView mTille;
        TextView mDesc;
        TextView mTime;
        TextView mPhone;

    }
}
