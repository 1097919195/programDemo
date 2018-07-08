package com.example.asus_pc.loginsystem;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button register,back;
    EditText username,password,password2;
    MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
        back= (Button) findViewById(R.id.button2);
        register= (Button) findViewById(R.id.button1);
        username= (EditText) findViewById(R.id.zc_username);
        password= (EditText) findViewById(R.id.zc_password);
        password2= (EditText) findViewById(R.id.zc2_password);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("您确定要退出注册吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        RegisterActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUserName = (EditText) findViewById(R.id.zc_username);
                EditText etPsw = (EditText) findViewById(R.id.zc_password);
                String name = etUserName.getText().toString();
                String password = etPsw.getText().toString();
                if(checkName(name)&&checkPsw(password)){
                    if(CheckDataIsIn(name)){
                        Toast.makeText(RegisterActivity.this,"该用户名已经被注册，注册失败", Toast.LENGTH_LONG).show();
                    }else
                    {
                        if(register(name,password))
                        {
                            Toast.makeText(RegisterActivity.this,"插入数据表成功", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                // checkEmail();
            }

            private boolean CheckDataIsIn(String value) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String Query = "Select * from userData where name =?";
                Cursor cursor = db.rawQuery(Query,new String[]{value});
                if(cursor.getCount()>0)
                {
                    cursor.close();
                    return true;
                }
                cursor.close();
                return false;
            }
        });
    }
    private boolean checkPsw(String psw) {
        if(psw.length()<3||psw.length()>6)
        {
            Toast.makeText(RegisterActivity.this,"密码长度必须为3-6位", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            for (int i = psw.length();--i>=0;){
                char c = psw.charAt(i);
                if((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z') || Character.isDigit(c) )
                {
                    return   true;
                }else{
                    Toast.makeText(RegisterActivity.this,"用户名必须为字母或数字", Toast.LENGTH_LONG).show();
                    return   false;
                }
            }
        }
        return true;
    }
    private boolean checkName(String name) {
        if(name.length()<6||name.length()>12)
        {
            Toast.makeText(RegisterActivity.this,"用户名长度必须为6-12位字母", Toast.LENGTH_LONG).show();
            return false;
        }else
        {
            for (int i = name.length();--i>=0;){
                char c = name.charAt(i);
                if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z')))
                {
                    return   true;
                }else{
                    Toast.makeText(RegisterActivity.this,"用户名必须为字母", Toast.LENGTH_LONG).show();
                    return   false;
                }
            }

        }
        return true;
    }

    private boolean register(String name, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("password",password);
        db.insert("userData",null,values);
        db.close();
        return true;
    }
    //int BackTime=0;
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        //builder.setIcon(R.drawable.tools);
        builder.setMessage("您确定要退出注册吗？");
        builder.setTitle("提示");

        
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                RegisterActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
