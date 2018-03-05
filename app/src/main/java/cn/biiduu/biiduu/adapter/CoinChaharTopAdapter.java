package cn.biiduu.biiduu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.protocol.ArticleProtocol;

public class CoinChaharTopAdapter extends BaseQuickAdapter<ArticleProtocol, BaseViewHolder> {
    public CoinChaharTopAdapter() {
        super(R.layout.item_coin_chahar_top);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleProtocol item) {
        ArticleProtocol.ArticleBean articleBean = item.getArticle();
        if (articleBean != null) {
            helper.setText(R.id.tv_title, articleBean.getTitle());
            ImageView imageView = helper.getView(R.id.iv_image);
            Glide.with(imageView.getContext())
                    .load(item.getArticle().getCover())
                    .error(R.drawable.img_def_banner_1)
                    .placeholder(R.drawable.img_def_banner_1)
                    .centerCrop()
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
