package com.hailong.biometricprompt.fingerprint;

/**
 * 验证结果回调，供使用者调用
 * Created by ZuoHailong on 2019/7/9.
 */
public interface FingerprintCallback {
    /**
     * 验证成功
     */
    void onSucceeded();

    /**
     * 验证失败
     */
    void onFailed();

    /**
     * 密码登录
     */
    void onUsepwd();

    /**
     * 取消验证
     */
    void onCancel();

    /**
     * 异常，验证中的异常信息都显示在dialog上，验证前的异常信息通过此回调通知调用者
     *
     * @param errorMsg
     */
    void onError(String errorMsg);
}
