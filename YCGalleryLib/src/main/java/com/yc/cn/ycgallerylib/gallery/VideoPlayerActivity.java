package com.yc.cn.ycgallerylib.gallery;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yc.cn.ycgallerylib.R;
import com.yc.cn.ycgallerylib.gallery.model.Constants;


public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getExtras().getString(Constants.URL);
        setContentView(R.layout.fragment_video_gallery);
        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                View progress = findViewById(R.id.videoProgress);
                progress.setVisibility(View.GONE);

                videoView.requestFocus();
                MediaController vidControl = new MediaController(VideoPlayerActivity.this);
                vidControl.setAnchorView(videoView);
                videoView.setMediaController(vidControl);
                videoView.start();
            }
        });
        videoView.setVideoURI(Uri.parse(url));
    }
}
