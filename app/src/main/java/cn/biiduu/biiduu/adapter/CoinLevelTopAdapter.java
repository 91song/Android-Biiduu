package cn.biiduu.biiduu.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.constant.ConfigConstants;
import cn.biiduu.biiduu.protocol.CoinLevelProtocol;
import cn.biiduu.biiduu.util.AppUtil;
import cn.biiduu.biiduu.util.NumberUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class CoinLevelTopAdapter extends BaseQuickAdapter<CoinLevelProtocol.RATINGEXCELLENTBean, BaseViewHolder> {
    private int[] goodDrawables = new int[]{R.drawable.biji_card_good_1, R.drawable.biji_card_good_2, R.drawable.biji_card_good_3};
    private int[] fastDrawables = new int[]{R.drawable.biji_card_fast_1, R.drawable.biji_card_fast_2, R.drawable.biji_card_fast_3};
    private int tag;

    public CoinLevelTopAdapter(int tag) {
        super(R.layout.item_coin_level_top);
        this.tag = tag;
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinLevelProtocol.RATINGEXCELLENTBean item) {
        CoinLevelProtocol.RATINGEXCELLENTBean.CoinLevelBean bean = item.getCoinLevel();
        String chineseName = bean.getChineseName();
        if (TextUtils.isEmpty(chineseName)) {
            chineseName = "";
        } else {
            chineseName = "-" + chineseName;
        }
        double usdExchangeRate = ("TKC".equals(bean.getEnglishName())) ? 1 : ConfigConstants.USD_EXCHANGE_RATE;
        helper.setText(R.id.tv_coin_name, bean.getEnglishName() + chineseName)
                .setText(R.id.tv_score, NumberUtil.round1ToString(bean.getNewScore()))
                .setText(R.id.tv_price, "¥ " + NumberUtil.formatNumber(bean.getPrice() * usdExchangeRate))
                .setText(R.id.tv_status, (AppUtil.getStatusName(bean.getScoreStatus())));
        ImageView ivImage = helper.getView(R.id.iv_image);
        Glide.with(ivImage.getContext())
                .load(bean.getLogoUrl())
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation(ivImage.getContext()))
                .dontAnimate()
                .into(ivImage);
        TextView tvScore = helper.getView(R.id.tv_score);
        Drawable drawable = tvScore.getContext().getResources().getDrawable(tag >= 0 ?
                R.drawable.home_ic_arrow_up_smal2 : R.drawable.home_ic_arrow_down_smal);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvScore.setCompoundDrawables(null, null, drawable, null);
        switch (tag) {
            case -1:
                helper.getView(R.id.ll_content).setBackgroundResource(R.drawable.biji_card_bad);
                break;
            case 0:
                helper.getView(R.id.ll_content).setBackgroundResource(goodDrawables[helper.getLayoutPosition() - 1]);
                break;
            case 1:
                helper.getView(R.id.ll_content).setBackgroundResource(fastDrawables[helper.getLayoutPosition() - 1]);
                break;
            default:
                break;
        }
    }
}
