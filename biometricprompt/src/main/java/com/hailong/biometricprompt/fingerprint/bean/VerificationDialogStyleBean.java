package com.hailong.biometricprompt.fingerprint.bean;

/**
 * 验证窗口的样式
 * Created by ZuoHailong on 2019/7/9.
 */
public class VerificationDialogStyleBean {

    /**
     * android 6.0
     */
    private int cancelTextColor;
    private int usepwdTextColor;
    private int fingerprintColor;
    private boolean usepwdVisible;

    /**
     * android 9.0
     *
     * @return
     */
    private String title;
    private String subTitle;
    private String description;
    private String cancelBtnText;//取消按钮文字

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCancelBtnText() {
        return cancelBtnText;
    }

    public void setCancelBtnText(String cancelBtnText) {
        this.cancelBtnText = cancelBtnText;
    }
}
