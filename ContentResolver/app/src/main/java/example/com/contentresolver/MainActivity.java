package example.com.contentresolver;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 本实例，我们将通过检索SDCard上的Video信息
 * 在MediaStore中，MediaStore.Video.Media中就有Video相关信息，
 * 同时MediaStore.Video.Thumbnails中含有各个video对应的缩略图信息
 */
public class MainActivity extends ListActivity {

    private Cursor cursor;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionApply(Manifest.permission.READ_EXTERNAL_STORAGE);//权限

        init();
    }

    //权限管理
    private void permissionApply(String PERMISSIONS) {
        //权限申请
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


    private void init(){
        String[] thumbColumns = new String[]{
                //视频缩略图的文件路径
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };

        String[] mediaColumns = new String[]{
                //获取视频的文件路径
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE
        };

        //首先检索SDcard上所有的video
        cursor = this.managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

        ArrayList<VideoInfo> videoList = new ArrayList<MainActivity.VideoInfo>();

        if(cursor.moveToFirst()){
            do{
                VideoInfo info = new VideoInfo();

                info.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                info.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                info.title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));

                //获取当前Video对应的Id，然后根据该ID获取其Thumb
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String selection = MediaStore.Video.Thumbnails.VIDEO_ID +"=?";
                String[] selectionArgs = new String[]{
                        id+""
                };
                Cursor thumbCursor = this.managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);

                if(thumbCursor.moveToFirst()){
                    info.thumbPath = thumbCursor.getString(thumbCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));

                }

                //然后将其加入到videoList
                videoList.add(info);

            }while(cursor.moveToNext());//先执行do语句一次，判断while，当满足条件时在走一次，再判断。。。
        }

        //然后需要设置ListView的Adapter了，使用我们自定义的Adatper
        VideoAdapter adapter = new VideoAdapter(this, videoList);
        this.getListView().setAdapter(adapter);
    }

    static class VideoInfo{
        String filePath;
        String mimeType;
        String thumbPath;
        String title;
    }

    /**
     * 定义一个Adapter来显示缩略图和视频title信息
     * @author Administrator
     *
     */
    class VideoAdapter extends BaseAdapter{

        private Context context;
        private List<VideoInfo> videoItems;

        public VideoAdapter(Context context, List<VideoInfo> data){
            this.context = context;
            this.videoItems = data;
        }
        @Override
        public int getCount() {
            return videoItems.size();
        }
        @Override
        public Object getItem(int p) {
            return videoItems.get(p);
        }
        @Override
        public long getItemId(int p) {
            return p;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_video, null);
                holder.thumbImage = (ImageView)convertView.findViewById(R.id.image);
                holder.titleText = (TextView)convertView.findViewById(R.id.text);
                //Adapter 有个getView方法，可以使用setTag把查找的view缓存起来方便多次重用,以后可以用getTag()将这个数据取出来
                convertView.setTag(holder);
            }else{
                //Adapter 有个getView方法，可以使用setTag把查找的view缓存起来方便多次重用,以后可以用getTag()将这个数据取出来
                holder = (ViewHolder)convertView.getTag();
            }

            //设置需要显示的信息
            holder.titleText.setText(videoItems.get(position).title);
            if(videoItems.get(position).thumbPath != null){
                holder.thumbImage.setImageURI(Uri.parse(videoItems.get(position).thumbPath));
            }

            return convertView;
        }

        class ViewHolder{
            ImageView thumbImage;
            TextView titleText;
        }
    }

}
