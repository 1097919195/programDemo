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

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private List<String> data;
    private int rescouse;
    private Context context;
    private MyOnItemClickListener mListener;

    public OrderAdapter(Context context, List<String> data, int rescouse) {
        this.data = data;
        this.rescouse = rescouse;
        this.context = context;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(rescouse, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        holder.ivIMg.setText(data.get(position));
        holder.ivIMg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.clickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface MyOnItemClickListener{
        void clickListener(int postion);
    }

    public  void setMyOnItemClickListener (MyOnItemClickListener mListener) {
        this.mListener=mListener;
    }
}

 class OrderViewHolder extends RecyclerView.ViewHolder{
       TextView ivIMg;

     public OrderViewHolder(View itemView) {
         super(itemView);
         ivIMg = itemView.findViewById(R.id.iv_car);

     }
 }
