package example.com.sqlitedemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import example.com.sqlitedemo.Utils.Constant;
import example.com.sqlitedemo.Utils.DBManager;
import example.com.sqlitedemo.Utils.MySQliteHelper;

public class MainActivity extends AppCompatActivity {

    private MySQliteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper=DBManager.getIntance(getApplication());
    }

    public void createDB(View view) {

        //getWriteableDatabase,getReadableDatabase 创建或者打开数据库（可读可写的数据库对象），如果不存在则创建数据库
        //如果磁盘已满或者数据库自身权限getReadableDatabase只读数据库
        SQLiteDatabase db = helper.getWritableDatabase();
    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.insert:
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "insert into "+ Constant.TABLE_NAME+" values(1,'zhangsan',20)";
                DBManager.execSQL(db,sql);
                String sql2 = "insert into "+ Constant.TABLE_NAME+" values(2,'lisi',21)";
                DBManager.execSQL(db,sql2);
                db.close();
                break;
            case R.id.update:
                db = helper.getWritableDatabase();
                String updatesql = "update "+Constant.TABLE_NAME+
                        " set "+Constant.NAME+"='wangwu' where "+Constant._ID+"=1";
                DBManager.execSQL(db,updatesql);
                db.close();
                break;
            case R.id.delete:
                db = helper.getWritableDatabase();
                String delesql = "delete from "+Constant.TABLE_NAME+ "where"+Constant._ID+"=2";
                DBManager.execSQL(db,delesql);
                db.close();
                break;
        }
    }
}
