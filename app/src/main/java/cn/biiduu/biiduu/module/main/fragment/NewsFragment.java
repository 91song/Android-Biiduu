package cn.biiduu.biiduu.module.main.fragment;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.marno.rapidlib.enums.RxLifeEvent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.ArticleNewsAdapter;
import cn.biiduu.biiduu.base.BaseRefreshFragment;
import cn.biiduu.biiduu.constant.EnumConstants;
import cn.biiduu.biiduu.data.repository.ArticleRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.article.ArticleDetailsActivity;
import cn.biiduu.biiduu.module.article.CoinChaharListActivity;
import cn.biiduu.biiduu.module.article.CoinNewspaperListActivity;
import cn.biiduu.biiduu.protocol.ArticleListProtocol;
import cn.biiduu.biiduu.protocol.ArticleProtocol;
import cn.biiduu.biiduu.protocol.CoinCircleListProtocol;
import cn.biiduu.biiduu.protocol.HostArticleProtocol;
import cn.biiduu.biiduu.util.ScreenUtil;
import cn.biiduu.biiduu.util.TimeUtil;
import cn.biiduu.biiduu.widget.FixedViewPager;
import cn.bingoogolapple.bgabanner.BGABanner;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.ListenerStubs;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class NewsFragment extends BaseRefreshFragment implements View.OnClickListener {
    private ArticleNewsAdapter mAdapter;
    private HostArticleProtocol mHostArticleProtocol;
    public ViewPager vpCoinChahar;
    private FixedViewPager fvpCoinChaharTime;
    private ImageView ivCoinNewspaper;
    private TextView tvPeriod;
    private TextView tvTitle;
    private BGABanner bgaBanner;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
        initHeaderView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bgaBanner != null) {
            bgaBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bgaBanner != null) {
            bgaBanner.stopAutoPlay();
        }
    }

    private void initRecyclerView() {
        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContent.setHasFixedSize(true);
        rvContent.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext())
                        .color(Color.parseColor("#F7F7F7"))
                        .sizeResId(R.dimen.divider_1)
                        .visibilityProvider((position, parent) -> position == 0)
                        .build());
        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoinCircleListProtocol.RowsBean rowsBean = (CoinCircleListProtocol.RowsBean) adapter.getData().get(position);
                startActivity(ArticleDetailsActivity.getStartIntent(getContext(),
                        rowsBean.getId(), EnumConstants.CLASSIFY_COIN_RING));
            }
        });
    }

    private void initHeaderView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_news_header,
                (ViewGroup) rvContent.getParent(), false);
        bgaBanner = (BGABanner) headerView.findViewById(R.id.bga_banner);
        bgaBanner.setAdapter((banner, itemView, model, position) -> {
            ImageView ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            Glide.with(ivImage.getContext())
                    .load(((ArticleProtocol) model).getArticle().getCover())
                    .error(R.drawable.img_def_banner_1)
                    .placeholder(R.drawable.img_def_banner_1)
                    .centerCrop()
                    .dontAnimate()
                    .into(ivImage);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTitle.setText(((ArticleProtocol) model).getArticle().getTitle());
        });
        bgaBanner.setDelegate((banner, itemView, model, position) -> {
            ArticleProtocol.ArticleBean articleBean = ((ArticleProtocol) model).getArticle();
            List<String> classify = articleBean.getClassify();
            if (classify != null && !classify.isEmpty()) {
                startActivity(ArticleDetailsActivity.getStartIntent(getContext(),
                        articleBean.getId(), classify.get(0)));
            }
        });
        headerView.findViewById(R.id.ll_coin_newspaper).setOnClickListener(this);
        headerView.findViewById(R.id.iv_coin_newspaper).setOnClickListener(this);
        ivCoinNewspaper = (ImageView) headerView.findViewById(R.id.iv_coin_newspaper);
        tvPeriod = (TextView) headerView.findViewById(R.id.tv_period);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        fvpCoinChaharTime = (FixedViewPager) headerView.findViewById(R.id.fvp_coin_chahar_time);
        fvpCoinChaharTime.setPagingEnabled(false);
        fvpCoinChaharTime.setOffscreenPageLimit(3);
        fvpCoinChaharTime.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fvpCoinChaharTime.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TextView tvMore = (TextView) headerView.findViewById(R.id.tv_more);
        vpCoinChahar = (ViewPager) headerView.findViewById(R.id.vp_coin_chahar);
        vpCoinChahar.setPageMargin(ScreenUtil.dp2px(10));
        vpCoinChahar.setOffscreenPageLimit(3);
        vpCoinChahar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (positionOffsetPixels > 0) {
//                    fvpCoinChaharTime.scrollTo(fvpCoinChaharTime.getWidth() * (position + 1)
//                            + positionOffsetPixels * fvpCoinChaharTime.getWidth() / vpCoinChahar.getWidth(), 0);
//                }
            }

            @Override
            public void onPageSelected(int position) {
                fvpCoinChaharTime.setCurrentItem(position + 1, true);
                if (position == vpCoinChahar.getAdapter().getCount() - 1) {
                    tvMore.setVisibility(View.VISIBLE);
                } else {
                    tvMore.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(vpCoinChahar);
        final boolean[] isLoadMore = {false};
        decor.setOverScrollUpdateListener(new ListenerStubs.OverScrollUpdateListenerStub() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                if (offset < 0 && state == 2) {
                    if (offset < -60) {
                        tvMore.setText("释放查看");
                        isLoadMore[0] = true;
                    } else {
                        tvMore.setText("更多");
                        isLoadMore[0] = false;
                    }
                }
                if (offset == 0 && state == 3 && isLoadMore[0]) {
                    isLoadMore[0] = false;
                    startActivity(CoinChaharListActivity.getStartIntent(getContext()));
                }
            }
        });
        mAdapter = new ArticleNewsAdapter();
        mAdapter.bindToRecyclerView(rvContent);
        mAdapter.setEmptyView(R.layout.layout_status_loading);
        mAdapter.addHeaderView(headerView);
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void loadData2() {
        ArticleRepository.getInstance().listCoinCircle(0, 100)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinCircleListProtocol>() {
                    @Override
                    public void _onNext(CoinCircleListProtocol entity) {
                        pflRefresh.refreshComplete();
                        if (entity == null || entity.getRows() == null || entity.getRows().isEmpty()) {
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            mAdapter.setNewData(entity.getRows());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        pflRefresh.refreshComplete();
                        mAdapter.setNewData(null);
                        mAdapter.setEmptyView(mErrorView);
                    }
                });
        ArticleRepository.getInstance().listTopArticle(EnumConstants.CLASSIFY_HOME_PAGE)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<List<ArticleProtocol>>() {
                    @Override
                    public void _onNext(List<ArticleProtocol> entity) {
                        if (entity != null && !entity.isEmpty()) {
                            if (entity.size() > 3) {
                                entity = entity.subList(3, entity.size());
                            }
                            bgaBanner.setData(R.layout.item_banner_news, entity, null);
                            bgaBanner.getViewPager().setClipChildren(false);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                                    .LayoutParams(bgaBanner.getViewPager().getLayoutParams());
                            layoutParams.setMargins(ScreenUtil.dp2px(20), ScreenUtil.dp2px(17),
                                    ScreenUtil.dp2px(20), ScreenUtil.dp2px(20));
                            bgaBanner.getViewPager().setLayoutParams(layoutParams);
                            bgaBanner.getViewPager().setPageTransformer(true, (page, position) -> {
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
                        }
                    }
                });
        ArticleRepository.getInstance().listArticle(EnumConstants.CLASSIFY_COIN_NEWSPAPER, 0, 1, null)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<ArticleListProtocol>() {
                    @Override
                    public void _onNext(ArticleListProtocol entity) {
                        if (entity != null && entity.getRows() != null && !entity.getRows().isEmpty()) {
                            mHostArticleProtocol = entity.getRows().get(0);
                            Glide.with(getContext())
                                    .load(mHostArticleProtocol.getCover())
                                    .error(R.drawable.img_def_banner_1)
                                    .placeholder(R.drawable.img_def_banner_1)
                                    .centerCrop()
                                    .dontAnimate()
                                    .into(ivCoinNewspaper);
                            if (mHostArticleProtocol.getTitle().contains("——")) {
                                tvPeriod.setText(mHostArticleProtocol.getTitle().substring(0, mHostArticleProtocol.getTitle().indexOf("——")));
                                tvTitle.setText(mHostArticleProtocol.getTitle().substring(mHostArticleProtocol.getTitle().indexOf("——") + 2));
                            }
                        }
                    }
                });
        ArticleRepository.getInstance().listTopArticle(EnumConstants.CLASSIFY_COIN_CHAHAR)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<List<ArticleProtocol>>() {
                    @Override
                    public void _onNext(List<ArticleProtocol> entity) {
                        if (entity != null && !entity.isEmpty()) {
                            if (entity.size() > 3) {
                                entity = entity.subList(0, 3);
                            }
                            List<String> times = new ArrayList<>();
                            times.add("敬请期待");
                            for (ArticleProtocol article : entity) {
                                if (article.getArticle() != null) {
                                    times.add(TimeUtil.getPastedTimeDesc(article.getArticle().getPublishTime()));
                                }
                            }
                            times.add("查看更多");
                            fvpCoinChaharTime.setAdapter(new CoinChaharTimePagerAdapter(times));
                            fvpCoinChaharTime.setCurrentItem(1);
                            vpCoinChahar.setAdapter(new CoinChaharPagerAdapter(entity));
                        }
                    }
                });
    }

    private class CoinChaharPagerAdapter extends PagerAdapter {
        private List<ArticleProtocol> data = new ArrayList<>();

        CoinChaharPagerAdapter(List<ArticleProtocol> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(getContext())
                    .inflate(R.layout.item_coin_chahar_top, container, false);
            itemView.setOnClickListener(v -> {
                ArticleProtocol articleProtocol = data.get(position);
                ArticleProtocol.ArticleBean articleBean = articleProtocol.getArticle();
                List<String> classify = articleBean.getClassify();
                if (classify != null && !classify.isEmpty()) {
                    startActivity(ArticleDetailsActivity.getStartIntent(getContext(),
                            articleBean.getId(), classify.get(0)));
                }
            });
            ImageView ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ArticleProtocol.ArticleBean articleBean = data.get(position).getArticle();
            if (articleBean != null) {
                Glide.with(getContext())
                        .load(articleBean.getCover())
                        .error(R.drawable.img_def_banner_1)
                        .placeholder(R.drawable.img_def_banner_1)
                        .centerCrop()
                        .dontAnimate()
                        .into(ivImage);
                tvTitle.setText(articleBean.getTitle());
            }
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class CoinChaharTimePagerAdapter extends PagerAdapter {
        private List<String> data = new ArrayList<>();

        CoinChaharTimePagerAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(getContext())
                    .inflate(R.layout.item_coin_chahar_time, container, false);
            itemView.setTag(position);
            TextView tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvTime.setText(data.get(position));
            container.addView(itemView);
            getItemPosition(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            View view = (View) object;
            int position = (int) ((View) object).getTag();
            TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
            ImageView ivCircle = (ImageView) view.findViewById(R.id.iv_circle);
            if (position == fvpCoinChaharTime.getCurrentItem()) {
                ivCircle.setImageResource(R.drawable.shape_circle_3);
                tvTime.setTextColor(Color.parseColor("#578DDF"));
            } else {
                ivCircle.setImageResource(R.drawable.shape_circle);
                tvTime.setTextColor(Color.parseColor("#999999"));
            }
            return super.getItemPosition(object);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_coin_newspaper:
                startActivity(CoinNewspaperListActivity.getStartIntent(getContext()));
                break;
            case R.id.iv_coin_newspaper:
                if (mHostArticleProtocol != null) {
                    List<String> classify = mHostArticleProtocol.getClassify();
                    if (classify != null && !classify.isEmpty()) {
                        startActivity(ArticleDetailsActivity.getStartIntent(getContext(),
                                mHostArticleProtocol.getId(), classify.get(0)));
                    }
                }
                break;
            default:
                break;
        }
    }
}
