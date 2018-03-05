package cn.biiduu.biiduu.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marno.rapidlib.basic.BasicFragment;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.widget.SquareHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public abstract class BaseRefreshFragment extends BasicFragment {
    private static final int REFRESH_LOADING_MIN_TIME = 1500;
    private static final int REFRESH_CLOSE_HEADER_DURATION = 500;
    private static final long REFRESH_AUTO_REFRESH_DELAY_TIME = 150;

    @BindView(R.id.pfl_refresh)
    protected PtrFrameLayout pflRefresh;
    @BindView(R.id.rv_content)
    protected RecyclerView rvContent;

    protected View mEmptyView;
    protected View mErrorView;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initRefresh();
        initEmptyView();
        initView(view);
    }

    protected void initRefresh() {
//        srlRefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        srlRefresh.setColorSchemeResources(R.color.refresh_color, R.color.refresh_color, R.color.refresh_color);
//        srlRefresh.setDistanceToTriggerSync(200);
//        srlRefresh.setProgressViewEndTarget(false, 200);
//        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadData();
//            }
//        });
        if (pflRefresh != null) {
            pflRefresh.setLoadingMinTime(REFRESH_LOADING_MIN_TIME);
            pflRefresh.setDurationToCloseHeader(REFRESH_CLOSE_HEADER_DURATION);
            pflRefresh.disableWhenHorizontalMove(true);
            SquareHeader squareHeader = new SquareHeader(getContext());
            pflRefresh.setHeaderView(squareHeader);
            pflRefresh.addPtrUIHandler(squareHeader);
            pflRefresh.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return rvContent != null && !rvContent.canScrollVertically(-1);
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    loadData2();
                }
            });
            pflRefresh.postDelayed(() -> pflRefresh.autoRefresh(), REFRESH_AUTO_REFRESH_DELAY_TIME);
        }
    }

    protected void initEmptyView() {
        mEmptyView = getActivity().getLayoutInflater().inflate(R.layout.layout_status_empty,
                (ViewGroup) rvContent.getParent(), false);
        mErrorView = getActivity().getLayoutInflater().inflate(R.layout.layout_status_no_net,
                (ViewGroup) rvContent.getParent(), false);
    }

    protected abstract void initView(View view);

    protected abstract void loadData2();
}
