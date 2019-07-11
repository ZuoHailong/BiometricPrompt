package com.hailong.biometricprompt.fingerprint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.hailong.biometricprompt.R;
import com.hailong.biometricprompt.fingerprint.bean.VerificationDialogStyleBean;
import com.hailong.biometricprompt.uitls.AndrVersionUtil;

/**
 * Created by ZuoHailong on 2019/7/9.
 */
public class FingerprintVerifyManager {

    public FingerprintVerifyManager(Builder builder) {
        IFingerprint fingerprint;
        if (AndrVersionUtil.isAboveAndrP()) {
            fingerprint = FingerprintAndrM.newInstance();
        } else if (AndrVersionUtil.isAboveAndrM()) {
            fingerprint = FingerprintAndrM.newInstance();
        } else {//Android 6.0 以下官方未开放指纹识别，某些机型自行支持的情况暂不做处理
            builder.callback.onError(builder.context.getString(R.string.biometricprompt_verify_error_below_m));
            return;
        }
        //设定指纹验证框的样式
        VerificationDialogStyleBean bean = new VerificationDialogStyleBean();
        bean.setCancelTextColor(builder.cancelTextColor);
        bean.setFingerprintColor(builder.fingerprintColor);
        bean.setUsepwdVisible(builder.usepwdVisible);

        fingerprint.authenticate(builder.context, bean, builder.callback);
    }

    /**
     * UpdateAppManager的构建器
     */
    public static class Builder {

        /*必选字段*/
        private Activity context;
        private FingerprintCallback callback;

        /*可选字段*/
        private int cancelTextColor;
        private int fingerprintColor;
        private boolean usepwdVisible;

        /**
         * 构建器
         *
         * @param activity
         */
        public Builder(@NonNull Activity activity) {
            this.context = activity;
        }

        /**
         * 指纹识别回调
         *
         * @param callback
         */
        public Builder callback(@NonNull FingerprintCallback callback) {
            this.callback = callback;
            return this;
        }

        /**
         * 取消按钮文本色
         *
         * @param color
         */
        public Builder cancelTextColor(@ColorInt int color) {
            this.cancelTextColor = color;
            return this;
        }

        /**
         * 指纹图标颜色
         *
         * @param color
         */
        public Builder fingerprintColor(@ColorInt int color) {
            this.fingerprintColor = color;
            return this;
        }

        /**
         * 密码登录按钮是否显示
         *
         * @param isVisible
         */
        public Builder usepwdVisible(boolean isVisible) {
            this.usepwdVisible = isVisible;
            return this;
        }


        /**
         * 开始构建
         *
         * @return
         */
        public FingerprintVerifyManager build() {
            return new FingerprintVerifyManager(this);
        }
    }

}
