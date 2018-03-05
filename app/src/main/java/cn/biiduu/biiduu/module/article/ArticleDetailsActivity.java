package cn.biiduu.biiduu.module.article;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marno.rapidlib.enums.RxLifeEvent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.essentials.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.biiduu.biiduu.BuildConfig;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.ArticleRecommendReadAdapter;
import cn.biiduu.biiduu.base.BaseLoadActivity;
import cn.biiduu.biiduu.constant.EnumConstants;
import cn.biiduu.biiduu.data.repository.ArticleRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.article.fragment.ShareDialogFragment;
import cn.biiduu.biiduu.protocol.ArticleDetailsProtocol;
import cn.biiduu.biiduu.protocol.ArticleProtocol;
import cn.biiduu.biiduu.protocol.CoinCircleDetailsProtocol;
import cn.biiduu.biiduu.util.AppUtil;
import cn.biiduu.biiduu.util.ShareManager;
import cn.biiduu.biiduu.util.TimeUtil;

public class ArticleDetailsActivity extends BaseLoadActivity implements ShareDialogFragment.OnShareClickListener, UMShareListener {
    @BindView(R.id.tv_classify)
    TextView tvClassify;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.tv_browser_count)
    TextView tvBrowserCount;
    @BindView(R.id.tv_share_count)
    TextView tvShareCount;
    @BindView(R.id.tv_src)
    TextView tvSrc;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.ll_recommend_read)
    LinearLayout llRecommendRead;

    private ArticleDetailsProtocol mArticleDetailsProtocol;
    private CoinCircleDetailsProtocol mCoinCircleDetailsProtocol;
    private ArticleRecommendReadAdapter mAdapter;

    public static Intent getStartIntent(Context context, String articleId, String classify) {
        Intent intent = new Intent(context, ArticleDetailsActivity.class);
        intent.putExtra("articleId", articleId);
        intent.putExtra("classify", classify);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void initView() {
        tvClassify.setText(AppUtil.getChineseClassify(getIntent().getStringExtra("classify")));
        initWebView();
        initRecyclerView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = wvContent.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setTextZoom(100);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                esvStatus.content();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                esvStatus.noNet();
            }
        });
    }

    private void initRecyclerView() {
        rvContent.setNestedScrollingEnabled(false);
        rvContent.setHasFixedSize(true);
        rvContent.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#F7F7F7"))
                        .sizeResId(R.dimen.divider_1)
                        .build());
        mAdapter = new ArticleRecommendReadAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ArticleProtocol.ArticleBean articleBean = (ArticleProtocol.ArticleBean) adapter.getData().get(position);
            List<String> classify = articleBean.getClassify();
            if (classify != null && !classify.isEmpty()) {
                startActivity(ArticleDetailsActivity.getStartIntent(ArticleDetailsActivity.this,
                        articleBean.getId(), classify.get(0)));
            }
        });
        rvContent.setAdapter(mAdapter);
    }

    @OnClick({R.id.ib_back, R.id.ib_share})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                onBackPressed();
                break;
            case R.id.ib_share:
                new ShareDialogFragment(this).show(getSupportFragmentManager(), "share");
                break;
            default:
                break;
        }
    }

    @Override
    protected void loadData() {
        String articleId = getIntent().getStringExtra("articleId");
        String classify = getIntent().getStringExtra("classify");
        if (EnumConstants.CLASSIFY_COIN_RING.equals(classify)) {
            ArticleRepository.getInstance().getCoinCircleDetails(articleId)
                    .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                    .subscribe(new DefaultSubscriber<CoinCircleDetailsProtocol>() {
                        @Override
                        public void _onNext(CoinCircleDetailsProtocol entity) {
                            if (entity == null) {
                                esvStatus.empty();
                            } else {
                                mCoinCircleDetailsProtocol = entity;
                                tvTitle.setText(entity.getTitle());
                                tvLabel.setText(entity.getTags() == null ? "" : StringUtils.join(entity.getTags(), "·"));
                                tvBrowserCount.setText(String.valueOf(0));
                                tvShareCount.setText(String.valueOf(0));
                                if ("原创".equals(entity.getAuthor())) {
                                    tvSrc.setText("本文原创首发：币都");
                                }
                                tvTime.setText(TimeUtil.getPastedTimeDesc(entity.getReleaseTime().getTime()));
                                wvContent.loadData(getHtmlData(entity.getDes()), "text/html; charset=UTF-8", null);
                            }
                        }
                    });
        } else {
            ArticleRepository.getInstance().getArticleDetails(articleId)
                    .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                    .subscribe(new DefaultSubscriber<ArticleDetailsProtocol>() {
                        @Override
                        public void _onNext(ArticleDetailsProtocol entity) {
                            if (entity == null) {
                                esvStatus.empty();
                            } else {
                                mArticleDetailsProtocol = entity;
                                tvTitle.setText(entity.getTitle());
                                tvLabel.setText(entity.getTags() == null ? "" : StringUtils.join(entity.getTags(), "·"));
                                tvBrowserCount.setText(String.valueOf(entity.getRank()));
                                tvShareCount.setText(String.valueOf(entity.getShareTimes()));
                                if (EnumConstants.CLASSIFY_COIN_NEWSPAPER.equals(classify)) {
                                    Glide.with(ArticleDetailsActivity.this)
                                            .load(entity.getCover())
                                            .error(R.drawable.img_def_banner_1)
                                            .placeholder(R.drawable.img_def_banner_1)
                                            .centerCrop()
                                            .dontAnimate()
                                            .into(ivCover);
                                    ivCover.setVisibility(View.VISIBLE);
                                }
                                if ("原创".equals(entity.getSource())) {
                                    tvSrc.setText("本文原创首发：币都");
                                }
                                tvTime.setText(TimeUtil.getPastedTimeDesc(entity.getPublishTime()));
                                wvContent.loadData(getHtmlData(entity.getContent()), "text/html; charset=UTF-8", null);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            esvStatus.noNet();
                        }
                    });
        }
        ArticleRepository.getInstance().listRecommendReadArticle(articleId, 0, 3)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<List<ArticleProtocol.ArticleBean>>() {
                    @Override
                    public void _onNext(List<ArticleProtocol.ArticleBean> entity) {
                        if (entity != null && !entity.isEmpty()) {
                            llRecommendRead.setVisibility(View.VISIBLE);
                            mAdapter.setNewData(entity);
                        }
                    }
                });

    }

    private String getHtmlData(String body) {
        String head = "<head><style>img{max-width: 100%; width: auto; height: auto;}body{text-align: justify; font-size: " + 16 + "px; line-height: " + 28 + "px}</style></head>";
        return "<html>" + head + "<body>" + body + "</body></html>";
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
        String type = getIntent().getStringExtra("classify");
        if (EnumConstants.CLASSIFY_COIN_RING.equals(type)) {
            type = "2";
        } else {
            type = "1";
        }
        if (mArticleDetailsProtocol != null) {
            ShareManager.ShareBean shareBean = new ShareManager.ShareBean(new UMImage(this, mArticleDetailsProtocol.getCover()));
            shareBean.setPlatform(share_media);
            shareBean.setTitle(mArticleDetailsProtocol.getTitle());
            shareBean.setText(mArticleDetailsProtocol.getSummary());
            shareBean.setTargetUrl(BuildConfig.BASE_STATIC_URL + "static/share/sharepage.html?id=" + mArticleDetailsProtocol.getId() + "&class=" + type);
            ShareManager.getInstance().shareLink(this, shareBean, this);
        } else if (mCoinCircleDetailsProtocol != null) {
            ShareManager.ShareBean shareBean = new ShareManager.ShareBean(new UMImage(this, mCoinCircleDetailsProtocol.getBanner()));
            shareBean.setPlatform(share_media);
            shareBean.setTitle(mCoinCircleDetailsProtocol.getTitle());
            shareBean.setText(mCoinCircleDetailsProtocol.getTitle());
            shareBean.setTargetUrl(BuildConfig.BASE_STATIC_URL + "#/biCircle");
            ShareManager.getInstance().shareLink(this, shareBean, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
    protected void onResume() {
        super.onResume();
        if (wvContent != null) {
            wvContent.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (wvContent != null) {
            wvContent.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (wvContent != null) {
            wvContent.stopLoading();
            wvContent.destroy();
            wvContent = null;
        }
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
