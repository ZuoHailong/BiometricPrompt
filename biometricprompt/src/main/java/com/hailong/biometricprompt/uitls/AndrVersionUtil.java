package com.hailong.biometricprompt.uitls;

import android.os.Build;

/**
 * Created by ZuoHailong on 2019/7/9.
 */
public class AndrVersionUtil {
    /**
     * 高于Android P（9.0）
     *
     * @return
     */
    public static boolean isAboveAndrP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 高于Android M（6.0）
     *
     * @return
     */
    public static boolean isAboveAndrM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
