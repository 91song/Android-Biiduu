package cn.biiduu.biiduu.module.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.Toast;

import com.marno.rapidlib.basic.BasicFragment;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.module.cointing.fragment.InterestingTingFragment;
import cn.biiduu.biiduu.module.cointing.fragment.ProfessionalTingFragment;
import cn.biiduu.biiduu.util.ToastUtil;

public class CoinTingFragment extends BasicFragment {
    private static final int FRAGMENT_COUNT = 1;

    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.rb_interesting)
    RadioButton rbInteresting;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private boolean isMeasured;

    @Override
    protected int getLayout() {
        return R.layout.fragment_coin_ting;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rbInteresting.setOnClickListener(v -> ToastUtil.showCenterToast(getContext(), R.string.coin_ting_interesting_ting_description, Toast.LENGTH_SHORT));
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnPreDrawListener(() -> {
            if (!isMeasured) {
//                rgNavigation.setOnCheckedChangeListener((group, checkedId) -> {
//                    int position = group.indexOfChild(group.findViewById(checkedId));
//                    vLine.setTranslationX(vLine.getWidth() * position + (position == 0 ? 0 : ScreenUtil.dp2px(40)));
//                    vpContent.setCurrentItem(position, false);
//                });
//                vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                        float offset = positionOffsetPixels / FRAGMENT_COUNT * (rgNavigation.getWidth() + ScreenUtil.dp2px(40)) / ScreenUtil.getScreenWidth(getContext());
//                        if (offset > 0) {
//                            vLine.setTranslationX(vLine.getWidth() * position + offset);
//                        }
//                    }
//
//                    @Override
//                    public void onPageSelected(int position) {
//                        ((RadioButton) rgNavigation.getChildAt(position)).setChecked(true);
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int state) {
//
//                    }
//                });
                vpContent.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        if (position == 0) {
                            return ProfessionalTingFragment.newInstance();
                        } else if (position == 1) {
                            return InterestingTingFragment.newInstance();
                        }
                        return null;
                    }

                    @Override
                    public int getCount() {
                        return FRAGMENT_COUNT;
                    }
                });
                isMeasured = true;
            }
            return true;
        });
    }
}
