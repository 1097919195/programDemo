package example.com.baseadapterpro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mlistView;
    private List<Bean> mDatas;
    private MyAdapter mAdapter;
    private MyAdapterFinal mAdapterFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initView();
    }

    private void initView() {
        mlistView = (ListView) findViewById(R.id.listview);
        //mlistView.setAdapter(mAdapter);
        mlistView.setAdapter(mAdapterFinal);

    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        Bean bean = new Bean("Android新技能1111111111111111111111122222222222222222","Android打造万能的适配器", "2017.8.8","10086");

        mDatas.add(bean);
        mDatas.add(bean);
        mDatas.add(bean);
        mDatas.add(bean);
        mDatas.add(bean);
        mDatas.add(bean);
        mDatas.add(bean);

        //mAdapter = new MyAdapter(this,mDatas);
        mAdapterFinal = new MyAdapterFinal(this,mDatas);
    }
}
