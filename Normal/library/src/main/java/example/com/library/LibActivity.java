package example.com.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class LibActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib);

        WebView webView = (WebView) findViewById(R.id.web);
        webView.loadUrl("http://www.baidu.com");
    }
}
