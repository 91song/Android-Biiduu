package cn.biiduu.biiduu.module.main.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.marno.rapidlib.enums.RxLifeEvent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.CoinIndexAdapter;
import cn.biiduu.biiduu.base.BaseRefreshFragment;
import cn.biiduu.biiduu.data.repository.CoinIndexRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.protocol.CoinIndexProtocol;
import cn.biiduu.biiduu.protocol.CoinIndexTrendProtocol;
import cn.biiduu.biiduu.widget.CustomMarkerView;
import cn.biiduu.biiduu.widget.ObservableScrollView;

public class CoinIndexFragment extends BaseRefreshFragment {
    private static final int COIN_INDEX_PAGE_SIZE = 10;
    private static final int COIN_INDEX_TREND_SIZE = 8;

    @BindView(R.id.fl_coin_index_header)
    FrameLayout flCoinIndexHeader;
    @BindView(R.id.osv_title)
    ObservableScrollView osvTitle;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    private CoinIndexAdapter mAdapter;
    private LineChart lcChart;
    private int mType = 1;
    private AutoRefreshTask mAutoRefreshTask;

    @Override
    protected int getLayout() {
        return R.layout.fragment_coin_index;
    }

    @Override
    protected void initView(View view) {
        ivArrow.setOnClickListener(v -> osvTitle.fullScroll(View.FOCUS_RIGHT));
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
        rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mAdapter.updateObservableScrollView();
                if (dy != 0) {
                    View view = recyclerView.getLayoutManager().findViewByPosition(0);
                    if (view == null || Math.abs(view.getTop()) >= mAdapter.getHeaderLayout().getHeight() - flCoinIndexHeader.getHeight()) {
                        flCoinIndexHeader.setVisibility(View.VISIBLE);
                    } else {
                        flCoinIndexHeader.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void initHeaderView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_coin_index_header,
                (ViewGroup) rvContent.getParent(), false);
        ((RadioGroup) headerView.findViewById(R.id.rg_time)).setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_fenshi) {
                mType = 1;
            } else {
                mType = 0;
            }
            loadCoinIndexTrend(COIN_INDEX_TREND_SIZE, mType);
        });
        lcChart = (LineChart) headerView.findViewById(R.id.lc_chart);
        lcChart.setDragEnabled(true);
        lcChart.setScaleEnabled(false);
        lcChart.setTouchEnabled(true);
        lcChart.setHighlightPerTapEnabled(true);
        lcChart.setHighlightPerDragEnabled(true);
        lcChart.setDoubleTapToZoomEnabled(false);
        lcChart.getDescription().setEnabled(false);
        lcChart.setNoDataText("没有币指走势图数据");
        lcChart.setNoDataTextColor(Color.WHITE);
        lcChart.setDrawMarkers(true);
        lcChart.setMarker(new CustomMarkerView(getContext()));
        Legend legend = lcChart.getLegend();
        legend.setEnabled(false);
        XAxis xAxis = lcChart.getXAxis();
        xAxis.setTextSize(10f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.parseColor("#F7F7F7"));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Color.parseColor("#111111"));
        lcChart.getAxisRight().setEnabled(false);
        lcChart.getAxisLeft().setTextColor(Color.parseColor("#F7F7F7"));
        lcChart.getAxisLeft().setTextSize(10f);
        lcChart.getAxisLeft().setDrawAxisLine(false);
        lcChart.getAxisLeft().enableGridDashedLine(10f, 5f, 0f);
        lcChart.getAxisLeft().setGridColor(Color.parseColor("#111111"));
        ObservableScrollView osvTitle = (ObservableScrollView) headerView.findViewById(R.id.osv_title);
        ImageView ivArrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        ivArrow.setOnClickListener(v -> osvTitle.fullScroll(View.FOCUS_RIGHT));
        mAdapter = new CoinIndexAdapter(osvTitle, this.osvTitle, ivArrow, this.ivArrow);
        mAdapter.bindToRecyclerView(rvContent);
        mAdapter.setEmptyView(R.layout.layout_status_loading);
        mAdapter.setOnLoadMoreListener(() -> CoinIndexRepository.getInstance().getCoinIndex(mAdapter.getData().size(), COIN_INDEX_PAGE_SIZE)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinIndexProtocol>() {
                    @Override
                    public void _onNext(CoinIndexProtocol entity) {
                        mAdapter.loadMoreComplete();
                        if (entity == null) {
                            mAdapter.loadMoreFail();
                        } else {
                            List<CoinIndexProtocol.RowsBean> rowsBeen = entity.getRows();
                            if (rowsBeen == null || rowsBeen.isEmpty()) {
                                mAdapter.loadMoreEnd();
                            } else {
                                mAdapter.addData(rowsBeen);
                                if (rowsBeen.size() < COIN_INDEX_PAGE_SIZE) {
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
        CoinIndexRepository.getInstance().getCoinIndex(0, COIN_INDEX_PAGE_SIZE)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<CoinIndexProtocol>() {
                    @Override
                    public void _onNext(CoinIndexProtocol entity) {
                        flCoinIndexHeader.setVisibility(View.INVISIBLE);
                        pflRefresh.refreshComplete();
                        if (entity == null || entity.getRows() == null || entity.getRows().isEmpty()) {
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            mAdapter.setNewData(entity.getRows());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        flCoinIndexHeader.setVisibility(View.INVISIBLE);
                        pflRefresh.refreshComplete();
                        mAdapter.setNewData(null);
                        mAdapter.setEmptyView(mErrorView);
                    }
                });
        loadCoinIndexTrend(COIN_INDEX_TREND_SIZE, mType);
        if (rvContent != null) {
            if (mAutoRefreshTask == null) {
                mAutoRefreshTask = new AutoRefreshTask();
            }
            rvContent.postDelayed(mAutoRefreshTask, 60000 * 5);
        }
    }

    public class AutoRefreshTask implements Runnable {
        @Override
        public void run() {
            loadData2();
        }
    }

    private List<String> getMinutes(int index) {
        List<String> minutes = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        minutes.add(format.format(calendar.getTime()));
        for (int i = 0; i < index - 1; i++) {
            calendar.add(Calendar.MINUTE, -5);
            minutes.add(format.format(calendar.getTime()));
        }
        Collections.reverse(minutes);
        return minutes;
    }

    private List<String> getDays(int index) {
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        days.add(format.format(calendar.getTime()));
        for (int i = 0; i < index - 1; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            days.add(format.format(calendar.getTime()));
        }
        Collections.reverse(days);
        return days;
    }

    private void loadCoinIndexTrend(int index, int type) {
        CoinIndexRepository.getInstance().listCoinIndexTrend(index, type)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<List<CoinIndexTrendProtocol>>() {
                    @Override
                    public void _onNext(List<CoinIndexTrendProtocol> entity) {
                        if (entity != null) {
                            lcChart.highlightValue(null);
                            XAxis xAxis = lcChart.getXAxis();
                            xAxis.setLabelCount(entity.size());
                            xAxis.setValueFormatter((value, axis) -> {
                                switch (type) {
                                    case 1:
                                        List<String> minutes = getMinutes(entity.size());
                                        if (minutes.size() - 1 >= (int) (value / 10 - 1)) {
                                            return minutes.get((int) (value / 10 - 1));
                                        }
                                        break;
                                    case 0:
                                        List<String> days = getDays(entity.size());
                                        if (days.size() - 1 >= (int) (value / 10 - 1)) {
                                            return days.get((int) (value / 10 - 1));
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                return null;
                            });
                            float[] ys = new float[entity.size()];
                            for (int i = 0; i < ys.length; i++) {
                                ys[i] = (float) entity.get(i).getCoinValueSum();
                            }
                            Arrays.sort(ys);
                            float maxValue = ys[ys.length - 1];
                            float minValue = ys[0];
                            lcChart.getAxisLeft().setAxisMaximum(maxValue + (maxValue - minValue) / 5 * 1.2f);
                            lcChart.getAxisLeft().setAxisMinimum(minValue - (maxValue - minValue) / 5 * 1.2f);
                            lcChart.getAxisLeft().setValueFormatter((value, axis) -> "¥ " + new DecimalFormat("0").format(value));
                            ArrayList<Entry> values = new ArrayList<>();
                            for (int i = 0; i < entity.size(); i++) {
                                values.add(new Entry(10 + i * 10, (float) entity.get(i).getCoinValueSum()));
                            }
                            LineDataSet lineDataSet;
                            lineDataSet = new LineDataSet(values, "AA");
                            lineDataSet.disableDashedLine();
                            lineDataSet.setHighlightEnabled(true);
                            lineDataSet.setDrawHighlightIndicators(false);
                            lineDataSet.setColor(Color.parseColor("#3061A0"));
                            lineDataSet.setCircleColor(Color.parseColor("#3486ED"));
                            lineDataSet.setLineWidth(1f);
                            lineDataSet.setCircleRadius(2f);
                            lineDataSet.setDrawCircleHole(false);
                            lineDataSet.setValueTextSize(9f);
                            lineDataSet.setDrawValues(false);
                            lineDataSet.setDrawCircles(true);
                            lineDataSet.setDrawFilled(true);
                            lineDataSet.setFormLineWidth(1f);
                            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                            lineDataSet.setFormSize(15.f);
                            lineDataSet.setMode(LineDataSet.Mode.LINEAR);
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

    @Override
    public void onDestroyView() {
        if (rvContent != null && mAutoRefreshTask != null) {
            rvContent.removeCallbacks(mAutoRefreshTask);
        }
        super.onDestroyView();
    }
}
