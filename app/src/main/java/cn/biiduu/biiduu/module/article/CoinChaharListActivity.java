package cn.biiduu.biiduu.module.article;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marno.rapidlib.basic.BasicActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.module.article.fragment.CoinChaharFragment;
import cn.biiduu.biiduu.util.ScreenUtil;
import cn.biiduu.biiduu.widget.FixedViewPager;

public class CoinChaharListActivity extends BasicActivity implements CoinChaharFragment.OnScrolledListener {
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.vp_coin_chahar_date)
    ViewPager vpCoinChaharDate;
    @BindView(R.id.fvp_coin_chahar)
    FixedViewPager fvpCoinChahar;
    @BindView(R.id.fl_coin_chahar_date)
    FrameLayout flCoinChaharDate;

    private List<Date> mDates = getDays();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CoinChaharListActivity.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coin_chahar_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
        ibBack.setOnClickListener(view -> onBackPressed());
        initViewPager();
    }

    private void initViewPager() {
        vpCoinChaharDate.setOffscreenPageLimit(3);
        vpCoinChaharDate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fvpCoinChahar.setCurrentItem(position, true);
                vpCoinChaharDate.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpCoinChaharDate.setPageTransformer(true, (page, position) -> {
            if (position < -1) {
                page.setAlpha(0f);
                page.setScaleX((float) (1.2 + position * 0.08));
                page.setScaleY((float) (1.2 + position * 0.08));
            } else if (position <= 0) {
                page.findViewById(R.id.v_line).setAlpha(1 + position);
                float factor = 0.5f + (1 - 0.5f) * (1 + position);
                page.setAlpha(factor);
                page.setScaleX((float) (1.2 + position * 0.08));
                page.setScaleY((float) (1.2 + position * 0.08));
            } else if (position <= 1) {
                page.findViewById(R.id.v_line).setAlpha(1 - position);
                float factor = 0.5f + (1 - 0.5f) * (1 - position);
                page.setAlpha(factor);
                page.setScaleX((float) (1.2 - position * 0.08));
                page.setScaleY((float) (1.2 - position * 0.08));
            } else if (position > 1) {
                page.setAlpha(0f);
                page.setScaleX((float) (1.2 - position * 0.08));
                page.setScaleY((float) (1.2 - position * 0.08));
            }
        });
        vpCoinChaharDate.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mDates.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                RelativeLayout itemView = (RelativeLayout) getLayoutInflater().inflate(R.layout
                        .item_coin_chahar_date, container, false);
                TextView tvDate = (TextView) itemView.findViewById(R.id.tv_date);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (format.format(mDates.get(position)).equals(format.format(Calendar.getInstance().getTime()))) {
                    tvDate.setText("今日");
                } else if (format.format(mDates.get(position)).equals(format.format(calendar.getTime()))) {
                    tvDate.setText("昨日");
                } else {
                    format = new SimpleDateFormat("MMM d", Locale.ENGLISH);
                    tvDate.setText(format.format(mDates.get(position)));
                }
                container.addView(itemView);
                return itemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        fvpCoinChahar.setOffscreenPageLimit(3);
        fvpCoinChahar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vpCoinChaharDate.setCurrentItem(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fvpCoinChahar.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CoinChaharFragment coinChaharFragment = new CoinChaharFragment(CoinChaharListActivity.this);
                Bundle bundle = new Bundle();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                bundle.putString("date", format.format(mDates.get(position)));
                coinChaharFragment.setArguments(bundle);
                return coinChaharFragment;
            }

            @Override
            public int getCount() {
                return mDates.size();
            }
        });
    }

    private List<Date> getDays() {
        List<Date> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        days.add(calendar.getTime());
        for (int i = 0; i < 90 - 1; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            days.add(calendar.getTime());
        }
        return days;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            fvpCoinChahar.setPagingEnabled(true);
            flCoinChaharDate.setTranslationY(0);
        } else {
            fvpCoinChahar.setPagingEnabled(false);
            float translationY = flCoinChaharDate.getTranslationY() - dy;
            if (translationY <= 0 && translationY >= -ScreenUtil.dp2px(80)) {
                flCoinChaharDate.setTranslationY(translationY);
                if (Math.abs(translationY) >= ScreenUtil.dp2px(40)) {
                    vpCoinChaharDate.setVisibility(View.INVISIBLE);
                    int position = vpCoinChaharDate.getCurrentItem();
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if (format.format(mDates.get(position)).equals(format.format(Calendar.getInstance().getTime()))) {
                        tvTitle.setText("今日");
                    } else if (format.format(mDates.get(position)).equals(format.format(calendar.getTime()))) {
                        tvTitle.setText("昨日");
                    } else {
                        format = new SimpleDateFormat("MMM d", Locale.ENGLISH);
                        tvTitle.setText(format.format(mDates.get(position)));
                    }
                } else {
                    vpCoinChaharDate.setVisibility(View.VISIBLE);
                    tvTitle.setText("");
                }
            }
        }
    }
}
