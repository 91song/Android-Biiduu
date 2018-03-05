package cn.biiduu.biiduu.module.cointing.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import cn.biiduu.biiduu.R;
import cn.biiduu.biiduu.util.ScreenUtil;

@SuppressLint("ValidFragment")
public class FeedbackDialogFragment extends DialogFragment {
    private EditText etContent;
    private OnCommitClickListener mListener;

    public FeedbackDialogFragment(OnCommitClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_feedback_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.AnimBottom);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = getActivity().getWindowManager().getDefaultDisplay().getWidth() - ScreenUtil.dp2px(40);
            lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2;
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);
        }
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etContent = (EditText) getDialog().findViewById(R.id.et_content);
        getDialog().findViewById(R.id.ib_close).setOnClickListener(v -> dismiss());
        getDialog().findViewById(R.id.tv_commit).setOnClickListener(v -> {
            if (checkContent()) {
                if (mListener != null) {
                    mListener.onCommitClick(etContent.getText().toString().trim());
                }
                dismiss();
            }
        });
    }

    private boolean checkContent() {
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content.trim())) {
            Toast.makeText(getContext(), "反馈内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public interface OnCommitClickListener {
        void onCommitClick(String content);
    }
}
