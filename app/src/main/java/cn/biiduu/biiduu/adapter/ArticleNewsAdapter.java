package cn.biiduu.biiduu.adapter;

import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.essentials.StringUtils;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.protocol.CoinCircleListProtocol;
import cn.biiduu.biiduu.util.TimeUtil;

public class ArticleNewsAdapter extends BaseQuickAdapter<CoinCircleListProtocol.RowsBean, BaseViewHolder> {
    public ArticleNewsAdapter() {
        super(R.layout.item_article_news);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinCircleListProtocol.RowsBean rowsBean) {
        helper.setText(R.id.tv_title, Html.fromHtml(rowsBean.getTitle()))
                .setText(R.id.tv_label, rowsBean.getTags() == null ? "" : StringUtils.join(rowsBean.getTags(), "·"))
                .setText(R.id.tv_browser_count, String.valueOf(0))
                .setText(R.id.tv_share_count, String.valueOf(0))
                .setText(R.id.tv_time, TimeUtil.formatTime(rowsBean.getReleaseTime().getTime(), "MM.dd"));
        ImageView ivImage = helper.getView(R.id.iv_image);
        Glide.with(ivImage.getContext())
                .load(rowsBean.getBanner())
                .error(R.drawable.img_def_banner_2)
                .placeholder(R.drawable.img_def_banner_2)
                .centerCrop()
                .dontAnimate()
                .into(ivImage);
    }
}
