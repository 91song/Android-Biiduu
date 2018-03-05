package cn.biiduu.biiduu.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.constant.ConfigConstants;
import cn.biiduu.biiduu.protocol.CoinIndexProtocol;
import cn.biiduu.biiduu.util.NumberUtil;
import cn.biiduu.biiduu.widget.ObservableScrollView;

public class CoinIndexAdapter extends BaseQuickAdapter<CoinIndexProtocol.RowsBean, BaseViewHolder> {
    private List<ObservableScrollView> osvContents = new ArrayList<>();

    public CoinIndexAdapter(ObservableScrollView osvTitle1, ObservableScrollView osvTitle2, ImageView ivArrow1, ImageView ivArrow2) {
        super(R.layout.item_coin_index);
        osvContents.add(osvTitle1);
        osvTitle1.setOnScrollChangedListener((scrollView, x, y, oldx, oldy) -> {
            if (scrollView.getScrollX() + scrollView.getWidth() - scrollView.getPaddingLeft()
                    - scrollView.getPaddingRight() == scrollView.getChildAt(0).getWidth()) {
                ivArrow1.setVisibility(View.INVISIBLE);
            } else {
                ivArrow1.setVisibility(View.VISIBLE);
            }
            for (ObservableScrollView osvContent : osvContents) {
                osvContent.scrollTo(x, y);
            }
        });
        osvContents.add(osvTitle2);
        osvTitle2.setOnScrollChangedListener((scrollView, x, y, oldx, oldy) -> {
            if (scrollView.getScrollX() + scrollView.getWidth() - scrollView.getPaddingLeft()
                    - scrollView.getPaddingRight() == scrollView.getChildAt(0).getWidth()) {
                ivArrow2.setVisibility(View.INVISIBLE);
            } else {
                ivArrow2.setVisibility(View.VISIBLE);
            }
            for (ObservableScrollView osvContent : osvContents) {
                osvContent.scrollTo(x, y);
            }
        });
    }

    public void updateObservableScrollView() {
        for (ObservableScrollView osvContent : osvContents) {
            osvContent.scrollTo(osvContents.get(0).getScrollX(), osvContents.get(0).getScrollY());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinIndexProtocol.RowsBean item) {
        double usdExchangeRate = (item.getCoinType().startsWith("TKC")) ? 1 : ConfigConstants.USD_EXCHANGE_RATE;
        helper.setText(R.id.tv_coin_name, item.getCoinType())
                .setText(R.id.tv_price, "¥ " + NumberUtil.formatNumber(item.getPrice() * usdExchangeRate))
                .setText(R.id.tv_24_hour_volume, "¥ " + NumberUtil.formatNumber(Long.parseLong(item.getVolume24h()) * usdExchangeRate))
                .setText(R.id.tv_24_hour_quote_change, item.getFluctuation24());
        ImageView ivArrow = helper.getView(R.id.iv_arrow);
        ObservableScrollView osvContent = helper.getView(R.id.osv_content);
        osvContent.scrollTo(osvContents.get(0).getScrollX(), osvContents.get(0).getScrollY());
        osvContents.add(osvContent);
        osvContent.setOnScrollChangedListener((scrollView, x, y, oldx, oldy) -> {
            if (scrollView.getScrollX() + scrollView.getWidth() - scrollView.getPaddingLeft()
                    - scrollView.getPaddingRight() == scrollView.getChildAt(0).getWidth()) {
                ivArrow.setVisibility(View.INVISIBLE);
            } else {
                ivArrow.setVisibility(View.VISIBLE);
            }
            for (ObservableScrollView osvContent1 : osvContents) {
                osvContent1.scrollTo(x, y);
            }
        });
        TextView tv24HourQuoteChange = helper.getView(R.id.tv_24_hour_quote_change);
        String quoteChange = item.getFluctuation24();
        if (quoteChange != null) {
            if (quoteChange.startsWith("-")) {
                tv24HourQuoteChange.setTextColor(Color.parseColor("#0DC352"));
            } else if (quoteChange.equals("0%")) {
                tv24HourQuoteChange.setTextColor(Color.parseColor("#333333"));
            } else {
                tv24HourQuoteChange.setTextColor(Color.parseColor("#FE4242"));
            }
        }
        ImageView iv7DayPriceTrend = helper.getView(R.id.iv_7_day_price_trend);
        Glide.with(iv7DayPriceTrend.getContext())
                .load(item.getPricetrend7Day())
                .centerCrop()
                .dontAnimate()
                .into(iv7DayPriceTrend);
    }
}
