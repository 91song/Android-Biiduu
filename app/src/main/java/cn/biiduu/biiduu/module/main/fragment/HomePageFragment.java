package cn.biiduu.biiduu.module.main.fragment;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.marno.rapidlib.enums.RxLifeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.ArticleAdapter;
import cn.biiduu.biiduu.base.BaseRefreshFragment;
import cn.biiduu.biiduu.constant.ConfigConstants;
import cn.biiduu.biiduu.constant.EnumConstants;
import cn.biiduu.biiduu.data.repository.ArticleRepository;
import cn.biiduu.biiduu.data.repository.CoinIndexRepository;
import cn.biiduu.biiduu.data.repository.CoinLevelRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.article.ArticleDetailsActivity;
import cn.biiduu.biiduu.module.coinlevel.CoinLevelDetailsActivity;
import cn.biiduu.biiduu.protocol.ArticleProtocol;
import cn.biiduu.biiduu.protocol.CoinIndexTrendProtocol;
import cn.biiduu.biiduu.protocol.CoinLevelProtocol;
import cn.biiduu.biiduu.util.AppUtil;
import cn.biiduu.biiduu.util.NumberUtil;
import cn.bingoogolapple.bgabanner.BGABanner;

public class HomePageFragment extends BaseRefreshFragment {
    private static final int COIN_LEVEL_COUNT = 3;
    private static final int COIN_INDEX_TREND_SIZE = 8;

    private List<TextView> tvCoinNames = new ArrayList<>();
    private List<TextView> tvScores = new ArrayList<>();
    private List<TextView> tvPrices = new ArrayList<>();
    private List<TextView> tvStatues = new ArrayList<>();

