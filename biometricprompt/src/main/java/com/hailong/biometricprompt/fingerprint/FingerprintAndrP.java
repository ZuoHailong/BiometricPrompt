package com.hailong.biometricprompt.fingerprint;

import android.app.Activity;
import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.hailong.biometricprompt.R;
import com.hailong.biometricprompt.fingerprint.bean.VerificationDialogStyleBean;
import com.hailong.biometricprompt.uitls.CipherHelper;

/**
 * Android P == 9.0
 * Created by ZuoHailong on 2019/7/9.
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class FingerprintAndrP implements IFingerprint {

    private static FingerprintAndrP fingerprintAndrP;
    //指向调用者的指纹回调
    private FingerprintCallback fingerprintCallback;

    //用于取消扫描器的扫描动作
    private CancellationSignal cancellationSignal;
    //指纹加密
    private static BiometricPrompt.CryptoObject cryptoObject;

    @Override
    public void authenticate(Activity context, VerificationDialogStyleBean verificationDialogStyleBean, FingerprintCallback callback) {

        this.fingerprintCallback = callback;

        /*
         * 初始化 BiometricPrompt.Builder
         */
        String title = TextUtils.isEmpty(verificationDialogStyleBean.getTitle()) ?
                context.getString(R.string.biometricprompt_fingerprint_verification) :
                verificationDialogStyleBean.getTitle();
        String cancelText = TextUtils.isEmpty(verificationDialogStyleBean.getCancelBtnText()) ?
                context.getString(R.string.biometricprompt_cancel) :
                verificationDialogStyleBean.getCancelBtnText();
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(context)
                .setTitle(title)
                .setNegativeButton(cancelText, command -> {
                }, (dialog, which) -> {
                });
        if (!TextUtils.isEmpty(verificationDialogStyleBean.getSubTitle()))
            builder.setSubtitle(verificationDialogStyleBean.getSubTitle());
        if (!TextUtils.isEmpty(verificationDialogStyleBean.getDescription()))
            builder.setDescription(verificationDialogStyleBean.getDescription());

        //构建 BiometricPrompt
        BiometricPrompt biometricPrompt = builder.build();

        //取消扫描，每次取消后需要重新创建新示例
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(() -> {
        });

        /*
         * 拉起指纹验证模块，等待验证
         * Executor：
         * context.getMainExecutor()
         */
        biometricPrompt.authenticate(cryptoObject, cancellationSignal, context.getMainExecutor(), authenticationCallback);
    }

    public static FingerprintAndrP newInstance() {
        if (fingerprintAndrP == null) {
            synchronized (FingerprintAndrM.class) {
                if (fingerprintAndrP == null) {
                    fingerprintAndrP = new FingerprintAndrP();
                }
            }
        }
        //指纹加密，提前进行Cipher初始化，防止指纹认证时还没有初始化完成
        try {
            cryptoObject = new BiometricPrompt.CryptoObject(new CipherHelper().createCipher());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fingerprintAndrP;
    }

    /**
     * 认证结果回调
     */
    private BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            if (fingerprintCallback != null) {
                if (errorCode == 5) {//用户取消指纹验证，不必向用户抛提示信息
                    fingerprintCallback.onCancel();
                    return;
                }
            }
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            if (fingerprintCallback != null)
                fingerprintCallback.onSucceeded();
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            if (fingerprintCallback != null)
                fingerprintCallback.onFailed();
        }
    };

    /*
     * 在 Android Q，Google 提供了 Api BiometricManager.canAuthenticate() 用来检测指纹识别硬件是否可用及是否添加指纹
     * 不过尚未开放，标记为"Stub"(存根)
     * 所以暂时还是需要使用 Andorid 6.0 的 Api 进行判断
     * */
    @Override
    public boolean canAuthenticate(Context context, FingerprintCallback fingerprintCallback) {

        /*
         * 硬件是否支持指纹识别
         * */
        if (!FingerprintManagerCompat.from(context).isHardwareDetected()) {
            fingerprintCallback.onHwUnavailable();
            return false;
        }
        //是否已添加指纹
        if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
            fingerprintCallback.onNoneEnrolled();
            return false;
        }
        return true;
    }

}