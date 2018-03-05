package cn.biiduu.biiduu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.essentials.StringUtils;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.protocol.ArticleProtocol;
import cn.biiduu.biiduu.util.TimeUtil;

public class ArticleAdapter extends BaseQuickAdapter<ArticleProtocol, BaseViewHolder> {
    public ArticleAdapter() {
        super(R.layout.item_article);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleProtocol articleProtocol) {
        ArticleProtocol.ArticleBean articleBean = articleProtocol.getArticle();
        if (articleBean != null) {
            helper.setText(R.id.tv_title, articleBean.getTitle())
                    .setText(R.id.tv_label, articleBean.getTags() == null ? "" : StringUtils.join(articleBean.getTags(), "·"))
                    .setText(R.id.tv_browser_count, String.valueOf(articleBean.getRank()))
                    .setText(R.id.tv_share_count, String.valueOf(articleBean.getShareTimes()))
                    .setText(R.id.tv_time, TimeUtil.formatTime(articleBean.getPublishTime(), "MM.dd"));
            ImageView ivImage = helper.getView(R.id.iv_image);
            Glide.with(ivImage.getContext())
                    .load(articleBean.getCover())
                    .error(R.drawable.img_def_banner_1)
                    .placeholder(R.drawable.img_def_banner_1)
                    .centerCrop()
                    .dontAnimate()
                    .into(ivImage);
        }
    }
}
