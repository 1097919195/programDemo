package example.com.sqlitedemo.Utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteOpenHelper
 * 1.oncreate().onUpgrade等创建数据库更新数据库的方法
 * 2.提供获取数据库的函数
 */

public class MySQliteHelper extends SQLiteOpenHelper {
    /**
     * 构造函数
     * @param context  上下文对象
     * @param name     创建数据库的名称
     * @param factory  游标工厂
     * @param version  表示创建数据库的版本 >=1
     */
    public MySQliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQliteHelper(Context context) {
        super(context,Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    /**
     * 当数据库创建时回调的函数
     * @param db 数据库对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.e("Tag", "onCreate");
            String sql="create table"+Constant.TABLE_NAME+"("+
                    Constant._ID+"Integer primary key,"+
                    Constant.NAME+"varchar(10),"+
                    Constant.AGE+"Integer)";
            db.execSQL(sql);//执行SQL语句
        } catch (SQLException e) {
        }

    }

    /**
     * 当数据库更新时回调的函数
     * @param db   数据库对象
     * @param oldVersion    数据库旧版本
     * @param newVersion   数据库新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e("Tag", "onUpgrade");
    }

    /**
     * 当数据库打不开是回调的函数
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.e("Tag", "onOpen");
    }
}
