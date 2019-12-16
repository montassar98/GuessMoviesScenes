package com.montassar.guessmoviesscenes.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.montassar.guessmoviesscenes.MainActivity;
import com.montassar.guessmoviesscenes.R;

public class PlayingVideoFragment extends Fragment {

    private VideoView videoView;
    private int videoId;
    boolean shouldStop = false;
    Handler mHandler;
    Runnable mRunnable;

    public PlayingVideoFragment(int videoId) {
        this.videoId = videoId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playing_video, container, false);


        videoView = (VideoView) view.findViewById(R.id.video_view);
       /* videoView.setVideoPath("file:///android_asset/videos/test.mp4");
        videoView.start();*/
        String path = "android.resource://" + getActivity().getPackageName() + "/" + videoId;
        Toast.makeText(getContext(), "" + R.raw.test, Toast.LENGTH_SHORT).show();
        // videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.requestFocus();
                //videoView.seekTo(MainActivity.videoTime);
                videoView.start();
                //


            }
        });
        videoView.setVideoURI(Uri.parse(path));
        //progress();
        trackProgress();

        return view;
    }
   /* void progress() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            public void run() {
                int currentPostion = videoView.getCurrentPosition();
                if(currentPostion >= 10 * 1000 || currentPostion == videoView.getDuration()) {
                   videoView.stopPlayback();
                }
                mHandler.postDelayed(this, 250);
            }
        };
        mHandler.post(mRunnable);
    }*/

    private void trackProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shouldStop) {
                    if (videoView != null && videoView.isPlaying()) {
                        if (videoView.getCurrentPosition() >= MainActivity.videoTime) {
                            videoView.stopPlayback();
                            shouldStop = true;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
