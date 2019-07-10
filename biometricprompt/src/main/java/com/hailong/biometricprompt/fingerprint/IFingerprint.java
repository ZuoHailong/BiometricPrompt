package com.hailong.biometricprompt.fingerprint;

import android.app.Activity;

import com.hailong.biometricprompt.fingerprint.bean.VerificationDialogStyleBean;

/**
 * Created by ZuoHailong on 2019/7/9.
 */
public interface IFingerprint {

    /**
     * 初始化并调起指纹验证
     *
     * @param context
     * @param verificationDialogStyleBean
     * @param callback
     */
    void authenticate(Activity context, VerificationDialogStyleBean verificationDialogStyleBean, FingerprintCallback callback);

}
