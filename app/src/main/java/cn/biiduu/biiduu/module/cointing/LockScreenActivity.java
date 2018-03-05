package cn.biiduu.biiduu.module.cointing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marno.rapidlib.basic.BasicActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.module.cointing.service.MediaPlayerService;
import cn.biiduu.biiduu.util.TimeUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class LockScreenActivity extends BasicActivity implements MediaPlayerService.OnMediaPlayerListener {
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_stop_time)
    TextView tvStopTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_play)
    ImageButton ibPlay;

    private MediaPlayer mMediaPlayer;
    private boolean isTracking;
    private PlayerTask mPlayerTask = new PlayerTask();
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMediaPlayerService = ((MediaPlayerService.MediaPlayerBinder) service).getService();
            mMediaPlayer = mMediaPlayerService.getMediaPlayer();
            mMediaPlayerService.addOnMediaPlayerListener(LockScreenActivity.this);
            sbProgress.setMax(mMediaPlayer.getDuration());
            tvStopTime.setText(TimeUtil.formatTime(mMediaPlayer.getDuration(), "mm:ss"));
            sbProgress.setProgress(mMediaPlayer.getCurrentPosition());
            if (mMediaPlayer.isPlaying()) {
                ibPlay.setImageResource(R.drawable.play_ic_pause);
            } else {
                ibPlay.setImageResource(R.drawable.play_ic_playing);
            }
            sbProgress.postDelayed(mPlayerTask, 100);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private MediaPlayerService mMediaPlayerService;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LockScreenActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_lock_screen;
    }

    @Override
    protected void beforeSetView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvStartTime.setText(TimeUtil.formatTime(progress, "mm:ss"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(sbProgress.getProgress());
                isTracking = false;
            }
        });
        Glide.with(LockScreenActivity.this)
                .load(getIntent().getStringExtra("cover_url"))
                .dontAnimate()
                .centerCrop()
                .bitmapTransform(new BlurTransformation(this, 23, 4))
                .into(ivBackground);
        Glide.with(LockScreenActivity.this)
                .load(getIntent().getStringExtra("cover_url"))
                .centerCrop()
                .dontAnimate()
                .into(ivCover);
        tvTitle.setText(getIntent().getStringExtra("title"));
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.ib_play)
    public void onClick(View v) {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayerService.pause();
                ibPlay.setImageResource(R.drawable.play_ic_playing);
            } else {
                mMediaPlayerService.start();
                ibPlay.setImageResource(R.drawable.play_ic_pause);
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    private class PlayerTask implements Runnable {
        @Override
        public void run() {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying() && !isTracking) {
                sbProgress.setProgress(mMediaPlayer.getCurrentPosition());
            }
            sbProgress.postDelayed(mPlayerTask, 100);
        }
    }

    @Override
    protected void onDestroy() {
        sbProgress.removeCallbacks(mPlayerTask);
        unbindService(connection);
        super.onDestroy();
    }
}
