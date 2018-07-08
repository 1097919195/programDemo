package example.com.normal;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, Object>> mVideo;
    Gallery mGallery;
    VideoAdapter videoAdapter;
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goIntent = (Button) findViewById(R.id.goIntent);
        Button goLibrary = (Button) findViewById(R.id.goLibrary);
        Button dialog1 = (Button) findViewById(R.id.dialog1);
        Button dialog2 = (Button) findViewById(R.id.dialog2);
        //权限发送
        permissionApply(Manifest.permission.READ_EXTERNAL_STORAGE);

//        getVideoInfo();
//
//        initView();
//
//        setOnListener();

        goIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction("android.intent.action.VIEW");
                Uri data = Uri.parse("http://www.baidu.com");
                i.setData(data);
                startActivity(i);

            }
        });
        goLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction("android.intent.action.act");
                startActivity(i);
            }
        });

        dialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyDialog(1);
            }
        });
        dialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyDialog(2);
            }
        });

//        //为按钮绑定上下文菜单（注意不是绑定监听器）
//        registerForContextMenu(btn);
    }

    private void showMyDialog(int num) {
        if (num == 1) {
            View layout = View.inflate(MainActivity.this, R.layout.dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(layout);
            final AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //    设置Title的图标
            builder.setIcon(R.mipmap.ic_launcher);
            //    设置Title的内容
            builder.setTitle("弹出警告框");
            //    设置Content来显示一个信息
            builder.setMessage("确定删除吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "positive: " + which, Toast.LENGTH_SHORT).show();
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "negative: " + which, Toast.LENGTH_SHORT).show();
                }
            });
            //    设置一个NeutralButton
            builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "neutral: " + which, Toast.LENGTH_SHORT).show();
                }
            });
            //    显示出该对话框
            builder.show();
        }


    }

    private void setOnListener() {
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HashMap<String, Object> video = mVideo.get(i);
                String path = video.get(MediaStore.Video.Media.DATA).toString();
                mVideoView.setVideoPath(path);
                mVideoView.start();
            }
        });
    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.vv);
        mGallery = (Gallery) findViewById(R.id.gallery);
        videoAdapter = new VideoAdapter(mVideo, this);
        mGallery.setAdapter(videoAdapter);
    }

    private void getVideoInfo() {
        //准备ContentReslover
        ContentResolver resolver = getContentResolver();
        //准备操作数据或条件
        String[] projection = {
                //获取视频的缩略图
                MediaStore.Video.Media._ID,
                //视频文件路径
                MediaStore.Video.Media.DATA,
                //标题
                MediaStore.Video.Media.TITLE
        };
        mVideo = new ArrayList<HashMap<String, Object>>();
        Cursor c = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        while (c.moveToNext()) {
            HashMap<String, Object> video = new HashMap<String, Object>();
            video.put(MediaStore.Video.Media._ID,c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID)));
            video.put(MediaStore.Video.Media.DATA,c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
            video.put(MediaStore.Video.Media.TITLE,c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
            mVideo.add(video);

        }
    }

    class VideoAdapter extends BaseAdapter {
        ArrayList<HashMap<String, Object>> videos;
        Context context;

        public VideoAdapter(ArrayList<HashMap<String, Object>> videos, Context context) {
            this.videos = videos;
            this.context = context;
        }

        @Override
        public int getCount() {
            return videos.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.item_video, null);
            ImageView image = convertView.findViewById(R.id.image);
            TextView text = convertView.findViewById(R.id.text);
            HashMap<String, Object> video = videos.get(i);
            text.setText(video.get(MediaStore.Video.Media.TITLE).toString());
            int id = (Integer) video.get(MediaStore.Video.Media._ID);
            Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
            image.setImageBitmap(bitmap);
            return convertView;
        }
    }


    //权限管理
    private void permissionApply(String PERMISSIONS) {
        //相机权限申请
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions.request(PERMISSIONS)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            Toast.makeText(MainActivity.this, "存储权限已同意", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "权限已被拒绝", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 加载菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
