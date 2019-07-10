package com.hailong.biometricprompt.fingerprint;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hailong.biometricprompt.R;
import com.hailong.biometricprompt.fingerprint.bean.VerificationDialogStyleBean;

/**
 * Created by ZuoHailong on 2019/3/12.
 */
public class FingerprintDialog extends DialogFragment {

    private static Context context;
    private static FingerprintDialog mDialog;
    private OnDialogActionListener actionListener;
    private TextView tvTip, tvCancel;
    private ImageView ivFingerprint;

    private VerificationDialogStyleBean verificationDialogStyleBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(false);
        View view = inflater.inflate(R.layout.biometricprompt_layout_fingerprint_dialog, container);
        ivFingerprint = view.findViewById(R.id.ivFingerprint);
        tvTip = view.findViewById(R.id.tvTip);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(v -> {
            if (actionListener != null)
                actionListener.onCancle();
            dismiss();
        });

        //调用者定义验证框样式
        if (verificationDialogStyleBean != null) {
            if (verificationDialogStyleBean.getCancelTextColor() != 0)
                tvCancel.setTextColor(verificationDialogStyleBean.getCancelTextColor());

            if (verificationDialogStyleBean.getFingerprintColor() != 0) {
                Drawable drawable = ivFingerprint.getDrawable();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android 5.0
                    drawable.setTint(verificationDialogStyleBean.getFingerprintColor());
                }
            }
        }

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (actionListener != null)
            actionListener.onDismiss();
    }

    public static FingerprintDialog newInstance(Context context) {
        if (mDialog == null) {
            synchronized (FingerprintDialog.class) {
                if (mDialog == null) {
                    mDialog = new FingerprintDialog();
                }
            }
        }
        FingerprintDialog.context = context;
        return mDialog;
    }

    public FingerprintDialog setActionListener(OnDialogActionListener actionListener) {
        this.actionListener = actionListener;
        return mDialog;
    }

    /**
     * 设定dialog样式
     *
     * @param bean
     */
    public FingerprintDialog setDialogStyle(VerificationDialogStyleBean bean) {
        this.verificationDialogStyleBean = bean;
        return mDialog;
    }

    /**
     * 根据指纹验证的结果更新tip的文字内容和文字颜色
     *
     * @param tip
     * @param colorId
     */
    public void setTip(String tip, @ColorRes int colorId) {
        tvTip.setText(tip);
        tvTip.setTextColor(ContextCompat.getColor(context, colorId));
    }

    public interface OnDialogActionListener {
        void onCancle();

        void onDismiss();
    }
}
