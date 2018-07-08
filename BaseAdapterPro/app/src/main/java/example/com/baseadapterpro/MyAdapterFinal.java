package example.com.baseadapterpro;

/**
 * Created by asus-pc on 2017/8/20.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapterFinal extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Bean> mDatas;
    private Context mContext;

    public MyAdapterFinal(Context context, List<Bean> datas) {
        mContext = context;
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
    public View getView(int postion, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        //判断是否复用
//        if (view == null) {
//
//            view = mInflater.inflate(R.layout.item_listview, viewGroup, false);
//            holder = new ViewHolder();
//
//            holder.mTille = view.findViewById(R.id.title);
//            holder.mDesc = view.findViewById(R.id.desc);
//            holder.mTime = view.findViewById(R.id.time);
//            holder.mPhone = view.findViewById(R.id.phone);
//
//            view.setTag(holder);
//        } else {
//            holder= (ViewHolder) view.getTag();
//        }

        ViewHolder holder = ViewHolder.get(mContext, convertView,parent,R.layout.item_listview,postion);

        Bean bean = mDatas.get(postion);

        //(TextView)holder.getView(R.id.title).setText(bean.getTitle());
        TextView title = holder.getView(R.id.title);
        TextView desc = holder.getView(R.id.desc);
        TextView time = holder.getView(R.id.time);
        TextView phone = holder.getView(R.id.phone);

        //listview里面实现按钮的监听
        Button btn = (Button) holder.getView(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"succeed",Toast.LENGTH_SHORT).show();
            }
        });

        title.setText(bean.getTitle());
        desc.setText(bean.getDesc());
        time.setText(bean.getTime());
        phone.setText(bean.getPhone());

        return holder.getConvertView();
    }

}
