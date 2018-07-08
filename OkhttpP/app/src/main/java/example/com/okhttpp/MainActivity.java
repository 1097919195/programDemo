package example.com.okhttpp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.androidannotations.annotations.EActivity;

//@EActivity (R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private Button jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jump = (Button) findViewById(R.id.jump);

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SimpleOkhttpActivity_.class);
                startActivity(intent);
            }
        });
    }
}
