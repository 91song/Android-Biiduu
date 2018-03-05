package cn.biiduu.biiduu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.biiduu.biiduu.R;

public class CoinChaharTimeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CoinChaharTimeAdapter() {
        super(R.layout.item_coin_chahar_time);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_time, item);
    }
}
