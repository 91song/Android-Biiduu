package cn.biiduu.biiduu.module.cointing.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.biiduu.biiduu.module.cointing.LockScreenActivity;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private MediaPlayerBinder mBinder = new MediaPlayerBinder();
    private List<OnMediaPlayerListener> mListeners = new ArrayList<>();
    private String mCoverUrl;
    private String mTitle;

    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                Intent lockIntent = new Intent(context, LockScreenActivity.class);
                lockIntent.putExtra("cover_url", mCoverUrl);
                lockIntent.putExtra("title", mTitle);
                lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(lockIntent);
            }
        }
    };

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void addOnMediaPlayerListener(OnMediaPlayerListener listener) {
        mListeners.add(listener);
    }

    public void removeOnMediaPlayerListener(OnMediaPlayerListener listener) {
        mListeners.remove(listener);
    }

    public void removeAllOnMediaPlayerListener() {
        mListeners.clear();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenOffReceiver, intentFilter);
    }

    private void initMediaPlayer() {
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
    }

    public void prepare(String url, String coverUrl, String title) {
        mCoverUrl = coverUrl;
        mTitle = title;
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        for (OnMediaPlayerListener listener : mListeners) {
            listener.onCompletion(mp);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        for (OnMediaPlayerListener listener : mListeners) {
            listener.onError(mp, what, extra);
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        for (OnMediaPlayerListener listener : mListeners) {
            listener.onPrepared(mp);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        for (OnMediaPlayerListener listener : mListeners) {
            listener.onBufferingUpdate(mp, percent);
        }
    }

    @Override
    public void onDestroy() {
        removeAllOnMediaPlayerListener();
        release();
        super.onDestroy();
    }

    public class MediaPlayerBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    public interface OnMediaPlayerListener {
        void onPrepared(MediaPlayer mp);

        void onCompletion(MediaPlayer mp);

        boolean onError(MediaPlayer mp, int what, int extra);

        void onBufferingUpdate(MediaPlayer mp, int percent);
    }
}
