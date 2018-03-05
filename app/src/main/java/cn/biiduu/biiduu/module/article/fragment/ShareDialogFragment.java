package cn.biiduu.biiduu.module.article.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.util.ScreenUtil;

@SuppressLint("ValidFragment")
public class ShareDialogFragment extends DialogFragment {
    private OnShareClickListener mListener;

    public ShareDialogFragment(OnShareClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_share_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.AnimBottom);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2 - ScreenUtil.dp2px(40);
            window.setAttributes(lp);
        }
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().findViewById(R.id.tv_weixin).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onWeiXinClick();
            }
            dismiss();
        });
        getDialog().findViewById(R.id.tv_sina_weibo).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSinaWeiboClick();
            }
            dismiss();
        });
        getDialog().findViewById(R.id.tv_friend_circle).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onFriendCircleClick();
            }
            dismiss();
        });
        getDialog().findViewById(R.id.tv_qq).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onQQFriendClick();
            }
            dismiss();
        });
        getDialog().findViewById(R.id.tv_qzone).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onQZoneClick();
            }
            dismiss();
        });
        getDialog().findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
    }

    public interface OnShareClickListener {
        void onWeiXinClick();

        void onSinaWeiboClick();

        void onFriendCircleClick();

        void onQQFriendClick();

        void onQZoneClick();
    }
}
