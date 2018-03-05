package cn.biiduu.biiduu.util;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.Log;

import cn.biiduu.biiduu.BuildConfig;

public class ShareManager {
    private static ShareManager instance;

    private ShareManager() {

    }

    public synchronized static ShareManager getInstance() {
        if (instance == null) {
            instance = new ShareManager();
        }
        return instance;
    }

    public void init(Context context) {
        Log.LOG = BuildConfig.DEBUG;
        PlatformConfig.setQQZone("1106468734", "Dt6s7rdrbuzVVddA");
        PlatformConfig.setWeixin("wxd118b91c170c73a8", "c2154118f9c4f2abdade7bef2a46582e");
        PlatformConfig.setSinaWeibo("2951117956", "6e08862bc93b1e14c577557a0ff780c0", "https://api.weibo.com/oauth2/default.html");
        UMShareAPI.get(context);
    }

    public void shareText(Activity activity, ShareBean shareBean, UMShareListener listener) {
        new ShareAction(activity).setPlatform(shareBean.getPlatform())
                .withText(shareBean.getText())
                .setCallback(listener)
                .share();
    }

    public void shareImage(Activity activity, ShareBean shareBean, UMShareListener listener) {
        new ShareAction(activity).setPlatform(shareBean.getPlatform())
                .withText(shareBean.getText())
                .withMedia(shareBean.getImage())
                .setCallback(listener)
                .share();
    }

    public void shareLink(Activity activity, ShareBean shareBean, UMShareListener listener) {
        UMWeb web = new UMWeb(shareBean.getTargetUrl());
        web.setTitle(shareBean.getTitle());
        web.setThumb(shareBean.getImage());
        web.setDescription(shareBean.getText());
        new ShareAction(activity).setPlatform(shareBean.getPlatform())
                .withMedia(web)
                .setCallback(listener)
                .share();
    }

    public void shareMusic(Activity activity, ShareBean shareBean, UMShareListener listener) {
        new ShareAction(activity).setPlatform(shareBean.getPlatform())
                .withMedia(shareBean.getMusic())
                .setCallback(listener)
                .share();
    }

    public void doOauthVerify(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        UMShareAPI shareAPI = UMShareAPI.get(activity);
        shareAPI.doOauthVerify(activity, platform, listener);
    }

    public void getPlatformInfo(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        UMShareAPI shareAPI = UMShareAPI.get(activity);
        shareAPI.getPlatformInfo(activity, platform, listener);
    }

    public boolean isAuthorize(Activity activity, SHARE_MEDIA platform) {
        UMShareAPI shareAPI = UMShareAPI.get(activity);
        return shareAPI.isAuthorize(activity, platform);
    }

    public boolean isInstall(Activity activity, SHARE_MEDIA platform) {
        UMShareAPI shareAPI = UMShareAPI.get(activity);
        return shareAPI.isInstall(activity, platform);
    }

    public static class ShareBean {
        private SHARE_MEDIA platform;
        private String title;
        private String text;
        private UMImage image;
        private UMusic music;
        private String targetUrl;

        public ShareBean(String title, String text, String targetUrl) {
            super();
            this.title = title;
            this.text = text;
            this.targetUrl = targetUrl;
        }

        public ShareBean(String title, String text, UMImage image, String targetUrl) {
            super();
            this.title = title;
            this.text = text;
            this.image = image;
            this.targetUrl = targetUrl;
        }

        public ShareBean(UMImage image) {
            this.image = image;
        }

        public ShareBean(UMusic music) {
            this.music = music;
        }

        SHARE_MEDIA getPlatform() {
            return platform;
        }

        public void setPlatform(SHARE_MEDIA platform) {
            this.platform = platform;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public UMImage getImage() {
            return image;
        }

        public void setImage(UMImage image) {
            this.image = image;
        }

        private UMusic getMusic() {
            return music;
        }

        public void setMusic(UMusic music) {
            this.music = music;
        }
    }
}
