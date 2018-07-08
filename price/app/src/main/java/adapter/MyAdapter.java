package adapter;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.price.R;

import java.util.List;

import bean.ListBean;


/**
 * Created by asus on 2018/4/16.
 */

public class MyAdapter extends BaseAdapter {
    private List<ListBean> dataList;
    private Context context;
    private int resource;
    TextView name;
    TextView license1;
    TextView balances;
    TextView postion;
    TextView carLicense;
    Button btn_charge;
    private CheckBox cb_select;
    String license;

    /**
     * 有参构造
     *
     * @param context  界面
     * @param dataList 数据
     * @param resource
     */
    public MyAdapter(Context context, List<ListBean> dataList,
                     int resource) {
        this.context = context;
        this.dataList = dataList;
        this.resource = resource;

    }

    /**
     * 数据大小
     *
     * @return
     */
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int index) {
        return dataList.get(index);
    }

    /**
     * @param index
     * @return
     */
    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View view, ViewGroup arg2) {

        // 给xml布局文件创建java对象
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource, null);
        // 指向布局文件内部组件
        cb_select = view.findViewById(R.id.cb_select);
        name = view.findViewById(R.id.tv_carName);
        license1 = view.findViewById(R.id.tv_license);
        balances = view.findViewById(R.id.tv_balance);
        postion = view.findViewById(R.id.tv_position);
        btn_charge = view.findViewById(R.id.btn_recharge);
        name.setText("车主:" + dataList.get(index).getLinsice());
        license1.setText(dataList.get(index).getName());
        balances.setText("余额:" + dataList.get(index).getMoney() + "元");
        postion.setText((index + 1) + ".");

        btn_charge.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                showChargeDialog();
            }
        });
        return view;
    }
//充值对话框

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showChargeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.charge_dialog);
        final AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = 600;
        params.height = 550;
        dialog.getWindow().setAttributes(params);

        carLicense = dialog.findViewById(R.id.tv_car_license);
        carLicense.setText(license);
        final EditText etFee = dialog.findViewById(R.id.et_fee);
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_charge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etFee.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "充值成功", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "充值金额不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