    private BGABanner bgaBanner;
    private LineChart lcChart;
    private ArticleAdapter mAdapter;
    private TextView tvCoinIndexTrendNewest;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home_page;
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
        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleProtocol articleProtocol = (ArticleProtocol) adapter.getData().get(position);
                List<String> classify = articleProtocol.getArticle().getClassify();
                if (classify != null && !classify.isEmpty()) {
                    startActivity(ArticleDetailsActivity.getStartIntent(getContext(),
                            articleProtocol.getArticle().getId(), classify.get(0)));
                }
            }
        });
    }

    private void initHeaderView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_page_header,
                (ViewGroup) rvContent.getParent(), false);
        for (int i = 1; i <= COIN_LEVEL_COUNT; i++) {
            TextView tvCoinName = (TextView) headerView.findViewById(getResources()
                    .getIdentifier("tv_coin_name_" + i, "id", getContext().getPackageName()));
            tvCoinNames.add(tvCoinName);
            TextView tvScore = (TextView) headerView.findViewById(getResources()
                    .getIdentifier("tv_score_" + i, "id", getContext().getPackageName()));
            tvScores.add(tvScore);
            TextView tvPrice = (TextView) headerView.findViewById(getResources()
                    .getIdentifier("tv_price_" + i, "id", getContext().getPackageName()));
            tvPrices.add(tvPrice);
            TextView tvStatus = (TextView) headerView.findViewById(getResources()
                    .getIdentifier("tv_status_" + i, "id", getContext().getPackageName()));
            tvStatues.add(tvStatus);
        }
        tvCoinIndexTrendNewest = (TextView) headerView.findViewById(R.id.tv_coin_index_newest);
        bgaBanner = (BGABanner) headerView.findViewById(R.id.bga_banner);
        bgaBanner.setAdapter((banner, itemView, model, position) -> {
            ArticleProtocol.ArticleBean articleBean = ((ArticleProtocol) model).getArticle();
            if (articleBean != null) {
                ImageView ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
                Glide.with(ivImage.getContext())
                        .load(articleBean.getCover())
                        .error(R.drawable.img_def_banner_1)
                        .placeholder(R.drawable.img_def_banner_1)
                        .centerCrop()
                        .dontAnimate()
                        .into(ivImage);
                TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                tvTitle.setText(articleBean.getTitle());
            }
        });
        bgaBanner.setDelegate((banner, itemView, model, position) -> {
            ArticleProtocol.ArticleBean articleBean = ((ArticleProtocol) model).getArticle();
            List<String> classify = articleBean.getClassify();
            if (classify != null && !classify.isEmpty()) {
                startActivity(ArticleDetailsActivity.getStartIntent(getContext(),
                        articleBean.getId(), classify.get(0)));
            }
        });
        lcChart = (LineChart) headerView.findViewById(R.id.lc_chart);
        lcChart.setDragEnabled(false);
        lcChart.setScaleEnabled(false);
        lcChart.setTouchEnabled(false);
        lcChart.setDoubleTapToZoomEnabled(false);
        lcChart.getDescription().setEnabled(false);
        lcChart.setNoDataText("没有币指走势图数据");
        lcChart.setNoDataTextColor(Color.WHITE);
        Legend legend = lcChart.getLegend();
        legend.setEnabled(false);
        lcChart.getXAxis().setEnabled(false);
        lcChart.getAxisRight().setEnabled(false);
        lcChart.getAxisLeft().setEnabled(false);
        mAdapter = new ArticleAdapter();
        mAdapter.bindToRecyclerView(rvContent);
        mAdapter.setEmptyView(R.layout.layout_status_loading);
        mAdapter.addHeaderView(headerView);
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void loadData2() {
        ArticleRepository.getInstance().listTopArticle(EnumConstants.CLASSIFY_COIN_CHAHAR)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<List<ArticleProtocol>>() {
                    @Override
                    public void _onNext(List<ArticleProtocol> entity) {
                        pflRefresh.refreshComplete();
                        if (entity == null || entity.isEmpty()) {
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            mAdapter.setNewData(entity);
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
                                entity = entity.subList(0, 3);
                            }
                            bgaBanner.setData(R.layout.item_banner, entity, null);
                        }
                    }
                });
        CoinLevelRepository.getInstance().listCoinLevelReplace()
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinLevelProtocol>() {
                    @Override
                    public void _onNext(CoinLevelProtocol entity) {
                        List<CoinLevelProtocol.RATINGEXCELLENTBean> been = new ArrayList<>();
                        List<CoinLevelProtocol.RATINGEXCELLENTBean> goodBeen = entity.getRATING_EXCELLENT();
                        Collections.sort(goodBeen, (o1, o2) -> {
                            if (Integer.parseInt(o1.getPosition()) < Integer.parseInt(o2.getPosition())) {
                                return -1;
                            }
                            if (Integer.parseInt(o1.getPosition()) > Integer.parseInt(o2.getPosition())) {
                                return 1;
                            }
                            return 0;
                        });
                        List<CoinLevelProtocol.RATINGEXCELLENTBean> risingBeen = entity.getFASTEST_RISING();
                        Collections.sort(risingBeen, (o1, o2) -> {
                            if (Integer.parseInt(o1.getPosition()) < Integer.parseInt(o2.getPosition())) {
                                return -1;
                            }
                            if (Integer.parseInt(o1.getPosition()) > Integer.parseInt(o2.getPosition())) {
                                return 1;
                            }
                            return 0;
                        });
                        List<CoinLevelProtocol.RATINGEXCELLENTBean> poorBeen = entity.getPOOR_RATING();
                        Collections.sort(poorBeen, (o1, o2) -> {
                            if (Integer.parseInt(o1.getPosition()) < Integer.parseInt(o2.getPosition())) {
                                return -1;
                            }
                            if (Integer.parseInt(o1.getPosition()) > Integer.parseInt(o2.getPosition())) {
                                return 1;
                            }
                            return 0;
                        });
                        been.add(goodBeen.get(0));
                        been.add(risingBeen.get(0));
                        been.add(poorBeen.get(0));
                        for (int i = 0; i < been.size(); i++) {
                            CoinLevelProtocol.RATINGEXCELLENTBean bean = been.get(i);
                            String chineseName = bean.getCoinLevel().getChineseName();
                            if (TextUtils.isEmpty(chineseName)) {
                                chineseName = "";
                            } else {
                                chineseName = "-" + chineseName;
                            }
                            tvCoinNames.get(i).setText(String.format("%s%s", bean.getCoinLevel().getEnglishName(), chineseName));
                            tvScores.get(i).setText(NumberUtil.round1ToString(bean.getCoinLevel().getNewScore()));
                            double usdExchangeRate = ("TKC".equals(bean.getCoinLevel().getEnglishName())) ? 1 : ConfigConstants.USD_EXCHANGE_RATE;
                            tvPrices.get(i).setText(String.format("¥ %s", NumberUtil.formatNumber(bean.getCoinLevel().getPrice() * usdExchangeRate)));
                            tvStatues.get(i).setText(AppUtil.getStatusName(bean.getCoinLevel().getScoreStatus()));
                            ((RelativeLayout) tvCoinNames.get(i).getParent()).setOnClickListener(v -> startActivity(CoinLevelDetailsActivity.getStartIntent(getContext(), bean.getCoinLevel().getId())));
                        }
                    }
                });
        CoinIndexRepository.getInstance().getCoinIndexTrendNewest(0)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinIndexTrendProtocol>() {
                    @Override
                    public void _onNext(CoinIndexTrendProtocol entity) {
                        if (entity != null) {
                            tvCoinIndexTrendNewest.setText(String.valueOf(entity.getCoinValueSum()));
                        }
                    }
                });
        CoinIndexRepository.getInstance().listCoinIndexTrend(COIN_INDEX_TREND_SIZE, 0)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<List<CoinIndexTrendProtocol>>() {
                    @Override
                    public void _onNext(List<CoinIndexTrendProtocol> entity) {
                        if (entity != null) {
                            float[] ys = new float[entity.size()];
                            for (int i = 0; i < ys.length; i++) {
                                ys[i] = (float) entity.get(i).getCoinValueSum();
                            }
                            Arrays.sort(ys);
                            float maxValue = ys[ys.length - 1];
                            float minValue = ys[0];
                            lcChart.getAxisLeft().setAxisMaximum(maxValue + (maxValue - minValue) / 5 * 1.2f);
                            lcChart.getAxisLeft().setAxisMinimum(minValue - (maxValue - minValue) / 5 * 1.2f);
                            ArrayList<Entry> values = new ArrayList<>();
                            for (int i = 0; i < entity.size(); i++) {
                                values.add(new Entry(10 + i * 10, (float) entity.get(i).getCoinValueSum()));
                            }
                            LineDataSet lineDataSet;
                            lineDataSet = new LineDataSet(values, "AA");
                            lineDataSet.disableDashedLine();
                            lineDataSet.setColor(Color.parseColor("#3061A0"));
                            lineDataSet.setLineWidth(1f);
                            lineDataSet.setDrawCircleHole(false);
                            lineDataSet.setDrawValues(false);
                            lineDataSet.setDrawCircles(false);
                            lineDataSet.setDrawFilled(true);
                            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                lineDataSet.setFillDrawable(getContext().getResources().getDrawable(R.drawable.shape_trend_bg));
                            } else {
                                lineDataSet.setFillColor(Color.parseColor("#FF033C75"));
                            }
                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(lineDataSet);
                            LineData data = new LineData(dataSets);
                            lcChart.setData(data);
                            lcChart.animateX(1200, Easing.EasingOption.Linear);
                        }
                    }
                });
    }
}
