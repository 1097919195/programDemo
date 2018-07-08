package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.price.R;

import java.util.List;

import bean.ListBean;

/**
 * Created by asus on 2018/4/18.
 */

public class FourthAdapter extends BaseAdapter{
    private boolean isShow=true;
    private Context context;
    private List<ListBean> datas;
    private int resouse;
    private LinearLayout lin_show;
    private TextView tvBusOcde,tvTime,tvDistance,tvShow;
    public FourthAdapter(Context context, List<ListBean> datas, int resouse) {
        this.context = context;
        this.datas = datas;
        this.resouse = resouse;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(resouse,viewGroup,false);
        tvBusOcde=view.findViewById(R.id.tv_busCode);
        tvTime=view.findViewById(R.id.tv_time);
        lin_show=view.findViewById(R.id.lin_show);
        tvDistance=view.findViewById(R.id.tv_distance);
//        tvShow=view.findViewById(R.id.tv_show);
        tvBusOcde.setText(datas.get(i).getLinsice());


//            tvShow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(isShow) {
//                      Log.e("data",i+"隐藏");
//                      lin_show.setVisibility(View.GONE);
//                        isShow =false;
//                    }else {
//                        isShow = true;
//                        lin_show.setVisibility(View.VISIBLE);
//                        Log.e("data",i+"显示");
//                    }
//                }
//            });

        return view;
    }
}
