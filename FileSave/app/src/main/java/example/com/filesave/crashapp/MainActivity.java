package example.com.filesave.crashapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import example.com.filesave.R;

public class MainActivity extends AppCompatActivity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.text);

    }

    //启动另一个程序的service
    public void qweClick(View view) {
        //需要用户允许 程序关联启动
        Intent i = new Intent();
        i.setComponent(new ComponentName("example.com.aaaa","example.com.aaaa.service.PushService"));
        startService(i);
    }

    public void Click(View view) {
        save();
    }

    public void save() {
        try {
            //openFileOutput()来读写应用在内部存储空间上的文件（getFilesDir()获取你app的内部存储空间，相当于你的应用在内部存储上的根目录）
            FileOutputStream outStream = this.openFileOutput("a.txt", Context.MODE_PRIVATE);
            outStream.write(text.getText().toString().getBytes());
            outStream.close();
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            int a = 2 / 0;
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
}
