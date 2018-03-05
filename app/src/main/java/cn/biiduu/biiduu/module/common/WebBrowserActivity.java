package cn.biiduu.biiduu.module.common;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marno.rapidlib.basic.BasicActivity;

import butterknife.BindView;
import cn.biiduu.biiduu.R;

public class WebBrowserActivity extends BasicActivity {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.wv_web_browser)
    WebView wvWebBrowser;

    public static Intent getStartIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebBrowserActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        return intent;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web_browser;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibBack.setOnClickListener(view -> onBackPressed());
        tvTitle.setText(getIntent().getStringExtra("title"));
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = wvWebBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        wvWebBrowser.setWebViewClient(new CustomWebViewClient());
        wvWebBrowser.setWebChromeClient(new CustomWebChromeClient());
        wvWebBrowser.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wvWebBrowser != null) {
            wvWebBrowser.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (wvWebBrowser != null) {
            wvWebBrowser.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (wvWebBrowser != null) {
            wvWebBrowser.stopLoading();
            wvWebBrowser.destroy();
            wvWebBrowser = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_DOWN) {
            if (wvWebBrowser != null && wvWebBrowser.canGoBack()) {
                wvWebBrowser.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pbProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbProgress.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pbProgress.setProgress(newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult jsResult) {
            new AlertDialog.Builder(view.getContext()).setTitle(getString(R.string.app_name))
                    .setMessage(message).setCancelable(false).setPositiveButton(android.R.string.ok, null).create().show();
            if (jsResult != null) {
                jsResult.confirm();
            }
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            tvTitle.setText(title);
        }
    }
}
