package cn.biiduu.biiduu.module.article;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marno.rapidlib.enums.RxLifeEvent;

import org.greenrobot.essentials.StringUtils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.base.BaseLoadActivity;
import cn.biiduu.biiduu.constant.EnumConstants;
import cn.biiduu.biiduu.data.repository.ArticleRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.protocol.ArticleListProtocol;
import cn.biiduu.biiduu.protocol.HostArticleProtocol;
import cn.biiduu.biiduu.util.TimeUtil;

public class CoinNewspaperListActivity extends BaseLoadActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.vp_coin_newspaper)
    ViewPager vpCoinNewspaper;
    @BindView(R.id.tv_page)
    TextView tvPage;

    private List<HostArticleProtocol> mHostArticleProtocols;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CoinNewspaperListActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coin_newspaper_list;
    }

    @Override
    protected void initView() {
        ibBack.setOnClickListener(view -> onBackPressed());
        vpCoinNewspaper.setOffscreenPageLimit(3);
        vpCoinNewspaper.setPageTransformer(true, (page, position) -> {
            if (position < -1) {
                page.setAlpha(0.5f);
                page.setScaleX((float) (1 + position * 0.06));
                page.setScaleY((float) (1 + position * 0.06));
            } else if (position <= 0) {
                float factor = 0.5f + (1 - 0.5f) * (1 + position);
                page.setAlpha(factor);
                page.setScaleX((float) (1 + position * 0.06));
                page.setScaleY((float) (1 + position * 0.06));
            } else if (position <= 1) {
                float factor = 0.5f + (1 - 0.5f) * (1 - position);
                page.setAlpha(factor);
                page.setScaleX((float) (1 - position * 0.06));
                page.setScaleY((float) (1 - position * 0.06));
            } else if (position > 1) {
                page.setAlpha(0.5f);
                page.setScaleX((float) (1 - position * 0.06));
                page.setScaleY((float) (1 - position * 0.06));
            }
        });
        vpCoinNewspaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mHostArticleProtocols != null) {
                    String title = mHostArticleProtocols.get(position).getTitle();
                    if (title.contains("——")) {
                        tvPeriod.setText(title.substring(0, title.indexOf("——")));
                    }
                }
                tvPage.setText(String.format(Locale.CHINA, "%d/%d", position + 1,
                        vpCoinNewspaper.getAdapter().getCount()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void loadData() {
        ArticleRepository.getInstance().listArticle(EnumConstants.CLASSIFY_COIN_NEWSPAPER, 0, 100, null)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<ArticleListProtocol>() {
                    @Override
                    public void _onNext(ArticleListProtocol entity) {
                        if (entity == null || entity.getRows() == null || entity.getRows().isEmpty()) {
                            esvStatus.empty();
                        } else {
                            mHostArticleProtocols = entity.getRows();
                            vpCoinNewspaper.setAdapter(new PagerAdapter() {
                                @Override
                                public int getCount() {
                                    return entity.getRows().size();
                                }

                                @Override
                                public boolean isViewFromObject(View view, Object o) {
                                    return view == o;
                                }

                                @Override
                                public Object instantiateItem(ViewGroup container, int position) {
                                    LinearLayout itemView = (LinearLayout) getLayoutInflater()
                                            .inflate(R.layout.item_coin_newspaper, container, false);
                                    itemView.setOnClickListener(v -> {
                                        HostArticleProtocol hostArticleProtocol = entity.getRows().get(position);
                                        List<String> classify = hostArticleProtocol.getClassify();
                                        if (classify != null && !classify.isEmpty()) {
                                            startActivity(ArticleDetailsActivity.getStartIntent(CoinNewspaperListActivity.this,
                                                    hostArticleProtocol.getId(), classify.get(0)));
                                        }
                                    });
                                    ImageView ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
                                    TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                                    TextView tvSummary = (TextView) itemView.findViewById(R.id.tv_summary);
                                    TextView tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
                                    TextView tvBrowserCount = (TextView) itemView.findViewById(R.id.tv_browser_count);
                                    TextView tvShareCount = (TextView) itemView.findViewById(R.id.tv_share_count);
                                    TextView tvTime = (TextView) itemView.findViewById(R.id.tv_time);
                                    HostArticleProtocol hostArticleProtocol = entity.getRows().get(position);
                                    Glide.with(ivImage.getContext())
                                            .load(hostArticleProtocol.getCover())
                                            .error(R.drawable.img_def_banner_2)
                                            .placeholder(R.drawable.img_def_banner_2)
                                            .centerCrop()
                                            .dontAnimate()
                                            .into(ivImage);
                                    tvTitle.setText(hostArticleProtocol.getTitle().substring(hostArticleProtocol.getTitle().indexOf("——") + 2));
                                    tvSummary.setText(hostArticleProtocol.getSummary());
                                    tvLabel.setText(hostArticleProtocol.getTags() == null ? "" : StringUtils.join(hostArticleProtocol.getTags(), "·"));
                                    tvBrowserCount.setText(String.valueOf(hostArticleProtocol.getRank()));
                                    tvShareCount.setText(String.valueOf(hostArticleProtocol.getShareTimes()));
                                    tvTime.setText(TimeUtil.formatTime(hostArticleProtocol.getPublishTime(), "MM.dd"));
                                    container.addView(itemView);
                                    return itemView;
                                }

                                @Override
                                public void destroyItem(ViewGroup container, int position, Object object) {
                                    container.removeView((View) object);
                                }
                            });
                            if (mHostArticleProtocols != null) {
                                String title = mHostArticleProtocols.get(0).getTitle();
                                if (title.contains("——")) {
                                    tvPeriod.setText(title.substring(0, title.indexOf("——")));
                                }
                            }
                            tvPage.setText(String.format(Locale.CHINA, "%d/%d", 1,
                                    vpCoinNewspaper.getAdapter().getCount()));
                            esvStatus.content();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        esvStatus.noNet();
                    }
                });
    }
}
