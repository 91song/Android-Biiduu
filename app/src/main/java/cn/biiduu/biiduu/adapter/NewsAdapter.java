package cn.biiduu.biiduu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.protocol.CoinLevelDetailsProtocol;

public class NewsAdapter extends BaseQuickAdapter<CoinLevelDetailsProtocol.NewsInformationBean, BaseViewHolder> {
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NewsAdapter() {
        super(R.layout.item_news);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinLevelDetailsProtocol.NewsInformationBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_src, item.getSource())
                .setText(R.id.tv_time, time);
    }
}
