package example.com.sqlitedemo.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 主要对数据库操作的工具类
 */

public class DBManager {
    private static MySQliteHelper helper;

    public static MySQliteHelper getIntance(Context context) {
        if (helper == null) {
            helper = new MySQliteHelper(context);
        }
        return helper;
    }

    /**
     * 根据sql语句在数据库中执行语句
     * @param db 数据库对象
     * @param sql sql语句
     */
    public static void execSQL(SQLiteDatabase db, String sql) {

        if (db != null) {
            if (sql != null&&"".equals(sql)) {//sql不等于null且非空

                db.execSQL(sql);
            }
        }
    }
}
