package com.hailong.biometricprompt.fingerprint;

import android.app.Activity;
import android.content.Context;

import com.hailong.biometricprompt.fingerprint.bean.VerificationDialogStyleBean;

/**
 * Created by ZuoHailong on 2019/7/9.
 */
public interface IFingerprint {

    /**
     * 检测指纹硬件是否可用，及是否添加指纹
     * @param context
     * @param callback
     * @return
     */
    boolean canAuthenticate(Context context, FingerprintCallback callback);

    /**
     * 初始化并调起指纹验证
     *
     * @param context
     * @param verificationDialogStyleBean
     * @param callback
     */
    void authenticate(Activity context, VerificationDialogStyleBean verificationDialogStyleBean, FingerprintCallback callback);

}
