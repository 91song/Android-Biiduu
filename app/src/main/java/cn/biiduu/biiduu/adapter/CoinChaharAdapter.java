package cn.biiduu.biiduu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.essentials.StringUtils;

import java.util.List;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.module.article.ArticleDetailsActivity;
import cn.biiduu.biiduu.protocol.HostArticleProtocol;
import cn.biiduu.biiduu.util.TimeUtil;

public class CoinChaharAdapter extends BaseQuickAdapter<HostArticleProtocol, BaseViewHolder> {
    public CoinChaharAdapter() {
        super(R.layout.item_coin_chahar);
    }

    @Override
    protected void convert(BaseViewHolder helper, HostArticleProtocol item) {
        helper.setText(R.id.tv_time, TimeUtil.formatTime(item.getPublishTime(), "HH:mm"))
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_summary, item.getSummary())
                .setText(R.id.tv_label, item.getTags() == null ? "" : StringUtils.join(item.getTags(), "·"))
                .setOnLongClickListener(R.id.ll_coin_chahar, v -> {
                    helper.getView(R.id.iv_circle).setSelected(true);
                    return false;
                })
                .setOnClickListener(R.id.ll_coin_chahar, v -> {
                    helper.getView(R.id.iv_circle).setSelected(true);
                    helper.getView(R.id.iv_circle).postDelayed(() -> helper.getView(R.id.iv_circle).setSelected(false), 300);
                    List<String> classify = item.getClassify();
                    if (classify != null && !classify.isEmpty()) {
                        v.getContext().startActivity(ArticleDetailsActivity.getStartIntent(v.getContext(),
                                item.getId(), classify.get(0)));
                    }
                });
    }
}
