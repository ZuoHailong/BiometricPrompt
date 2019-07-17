package com.hailong.biometricprompt.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.hailong.biometricprompt.fingerprint.FingerprintCallback;
import com.hailong.biometricprompt.fingerprint.FingerprintVerifyManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tvFingerprint).setOnClickListener(v -> {
            FingerprintVerifyManager.Builder builder = new FingerprintVerifyManager.Builder(MainActivity.this);
            builder.callback(fingerprintCallback)
                    .cancelTextColor(ContextCompat.getColor(MainActivity.this, R.color.biometricprompt_color_primary))
                    .usepwdTextColor(ContextCompat.getColor(MainActivity.this, R.color.biometricprompt_color_FF5555))
                    .fingerprintColor(ContextCompat.getColor(MainActivity.this, R.color.biometricprompt_color_primary))
                    .usepwdVisible(true)
                    .enableAndroidP(true)
                    .build();
        });
    }

    private FingerprintCallback fingerprintCallback = new FingerprintCallback() {
        @Override
        public void onSucceeded() {
            Toast.makeText(MainActivity.this, getString(R.string.biometricprompt_verify_success), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            Toast.makeText(MainActivity.this, getString(R.string.biometricprompt_verify_failed), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUsepwd() {
            Toast.makeText(MainActivity.this, getString(R.string.fingerprint_usepwd), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, getString(R.string.fingerprint_cancel), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onHwUnavailable() {
            Toast.makeText(MainActivity.this, getString(R.string.biometricprompt_finger_hw_unavailable), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNoneEnrolled() {
            //弹出提示框，跳转指纹添加页面
            AlertDialog.Builder lertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            lertDialogBuilder.setTitle(getString(R.string.biometricprompt_tip))
                    .setMessage(getString(R.string.biometricprompt_finger_add))
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.biometricprompt_finger_add_confirm), ((DialogInterface dialog, int which) -> {
                        Intent intent = new Intent(Settings.ACTION_FINGERPRINT_ENROLL);
                        startActivity(intent);
                    }
                    ))
                    .setPositiveButton(getString(R.string.biometricprompt_cancel), ((DialogInterface dialog, int which) -> {
                        dialog.dismiss();
                    }
                    ))
                    .create().show();
        }

        @Override
        public void onError(int msgId, String errorMsg) {
            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }

    };
}
