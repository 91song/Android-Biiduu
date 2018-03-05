package cn.biiduu.biiduu.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.protocol.CoinLevelListProtocol;
import cn.biiduu.biiduu.util.AppUtil;
import cn.biiduu.biiduu.util.NumberUtil;

public class CoinLevelAdapter extends BaseQuickAdapter<CoinLevelListProtocol.RowsBean, BaseViewHolder> {
    public CoinLevelAdapter() {
        super(R.layout.item_coin_level);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinLevelListProtocol.RowsBean item) {
        helper.setText(R.id.tv_coin_name, item.getEnglishName())
                .setText(R.id.tv_score, NumberUtil.round1ToString(item.getNewScore()))
                .setText(R.id.tv_status, AppUtil.getStatusName(item.getScoreStatus()))
                .setText(R.id.tv_quote_change, item.getFluctuation24());
        TextView tvQuoteChange = helper.getView(R.id.tv_quote_change);
        String quoteChange = item.getFluctuation24();
        if (quoteChange != null) {
            if (quoteChange.startsWith("-")) {
                tvQuoteChange.setTextColor(Color.parseColor("#0DC352"));
            } else if (quoteChange.equals("0%")) {
                tvQuoteChange.setTextColor(Color.parseColor("#333333"));
            } else {
                tvQuoteChange.setTextColor(Color.parseColor("#FE4242"));
            }
        }
        TextView tvStatus = helper.getView(R.id.tv_status);
    }
}
