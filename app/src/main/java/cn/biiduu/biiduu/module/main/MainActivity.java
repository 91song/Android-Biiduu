package cn.biiduu.biiduu.module.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.marno.rapidlib.basic.BasicActivity;
import com.marno.rapidlib.enums.RxLifeEvent;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.data.repository.AppRepository;
import cn.biiduu.biiduu.data.retrofit.DefaultSubscriber;
import cn.biiduu.biiduu.module.main.fragment.CoinIndexFragment;
import cn.biiduu.biiduu.module.main.fragment.CoinLevelFragment;
import cn.biiduu.biiduu.module.main.fragment.FindFragment;
import cn.biiduu.biiduu.module.main.fragment.HomePageFragment;
import cn.biiduu.biiduu.protocol.UpdateProtocol;
import cn.biiduu.biiduu.util.AppUtil;
import cn.biiduu.biiduu.util.PermissionHelper;
import me.victor.updater.Updater;
import me.victor.updater.UpdaterParams;

public class MainActivity extends BasicActivity implements RadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_CODE = 200;

    @BindView(R.id.rg_bottom_bar)
    RadioGroup rgBottomBar;

    private String[] mTags = new String[]{"home_page", "coin_level", "coin_index", "find"};

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rgBottomBar.setOnCheckedChangeListener(this);
        ((RadioButton) rgBottomBar.findViewById(R.id.rb_home_page)).setChecked(true);
        requestPermission();
    }

    private void requestPermission() {
        PermissionHelper.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                new PermissionHelper.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onShowRationale() {
                        PermissionHelper.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE);
                    }
                }, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (REQUEST_CODE == requestCode) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "授权成功", Toast.LENGTH_LONG).show();
//            } else {
//                if (PermissionHelper.shouldShowRequestPermissionRationale(this, permissions[0])) {
//                    Toast.makeText(this, "授权失败", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "不再询问", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
    }

    private void switchFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 隐藏已加入的Fragment
        for (String tag : mTags) {
            Fragment fragment = manager.findFragmentByTag(tag);
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        // 延迟显示指定Fragment
        Fragment fragment = manager.findFragmentByTag(mTags[position]);
        if (fragment == null) {
            transaction.add(R.id.rl_content, createFragment(position), mTags[position]);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }

    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomePageFragment();
            case 1:
                return new CoinLevelFragment();
            case 2:
                return new CoinIndexFragment();
            case 3:
                return new FindFragment();
            default:
                return null;
        }
    }

    @Override
    protected void loadData() {
        AppRepository.getInstance().checkUpdate("android", AppUtil.getVersionName(this))
                .compose(bindUntilEvent(RxLifeEvent.DESTROY))
                .subscribe(new DefaultSubscriber<UpdateProtocol>() {
                    @Override
                    public void _onNext(UpdateProtocol entity) {
                        if (entity != null) {
                            if (!entity.isLast()) {
                                String title = entity.isMustUpdate() ? "更新提示" : "有新版本啦";
                                String msg = entity.isMustUpdate() ? "由于系统升级，您的版本已停止服务，请及时更新到最新版本！" : "为了更好的为您服务，建议您更新到最新版本！";
                                UpdaterParams params = new UpdaterParams.Builder().setUpdateTitle(title)
                                        .setUpdateMsg(msg)
                                        .isForceUpdate(entity.isMustUpdate())
                                        .setDownloadUrl(entity.getUpdateUri())
                                        .setAuthority("cn.biiduu.fileprovider")
                                        .build();
                                Updater.getInstance().checkUpdate(MainActivity.this, params);
                            }
                        }
                    }
                });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switchFragment(radioGroup.indexOfChild(findViewById(i)));
    }
}
