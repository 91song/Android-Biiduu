package cn.biiduu.biiduu.base;

import android.os.Bundle;

import com.marno.easystatelibrary.EasyStatusView;
import com.marno.rapidlib.basic.BasicActivity;

import butterknife.BindView;
import cn.biiduu.biiduu.R;

public abstract class BaseLoadActivity extends BasicActivity {
    @BindView(R.id.esv_status)
    protected EasyStatusView esvStatus;

    @Override
    protected void initView(Bundle savedInstanceState) {
        esvStatus.loading();
        initView();
    }

    protected abstract void initView();
}
