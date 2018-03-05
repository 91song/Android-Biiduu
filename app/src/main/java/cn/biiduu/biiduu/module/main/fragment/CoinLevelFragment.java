package cn.biiduu.biiduu.module.main.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.marno.rapidlib.enums.RxLifeEvent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.CoinLevelAdapter;
import cn.biiduu.biiduu.adapter.CoinLevelTopAdapter;
import cn.biiduu.biiduu.base.BaseRefreshFragment;
import cn.biiduu.biiduu.data.repository.CoinLevelRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.coinlevel.CoinLevelDetailsActivity;
import cn.biiduu.biiduu.protocol.CoinLevelListProtocol;
import cn.biiduu.biiduu.protocol.CoinLevelProtocol;

public class CoinLevelFragment extends BaseRefreshFragment {
    private static final int COIN_LEVEL_PAGE_SIZE = 10;

    @BindView(R.id.ll_coin_level_header)
    LinearLayout llCoinLevelHeader;

    private CoinLevelAdapter mAdapter;
    private CoinLevelTopAdapter mGoodAdapter;
    private CoinLevelTopAdapter mRisingAdapter;
    private CoinLevelTopAdapter mBadAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_coin_level;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
        initHeaderView();
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
                CoinLevelListProtocol.RowsBean rowsBean = (CoinLevelListProtocol.RowsBean) adapter.getData().get(position);
                startActivity(CoinLevelDetailsActivity.getStartIntent(getContext(), rowsBean.getId()));
            }
        });
        rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy != 0) {
                    View view = recyclerView.getLayoutManager().findViewByPosition(0);
                    if (view == null || Math.abs(view.getTop()) >= mAdapter.getHeaderLayout().getHeight() - llCoinLevelHeader.getHeight()) {
                        llCoinLevelHeader.setVisibility(View.VISIBLE);
                    } else {
                        llCoinLevelHeader.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void initHeaderView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_coin_level_header,
                (ViewGroup) rvContent.getParent(), false);
        RecyclerView rvGood = (RecyclerView) headerView.findViewById(R.id.rv_good);
        LinearLayoutManager goodManager = new LinearLayoutManager(getContext());
        goodManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGood.setLayoutManager(goodManager);
        rvGood.setHasFixedSize(true);
        rvGood.addItemDecoration(
                new VerticalDividerItemDecoration.Builder(getContext())
                        .color(Color.TRANSPARENT)
                        .sizeResId(R.dimen.divider_10)
                        .showLastDivider()
                        .build());
        new PagerSnapHelper().attachToRecyclerView(rvGood);
        mGoodAdapter = new CoinLevelTopAdapter(0);
        mGoodAdapter.addHeaderView(new View(getContext()), 0, LinearLayout.HORIZONTAL);
        rvGood.setAdapter(mGoodAdapter);
        rvGood.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoinLevelProtocol.RATINGEXCELLENTBean bean = (CoinLevelProtocol.RATINGEXCELLENTBean) adapter.getData().get(position);
                startActivity(CoinLevelDetailsActivity.getStartIntent(getContext(), bean.getCoinLevel().getId()));
            }
        });
        RecyclerView rvRising = (RecyclerView) headerView.findViewById(R.id.rv_rising);
        LinearLayoutManager risingManager = new LinearLayoutManager(getContext());
        risingManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRising.setLayoutManager(risingManager);
        rvRising.setHasFixedSize(true);
        rvRising.addItemDecoration(
                new VerticalDividerItemDecoration.Builder(getContext())
                        .color(Color.TRANSPARENT)
                        .sizeResId(R.dimen.divider_10)
                        .showLastDivider()
                        .build());
        new PagerSnapHelper().attachToRecyclerView(rvRising);
        mRisingAdapter = new CoinLevelTopAdapter(1);
        mRisingAdapter.addHeaderView(new View(getContext()), 0, LinearLayout.HORIZONTAL);
        rvRising.setAdapter(mRisingAdapter);
        rvRising.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoinLevelProtocol.RATINGEXCELLENTBean bean = (CoinLevelProtocol.RATINGEXCELLENTBean) adapter.getData().get(position);
                startActivity(CoinLevelDetailsActivity.getStartIntent(getContext(), bean.getCoinLevel().getId()));
            }
        });
        RecyclerView rvBad = (RecyclerView) headerView.findViewById(R.id.rv_bad);
        LinearLayoutManager badManager = new LinearLayoutManager(getContext());
        badManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvBad.setLayoutManager(badManager);
        rvBad.setHasFixedSize(true);
        rvBad.addItemDecoration(
                new VerticalDividerItemDecoration.Builder(getContext())
                        .color(Color.TRANSPARENT)
                        .sizeResId(R.dimen.divider_10)
                        .showLastDivider()
                        .build());
        new PagerSnapHelper().attachToRecyclerView(rvBad);
        mBadAdapter = new CoinLevelTopAdapter(-1);
        mBadAdapter.addHeaderView(new View(getContext()), 0, LinearLayout.HORIZONTAL);
        rvBad.setAdapter(mBadAdapter);
        rvBad.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoinLevelProtocol.RATINGEXCELLENTBean bean = (CoinLevelProtocol.RATINGEXCELLENTBean) adapter.getData().get(position);
                startActivity(CoinLevelDetailsActivity.getStartIntent(getContext(), bean.getCoinLevel().getId()));
            }
        });
        mAdapter = new CoinLevelAdapter();
        mAdapter.bindToRecyclerView(rvContent);
        mAdapter.setEmptyView(R.layout.layout_status_loading);
        mAdapter.setOnLoadMoreListener(() -> CoinLevelRepository.getInstance().listCoinLevel(mAdapter.getData().size(), COIN_LEVEL_PAGE_SIZE)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinLevelListProtocol>() {
                    @Override
                    public void _onNext(CoinLevelListProtocol entity) {
                        mAdapter.loadMoreComplete();
                        if (entity == null) {
                            mAdapter.loadMoreFail();
                        } else {
                            List<CoinLevelListProtocol.RowsBean> rowsBeen = entity.getRows();
                            if (rowsBeen == null || rowsBeen.isEmpty()) {
                                mAdapter.loadMoreEnd();
                            } else {
                                mAdapter.addData(entity.getRows());
                                if (entity.getRows().size() < COIN_LEVEL_PAGE_SIZE) {
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
    protected void loadData2() {
        CoinLevelRepository.getInstance().listCoinLevel(0, COIN_LEVEL_PAGE_SIZE)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinLevelListProtocol>() {
                    @Override
                    public void _onNext(CoinLevelListProtocol entity) {
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
        CoinLevelRepository.getInstance().listCoinLevelReplace()
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinLevelProtocol>() {
                    @Override
                    public void _onNext(CoinLevelProtocol entity) {
                        List<CoinLevelProtocol.RATINGEXCELLENTBean> goodBeen = entity.getRATING_EXCELLENT();
                        if (goodBeen != null && !goodBeen.isEmpty()) {
                            Collections.sort(goodBeen, (o1, o2) -> {
                                if (Integer.parseInt(o1.getPosition()) < Integer.parseInt(o2.getPosition())) {
                                    return -1;
                                }
                                if (Integer.parseInt(o1.getPosition()) > Integer.parseInt(o2.getPosition())) {
                                    return 1;
                                }
                                return 0;
                            });
                            if (goodBeen.size() > 3) {
                                goodBeen = goodBeen.subList(0, 3);
                            }
                            mGoodAdapter.setNewData(goodBeen);
                        }
                        List<CoinLevelProtocol.RATINGEXCELLENTBean> risingBeen = entity.getFASTEST_RISING();
                        if (risingBeen != null && !risingBeen.isEmpty()) {
                            Collections.sort(risingBeen, (o1, o2) -> {
                                if (Integer.parseInt(o1.getPosition()) < Integer.parseInt(o2.getPosition())) {
                                    return -1;
                                }
                                if (Integer.parseInt(o1.getPosition()) > Integer.parseInt(o2.getPosition())) {
                                    return 1;
                                }
                                return 0;
                            });
                            if (risingBeen.size() > 3) {
                                risingBeen = risingBeen.subList(0, 3);
                            }
                            mRisingAdapter.setNewData(risingBeen);
                        }
                        List<CoinLevelProtocol.RATINGEXCELLENTBean> badBeen = entity.getPOOR_RATING();
                        if (badBeen != null && !badBeen.isEmpty()) {
                            Collections.sort(badBeen, (o1, o2) -> {
                                if (Integer.parseInt(o1.getPosition()) < Integer.parseInt(o2.getPosition())) {
                                    return -1;
                                }
                                if (Integer.parseInt(o1.getPosition()) > Integer.parseInt(o2.getPosition())) {
                                    return 1;
                                }
                                return 0;
                            });
                            if (badBeen.size() > 3) {
                                badBeen = badBeen.subList(0, 3);
                            }
                            mBadAdapter.setNewData(badBeen);
                        }
                    }
                });
    }
}
