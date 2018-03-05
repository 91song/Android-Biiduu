package cn.biiduu.biiduu.module.article.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.marno.rapidlib.enums.RxLifeEvent;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.adapter.CoinChaharAdapter;
import cn.biiduu.biiduu.base.BaseLoadFragment;
import cn.biiduu.biiduu.constant.EnumConstants;
import cn.biiduu.biiduu.data.repository.ArticleRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.protocol.ArticleListProtocol;
import cn.biiduu.biiduu.util.ScreenUtil;

@SuppressLint("ValidFragment")
public class CoinChaharFragment extends BaseLoadFragment {
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private OnScrolledListener mListener;

    public CoinChaharFragment(OnScrolledListener listener) {
        mListener = listener;
    }

    private CoinChaharAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_coin_chahar;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContent.setHasFixedSize(true);
        mAdapter = new CoinChaharAdapter();
        View headerView = new View(getContext());
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(150)));
        headerView.setBackgroundColor(Color.parseColor("#111111"));
        mAdapter.addHeaderView(headerView);
        rvContent.setAdapter(mAdapter);
        rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mListener != null) {
                    mListener.onScrolled(recyclerView, dx, dy);
                }
            }
        });
        loadData2();
    }

    protected void loadData2() {
        String date = getArguments().getString("date");
        ArticleRepository.getInstance().listArticle(EnumConstants.CLASSIFY_COIN_CHAHAR, 0, 100, date)
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<ArticleListProtocol>() {
                    @Override
                    public void _onNext(ArticleListProtocol entity) {
                        if (entity == null || entity.getRows() == null || entity.getRows().isEmpty()) {
                            esvStatus.empty();
                        } else {
                            mAdapter.addData(entity.getRows());
                            esvStatus.content();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        esvStatus.noNet();
                    }
                });
    }

    public interface OnScrolledListener {
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }
}
