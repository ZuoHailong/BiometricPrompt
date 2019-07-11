package com.hailong.biometricprompt.fingerprint.bean;

/**
 * 验证窗口的样式
 * Created by ZuoHailong on 2019/7/9.
 */
public class VerificationDialogStyleBean {
    private int cancelTextColor;
    private int usepwdTextColor;
    private int fingerprintColor;
    private boolean usepwdVisible;

    public int getCancelTextColor() {
        return cancelTextColor;
    }

    public void setCancelTextColor(int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
    }

    public int getUsepwdTextColor() {
        return usepwdTextColor;
    }

    public void setUsepwdTextColor(int usepwdTextColor) {
        this.usepwdTextColor = usepwdTextColor;
    }

    public int getFingerprintColor() {
        return fingerprintColor;
    }

    public void setFingerprintColor(int fingerprintColor) {
        this.fingerprintColor = fingerprintColor;
    }

    public boolean isUsepwdVisible() {
        return usepwdVisible;
    }

    public void setUsepwdVisible(boolean usepwdVisible) {
        this.usepwdVisible = usepwdVisible;
    }
}
