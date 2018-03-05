package cn.biiduu.biiduu.module.coinlevel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.marno.rapidlib.enums.RxLifeEvent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.NewsAdapter;
import cn.biiduu.biiduu.base.BaseLoadActivity;
import cn.biiduu.biiduu.constant.ConfigConstants;
import cn.biiduu.biiduu.data.repository.CoinLevelRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.common.WebBrowserActivity;
import cn.biiduu.biiduu.protocol.CoinLevelDetailsProtocol;
import cn.biiduu.biiduu.util.AppUtil;
import cn.biiduu.biiduu.util.NumberUtil;
import cn.biiduu.biiduu.util.ScreenUtil;
import cn.biiduu.biiduu.util.TimeUtil;

public class CoinLevelDetailsActivity extends BaseLoadActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_coin_name)
    TextView tvCoinName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_organization)
    TextView tvOrganization;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private TextView tvSimpleComment;
    private TextView tvTeamIntroduction;
    private LinearLayout llNews;
    private NewsAdapter mAdapter;

    public static Intent getStartIntent(Context context, String coinLevelId) {
        Intent intent = new Intent(context, CoinLevelDetailsActivity.class);
        intent.putExtra("coinLevelId", coinLevelId);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coin_level_details;
    }

    @Override
    protected void initView() {
        ibBack.setOnClickListener(view -> onBackPressed());
        initRecyclerView();
        initHeaderView();
    }

    private void initRecyclerView() {
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        rvContent.setHasFixedSize(true);
        rvContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#F7F7F7"))
                .margin(ScreenUtil.dp2px(15), ScreenUtil.dp2px(15))
                .sizeResId(R.dimen.divider_1)
                .visibilityProvider((position, parent) -> position == 0)
                .build());
        rvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoinLevelDetailsProtocol.NewsInformationBean newsInformationBean = (CoinLevelDetailsProtocol.NewsInformationBean) adapter.getData().get(position);
                startActivity(WebBrowserActivity.getStartIntent(CoinLevelDetailsActivity.this, "新闻详情", newsInformationBean.getUrl()));
            }
        });
    }

    private void initHeaderView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_coin_level_details_header,
                (ViewGroup) rvContent.getParent(), false);
        tvSimpleComment = (TextView) headerView.findViewById(R.id.tv_simple_comment);
        tvTeamIntroduction = (TextView) headerView.findViewById(R.id.tv_team_introduction);
        llNews = (LinearLayout) headerView.findViewById(R.id.ll_news);
        mAdapter = new NewsAdapter();
        mAdapter.addHeaderView(headerView);
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        String coinLevelId = getIntent().getStringExtra("coinLevelId");
        CoinLevelRepository.getInstance().getCoinLevelDetails(coinLevelId)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinLevelDetailsProtocol>() {
                    @Override
                    public void _onNext(CoinLevelDetailsProtocol entity) {
                        if (entity == null) {
                            esvStatus.empty();
                        } else {
                            String status = AppUtil.getStatusName(entity.getScoreStatus());
                            tvCoinName.setText(entity.getEnglishName());
                            double usdExchangeRate = ("TKC".equals(entity.getEnglishName())) ? 1 : ConfigConstants.USD_EXCHANGE_RATE;
                            tvPrice.setText(String.format("¥ %s", NumberUtil.formatNumber(entity.getPrice() * usdExchangeRate)));
                            tvScore.setText(NumberUtil.round1ToString(entity.getNewScore()));
                            tvStatus.setText(status);
                            tvOrganization.setText(entity.getGradingInstitution());
                            tvTime.setText(TimeUtil.getTimeToDay(entity.getPublishTime()));
                            tvSimpleComment.setText(entity.getSimpleEvaluate());
                            tvTeamIntroduction.setText(entity.getContent());
                            mAdapter.setTime(TimeUtil.getTimeToDay(entity.getPublishTime()));
                            if (entity.getNewsInformation() != null && !entity.getNewsInformation().isEmpty()) {
                                mAdapter.addData(entity.getNewsInformation());
                                llNews.setVisibility(View.VISIBLE);
                            }
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
