package com.hailong.biometricprompt.fingerprint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import com.hailong.biometricprompt.R;
import com.hailong.biometricprompt.fingerprint.bean.VerificationDialogStyleBean;

/**
 * Android M == 6.0
 * Created by ZuoHailong on 2019/7/9.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintAndrM implements IFingerprint {

    private final String TAG = FingerprintAndrM.class.getName();
    private Activity context;

    private static FingerprintAndrM fingerprintAndrM;
    //指纹验证框
    private static FingerprintDialog fingerprintDialog;
    //指向调用者的指纹回调
    private FingerprintCallback fingerprintCallback;

    //用于取消扫描器的扫描动作
    private CancellationSignal cancellationSignal;
    //指纹加密
    private static FingerprintManagerCompat.CryptoObject cryptoObject;
    //Android 6.0 指纹管理
    private FingerprintManagerCompat fingerprintManagerCompat;

    @Override
    public void authenticate(Activity context, VerificationDialogStyleBean bean, FingerprintCallback callback) {
        this.context = context;
        this.fingerprintCallback = callback;
        //Android 6.0 指纹管理 实例化
        fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        //硬件是否支持指纹识别
        if (!fingerprintManagerCompat.isHardwareDetected()) {
            callback.onError(context.getString(R.string.biometricprompt_verify_error_no_hardware));
            return;
        }
        //是否已添加指纹
        if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
            AlertDialog.Builder lertDialogBuilder = new AlertDialog.Builder(context);
            lertDialogBuilder.setTitle(context.getString(R.string.biometricprompt_tip))
                    .setMessage(context.getString(R.string.biometricprompt_finger_add))
                    .setCancelable(false)
                    .setNegativeButton(context.getString(R.string.biometricprompt_finger_add_confirm), ((DialogInterface dialog, int which) -> {
                        Intent intent = new Intent(Settings.ACTION_FINGERPRINT_ENROLL);
                        context.startActivity(intent);
                    }
                    ))
                    .setPositiveButton(context.getString(R.string.biometricprompt_cancel), ((DialogInterface dialog, int which) -> {
                        dialog.dismiss();
                    }
                    ))
                    .create().show();
            return;
        }
        //取消扫描，每次取消后需要重新创建新示例
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(() -> fingerprintDialog.dismiss());

        //调起指纹验证
        fingerprintManagerCompat.authenticate(cryptoObject, 0, cancellationSignal, authenticationCallback, null);
        //指纹验证框
        fingerprintDialog = FingerprintDialog.newInstance(context).setActionListener(dialogActionListener).setDialogStyle(bean);
        fingerprintDialog.show(context.getFragmentManager(), TAG);
    }

    public static FingerprintAndrM newInstance() {
        if (fingerprintAndrM == null) {
            synchronized (FingerprintAndrM.class) {
                if (fingerprintAndrM == null) {
                    fingerprintAndrM = new FingerprintAndrM();
                }
            }
        }
        //指纹加密，提前进行Cipher初始化，防止指纹认证时还没有初始化完成
        try {
            cryptoObject = new CryptoObjectHelper().buildCryptoObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fingerprintAndrM;
    }

    /**
     * 指纹验证框按键监听
     */
    private FingerprintDialog.OnDialogActionListener dialogActionListener = new FingerprintDialog.OnDialogActionListener() {
        @Override
        public void onUsepwd() {
            if (fingerprintCallback != null)
                fingerprintCallback.onUsepwd();
        }

        @Override
        public void onCancle() {//取消指纹验证，通知调用者
            if (fingerprintCallback != null)
                fingerprintCallback.onCancel();
        }

        @Override
        public void onDismiss() {//验证框消失，取消指纹验证
            if (cancellationSignal != null && !cancellationSignal.isCanceled())
                cancellationSignal.cancel();
        }
    };

    /**
     * 指纹验证结果回调
     */
    private FingerprintManagerCompat.AuthenticationCallback authenticationCallback = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            fingerprintDialog.setTip(errString.toString(), R.color.biometricprompt_color_FF5555);
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            fingerprintDialog.setTip(helpString.toString(), R.color.biometricprompt_color_FF5555);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            fingerprintDialog.setTip(context.getString(R.string.biometricprompt_verify_success), R.color.biometricprompt_color_82C785);
            fingerprintCallback.onSucceeded();
            fingerprintDialog.dismiss();
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            fingerprintDialog.setTip(context.getString(R.string.biometricprompt_verify_failed), R.color.biometricprompt_color_FF5555);
            fingerprintCallback.onFailed();
        }
    };


}
