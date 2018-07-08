package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.price.R;

import java.util.List;

/**
 * Created by asus on 2018/4/19.
 */

public class RecyclerAdater extends RecyclerView.Adapter<MyViewHolder>{
    private List<String> data;
    private int expand_list;
    private Context context;

    public RecyclerAdater(Context context,List<String> data, int expand_list) {
        this.data = data;
        this.expand_list = expand_list;
        this.context=context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(expand_list,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String str=data.get(position);
        holder.tv.setText(str);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tv;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv=itemView.findViewById(R.id.child_title);
    }

}

