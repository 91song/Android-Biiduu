package cn.biiduu.biiduu.module.launch;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.marno.rapidlib.basic.BasicActivity;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.module.main.MainActivity;
import cn.biiduu.biiduu.util.AppUtil;
import cn.biiduu.biiduu.util.SpUtil;
import cn.biiduu.biiduu.util.TimerUtil;
import cn.bingoogolapple.bgabanner.BGABanner;

public class SplashActivity extends BasicActivity {
    private static final long DELAY_TIME = 2000;
    private static final String KEY_APP_VERSION_CODE = "app_version_code";
    private static final int[] GUIDE_DRAWABLES = {R.drawable.guide_1, R.drawable.guide_2,
            R.drawable.guide_3, R.drawable.guide_4};

    @BindView(R.id.bga_banner)
    BGABanner bgaBanner;

    @Override
    protected void beforeSetView() {
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        int currentVersionCode = AppUtil.getVersionCode(this);
        int appVersionCode = (int) SpUtil.get(this, KEY_APP_VERSION_CODE, 0);
        if (appVersionCode == 0 || appVersionCode != currentVersionCode) {
            bgaBanner.setVisibility(View.VISIBLE);
            bgaBanner.setDelegate((banner, itemView, model, position) -> {
                if (position == 0 || position == GUIDE_DRAWABLES.length - 1) {
                    SpUtil.put(SplashActivity.this, KEY_APP_VERSION_CODE, currentVersionCode);
                    startActivity(MainActivity.getStartIntent(SplashActivity.this));
                    finish();
                }
            });
            bgaBanner.setData(GUIDE_DRAWABLES);
        } else {
            TimerUtil.getInstance().setTimer(DELAY_TIME, () -> {
                startActivity(MainActivity.getStartIntent(SplashActivity.this));
                finish();
            });
        }
    }
}
