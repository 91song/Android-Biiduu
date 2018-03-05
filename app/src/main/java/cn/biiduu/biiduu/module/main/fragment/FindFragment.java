package cn.biiduu.biiduu.module.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.marno.rapidlib.basic.BasicFragment;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.module.cointing.fragment.ProfessionalTingFragment;

public class FindFragment extends BasicFragment implements RadioGroup.OnCheckedChangeListener {
    private String[] mTags = new String[]{"news", "coin_ting"};

    @BindView(R.id.rg_title)
    RadioGroup rgTitle;

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rgTitle.setOnCheckedChangeListener(this);
        ((RadioButton) rgTitle.findViewById(R.id.rb_news)).setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switchFragment(group.indexOfChild(group.findViewById(checkedId)));
    }

    private void switchFragment(int position) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
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
                return new NewsFragment();
            case 1:
                return new ProfessionalTingFragment();
            default:
                return null;
        }
    }
}
