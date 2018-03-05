package cn.biiduu.biiduu.base;

import android.os.Bundle;
import android.view.View;

import com.marno.easystatelibrary.EasyStatusView;
import com.marno.rapidlib.basic.BasicFragment;

import butterknife.BindView;
import cn.biiduu.biiduu.R;

public abstract class BaseLoadFragment extends BasicFragment {
    @BindView(R.id.esv_status)
    protected EasyStatusView esvStatus;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        esvStatus.loading();
        initView(view);
    }

    protected abstract void initView(View view);
}
