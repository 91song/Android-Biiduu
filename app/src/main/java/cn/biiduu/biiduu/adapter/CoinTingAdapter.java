package cn.biiduu.biiduu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.protocol.CoinTingListProtocol;
import cn.biiduu.biiduu.util.TimeUtil;

public class CoinTingAdapter extends BaseQuickAdapter<CoinTingListProtocol.RowsBean, BaseViewHolder> {
    public CoinTingAdapter() {
        super(R.layout.item_coin_ting);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinTingListProtocol.RowsBean item) {
        int number = getData().size() + 1 - helper.getPosition();
        String numberStr = String.valueOf(number);
        if (number < 10) {
            numberStr = "0" + number;
        }
        helper.setText(R.id.tv_title, numberStr + "." + item.getTitle())
                .setText(R.id.tv_time, TimeUtil.formatTime(item.getDuration() * 1000, "mm:ss"))
                .setText(R.id.tv_play_count, "播放量: " + String.valueOf(item.getView()))
                .setText(R.id.tv_publish_time, TimeUtil.formatTime(item.getPublishTime(), "yyyy.MM.dd"));
        ImageView ivCover = helper.getView(R.id.iv_cover);
        Glide.with(ivCover.getContext())
                .load(item.getCover())
                .error(R.drawable.img_def_banner_2)
                .placeholder(R.drawable.img_def_banner_2)
                .centerCrop()
                .dontAnimate()
                .into(ivCover);
    }
}
