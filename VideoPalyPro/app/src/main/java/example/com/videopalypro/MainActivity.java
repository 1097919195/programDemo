package example.com.videopalypro;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private VideoView videoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/....";

        videoview = (VideoView) findViewById(R.id.videoview);
        /**
         * 本地视屏播放
         */

        //videoview.setVideoPath("path");

        /**
         * 网络视频播放
         */
        videoview.setVideoURI(Uri.parse("http://www.huya.com/laowangge"));
        /**
         * 使用MediaController控制视频播放
         */
        MediaController controller = new MediaController(this);
        /**
         * 设置Videoview与MediaController建立关联
         */
        videoview.setMediaController(controller);
        /**
         * 设置MediaController与Videoview建立关联
         */
        controller.setMediaPlayer(videoview);
    }
}
