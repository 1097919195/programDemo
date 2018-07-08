package com.example.asus_pc.loginsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login,register;
    CheckBox remeber,automatic;
    EditText username,password;
    SharedPreferences sp;
    Editor ed;
    Boolean re_check=false,auto_check=false;
    MyDBHelper dbHelper;
    String user,paw;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
        login=(Button) findViewById(R.id.login_btn);
        register=(Button) findViewById(R.id.register_btn);
        remeber=(CheckBox) findViewById(R.id.remeber_paw);
        automatic=(CheckBox) findViewById(R.id.automatic_login);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        sp=getSharedPreferences("rem", MainActivity.this.MODE_PRIVATE);
        ed=sp.edit();

        username.setText(sp.getString("zc_user", null));
        password.setText(sp.getString("zc_pas", null));
        remeber.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(remeber.isChecked()){
                    Toast.makeText(MainActivity.this, "记住密码已选中", Toast.LENGTH_SHORT).show();
                    user=username.getText().toString();
                    paw=password.getText().toString();
                    ed.putString("user", user);
                    ed.putString("paw", paw);
                    ed.putBoolean("re_check", true);
                }else {
                    Toast.makeText(MainActivity.this, "记住密码已取消", Toast.LENGTH_SHORT).show();
                    ed.putBoolean("re_check", false);
                }
                ed.commit();
            }
        });
        automatic.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (automatic.isChecked()) {
                    Toast.makeText(MainActivity.this, "自动登录已选中", Toast.LENGTH_SHORT).show();
                    ed.putBoolean("auto_check", true);
                }else{
                    Toast.makeText(MainActivity.this, "自动登录已取消", Toast.LENGTH_SHORT).show();
                    ed.putBoolean("auto_check", false);
                }
                ed.commit();
            }
        });

        re_check=sp.getBoolean("re_check", false);
        auto_check=sp.getBoolean("auto_check", false);
        if(re_check==true){
            username.setText(sp.getString("user", null));
            password.setText(sp.getString("paw", null));
            remeber.setChecked(true);
            if (auto_check==true) {
                automatic.setChecked(true);
                Intent intent=new Intent(MainActivity.this,Successful.class);
                //intent.putExtra("user",username.getText().toString());
                startActivity(intent);
            }
        }else{
            ed.clear();
        }

        login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
//                if (password.length()>6) {
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(LoginActivity.this,"cuowu",Toast.LENGTH_SHORT).show();
//                }
                if (loginCheck(username.getText().toString(),password.getText().toString())==true)
                {
                    Toast.makeText(MainActivity.this,"登录成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Successful.class);
                    intent.putExtra("user",username.getText().toString());
                    startActivity(intent);
                }else
                {
                    ed.putBoolean("success",false);
                    Toast.makeText(MainActivity.this,"登录失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        register.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private  boolean loginCheck(String name, String psw){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String sql="select *from userData where name=? and password=?";
        Cursor cursor =db.rawQuery(sql,new String[]{name,psw});
        if (cursor.moveToFirst())
        {
            cursor.close();
            return true;
        }
        return false;
    }
    int BackTime=0;
    @Override
    public void onBackPressed() {
        if (BackTime==0){
            Toast.makeText(this,"再按一次退出", Toast.LENGTH_SHORT).show();
            BackTime=1;
            new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        BackTime=0;
                    }
                    super.run();
                }
            }.start();
            return;
        }else {
            this.finish();
        }
        super.onBackPressed();
    }
}
