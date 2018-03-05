package cn.biiduu.biiduu.module.cointing.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.marno.rapidlib.enums.RxLifeEvent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.CoinTingAdapter;
import cn.biiduu.biiduu.base.BaseRefreshFragment;
import cn.biiduu.biiduu.data.repository.CoinTingRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.cointing.CoinTingDetailsActivity;
import cn.biiduu.biiduu.protocol.CoinTingListProtocol;
import cn.biiduu.biiduu.util.TimeUtil;
import cn.biiduu.biiduu.widget.CustomLoadMoreView;

public class InterestingTingFragment extends BaseRefreshFragment {
    private static final int COIN_TING_PAGE_SIZE = 10;
    private TextView tvTotalPlayCount;
    private TextView tvProgramCount;
    private TextView tvUpdateTime;
    private CoinTingAdapter mAdapter;

    public static InterestingTingFragment newInstance() {
        Bundle args = new Bundle();
        InterestingTingFragment fragment = new InterestingTingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_interesting_ting;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
        initHeaderView();
    }

    @Override
    protected void loadData2() {
        CoinTingRepository.getInstance().listCoinTing("INTEREST", 0, COIN_TING_PAGE_SIZE)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinTingListProtocol>() {
                    @Override
                    public void _onNext(CoinTingListProtocol entity) {
                        pflRefresh.refreshComplete();
                        if (entity == null || entity.getRows() == null || entity.getRows().isEmpty()) {
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            tvTotalPlayCount.setText(getString(R.string.coin_ting_total_play_count, String.valueOf(entity.getTotalView())));
                            tvProgramCount.setText(getString(R.string.coin_ting_program_count, String.valueOf(entity.getTotal())));
                            tvUpdateTime.setText(getString(R.string.coin_ting_update_time, TimeUtil.getTimeToDay(entity.getRows().get(0).getPublishTime())));
                            mAdapter.setNewData(entity.getRows());
                            if (entity.getRows().size() < COIN_TING_PAGE_SIZE) {
                                mAdapter.setEnableLoadMore(false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        pflRefresh.refreshComplete();
                        mAdapter.setNewData(null);
                        mAdapter.setEmptyView(mErrorView);
                    }
                });
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
                CoinTingListProtocol.RowsBean rowsBean = (CoinTingListProtocol.RowsBean) adapter.getData().get(position);
                startActivity(CoinTingDetailsActivity.getStartIntent(getContext(), rowsBean.getId()));
            }
        });
    }

    private void initHeaderView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_coin_ting_header,
                (ViewGroup) rvContent.getParent(), false);
        ((ImageView) headerView.findViewById(R.id.iv_cover)).setImageResource(R.drawable.bi_ting_xinqu_banner);
        ((TextView) headerView.findViewById(R.id.tv_description)).setText(R.string.coin_ting_interesting_ting_description);
        tvTotalPlayCount = (TextView) headerView.findViewById(R.id.tv_total_play_count);
        tvProgramCount = (TextView) headerView.findViewById(R.id.tv_program_count);
        tvUpdateTime = (TextView) headerView.findViewById(R.id.tv_update_time);
        mAdapter = new CoinTingAdapter();
        mAdapter.bindToRecyclerView(rvContent);
        mAdapter.setEmptyView(R.layout.layout_status_loading);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setOnLoadMoreListener(() -> CoinTingRepository.getInstance().listCoinTing("INTEREST", mAdapter.getData().size(), COIN_TING_PAGE_SIZE)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinTingListProtocol>() {
                    @Override
                    public void _onNext(CoinTingListProtocol entity) {
                        mAdapter.loadMoreComplete();
                        if (entity == null) {
                            mAdapter.loadMoreFail();
                        } else {
                            List<CoinTingListProtocol.RowsBean> rowsBeen = entity.getRows();
                            if (rowsBeen == null || rowsBeen.isEmpty()) {
                                mAdapter.loadMoreEnd();
                            } else {
                                mAdapter.addData(entity.getRows());
                                if (entity.getRows().size() < COIN_TING_PAGE_SIZE) {
                                    mAdapter.loadMoreEnd();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter.loadMoreComplete();
                        mAdapter.loadMoreFail();
                    }
                }));
        mAdapter.addHeaderView(headerView);
        rvContent.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData2();
    }
}
