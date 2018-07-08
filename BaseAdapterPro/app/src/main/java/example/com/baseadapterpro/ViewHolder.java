package example.com.baseadapterpro;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * map<int,vlues>
 * Created by asus-pc on 2017/8/20.
 */

public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPostion;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup viewGroup,int layoutId,int postion) {

        this.mPostion = postion;
        this.mViews = new SparseArray <View> ();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, viewGroup,false);

        mConvertView.setTag(this);
    }

    //入口方法
    //通过类本身的静态函数创建实例:一种工厂模式(工厂模式就相当于创建实例对象的new,会给你系统带来更大的可扩展性和尽量少的修改量)
    //将对象的创建方式与接受者解耦 当有需要替换实现或者更新流程的时候不需要考虑更改调用者的实现
    public static ViewHolder get(Context context, View convertView,ViewGroup parent,int layoutId,int postion) {

        if (convertView == null) {
            return new ViewHolder(context, parent,layoutId,postion);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            //就算是复用的postion还是得更新下
            holder.mPostion = postion;
            return holder;
        }
    }

    /**
     * 通过viewId获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
