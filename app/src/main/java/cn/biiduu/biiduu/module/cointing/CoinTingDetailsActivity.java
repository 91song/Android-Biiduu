package cn.biiduu.biiduu.module.cointing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marno.rapidlib.enums.RxLifeEvent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMusic;

import org.greenrobot.essentials.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.biiduu.biiduu.BuildConfig;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.base.BaseLoadActivity;
import cn.biiduu.biiduu.data.repository.CoinTingRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.article.fragment.ShareDialogFragment;
import cn.biiduu.biiduu.module.cointing.fragment.FeedbackDialogFragment;
import cn.biiduu.biiduu.module.cointing.service.MediaPlayerService;
import cn.biiduu.biiduu.protocol.CoinTingDetailsProtocol;
import cn.biiduu.biiduu.util.ShareManager;
import cn.biiduu.biiduu.util.TimeUtil;

public class CoinTingDetailsActivity extends BaseLoadActivity implements ShareDialogFragment
        .OnShareClickListener, UMShareListener, FeedbackDialogFragment.OnCommitClickListener,
        MediaPlayerService.OnMediaPlayerListener {
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;
    @BindView(R.id.tv_stop_time)
    TextView tvStopTime;
    @BindView(R.id.tv_play_count)
    TextView tvPlayCount;
    @BindView(R.id.tv_publish_time)
    TextView tvPublishTime;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.tv_summary)
    TextView tvSummary;

    //    private ObjectAnimator mAnimator;
    private AnimationDrawable mAnimationDrawable;
    private MediaPlayer mMediaPlayer;
    private PlayerTask mPlayerTask = new PlayerTask();
    private boolean isTracking;
    private CoinTingDetailsProtocol mCoinTingDetailsProtocol;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMediaPlayerService = ((MediaPlayerService.MediaPlayerBinder) service).getService();
            mMediaPlayer = mMediaPlayerService.getMediaPlayer();
            mMediaPlayerService.addOnMediaPlayerListener(CoinTingDetailsActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private MediaPlayerService mMediaPlayerService;

    public static Intent getStartIntent(Context context, String id) {
        Intent intent = new Intent(context, CoinTingDetailsActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    @Override
    protected void initView() {
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
//        mAnimator = ObjectAnimator.ofFloat(ivPlay, "rotation", 0, 360);
//        mAnimator.setRepeatCount(ObjectAnimator.INFINITE);
//        mAnimator.setInterpolator(new LinearInterpolator());
//        mAnimator.setDuration(600);
//        mAnimator.start();
        mAnimationDrawable = (AnimationDrawable) ivPlay.getDrawable();
        mAnimationDrawable.start();
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void loadData() {
        CoinTingRepository.getInstance().getCoinTingDetails(getIntent().getStringExtra("id"))
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinTingDetailsProtocol>() {
                    @Override
                    public void _onNext(CoinTingDetailsProtocol entity) {
                        if (entity == null) {
                            esvStatus.empty();
                        } else {
                            mCoinTingDetailsProtocol = entity;
                            tvTitle.setText(entity.getTitle());
                            Glide.with(CoinTingDetailsActivity.this)
                                    .load(entity.getCover())
                                    .error(R.drawable.img_def_banner_1)
                                    .placeholder(R.drawable.img_def_banner_1)
                                    .centerCrop()
                                    .dontAnimate()
                                    .into(ivCover);
                            tvPlayCount.setText(getString(R.string.coin_ting_details_play_count, String.valueOf(entity.getView())));
                            tvPublishTime.setText(TimeUtil.getTimeToDay(entity.getPublishTime()));
                            tvLabel.setText(entity.getTags() == null ? "" : StringUtils.join(entity.getTags(), "·"));
                            tvSummary.setText(Html.fromHtml(entity.getSummary()));
                            mMediaPlayerService.prepare(entity.getMedia(), entity.getCover(), entity.getTitle());
                            esvStatus.content();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        esvStatus.noNet();
                    }
                });
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

    @Override
    public void onPrepared(MediaPlayer mp) {
//        mAnimator.end();
        mAnimationDrawable.stop();
        ivPlay.setImageResource(R.drawable.bi_ting_ic_play_pause);
        sbProgress.setMax(mp.getDuration());
        tvStopTime.setText(TimeUtil.formatTime(mp.getDuration(), "mm:ss"));
        sbProgress.postDelayed(mPlayerTask, 100);
        mMediaPlayerService.start();
        CoinTingRepository.getInstance().updateCoinTingPlayCount(getIntent().getStringExtra("id"))
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinTingDetailsProtocol>() {
                    @Override
                    public void _onNext(CoinTingDetailsProtocol entity) {

                    }
                });
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }

    @Override
    public void onCommitClick(String content) {
        CoinTingRepository.getInstance().addCoinTingFeedback(getIntent().getStringExtra("id"), content)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinTingDetailsProtocol>() {
                    @Override
                    public void _onNext(CoinTingDetailsProtocol entity) {
                        Toast.makeText(CoinTingDetailsActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class PlayerTask implements Runnable {
        @Override
        public void run() {
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    ivPlay.setImageResource(R.drawable.bi_ting_ic_play_pause);
                } else {
                    ivPlay.setImageResource(R.drawable.bi_ting_ic_play_def);
                }
                if (mMediaPlayer.isPlaying() && !isTracking) {
                    sbProgress.setProgress(mMediaPlayer.getCurrentPosition());
                }
            }
            sbProgress.postDelayed(mPlayerTask, 100);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coin_ting_details;
    }

    @OnClick({R.id.ib_back, R.id.ib_share, R.id.iv_play, R.id.tv_feedback})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                onBackPressed();
                break;
            case R.id.ib_share:
                new ShareDialogFragment(this).show(getSupportFragmentManager(), "share");
                break;
            case R.id.iv_play:
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayerService.pause();
                        ivPlay.setImageResource(R.drawable.bi_ting_ic_play_def);
                    } else {
                        mMediaPlayerService.start();
                        ivPlay.setImageResource(R.drawable.bi_ting_ic_play_pause);
                    }
                }
                break;
            case R.id.tv_feedback:
                new FeedbackDialogFragment(this).show(getSupportFragmentManager(), "feedback");
                break;
            default:
                break;
        }
    }

    @Override
    public void onWeiXinClick() {
        share(SHARE_MEDIA.WEIXIN);
    }

    @Override
    public void onSinaWeiboClick() {
        share(SHARE_MEDIA.SINA);
    }

    @Override
    public void onFriendCircleClick() {
        share(SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    @Override
    public void onQQFriendClick() {
        share(SHARE_MEDIA.QQ);
    }

    @Override
    public void onQZoneClick() {
        share(SHARE_MEDIA.QZONE);
    }

    private void share(SHARE_MEDIA share_media) {
        if (mCoinTingDetailsProtocol != null) {
            UMusic music = new UMusic(mCoinTingDetailsProtocol.getMedia());
            music.setTitle(mCoinTingDetailsProtocol.getTitle());
            music.setThumb(new UMImage(this, mCoinTingDetailsProtocol.getCover()));
            if (share_media == SHARE_MEDIA.SINA) {
                music.setDescription("【" + mCoinTingDetailsProtocol.getTitle() + "】" + Html.fromHtml(mCoinTingDetailsProtocol.getSummary()).toString());
            } else {
                music.setDescription(Html.fromHtml(mCoinTingDetailsProtocol.getSummary()).toString());
            }
            music.setmTargetUrl(BuildConfig.BASE_STATIC_URL + "static/share/biTingShare.html?biListenId=" + mCoinTingDetailsProtocol.getId());
            ShareManager.ShareBean shareBean = new ShareManager.ShareBean(music);
            shareBean.setPlatform(share_media);
            ShareManager.getInstance().shareMusic(this, shareBean, this);
        }
    }

    @Override
    protected void onDestroy() {
        sbProgress.removeCallbacks(mPlayerTask);
        unbindService(connection);
        super.onDestroy();
    }
}
