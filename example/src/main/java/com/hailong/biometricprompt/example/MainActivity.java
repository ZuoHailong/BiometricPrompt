package com.hailong.biometricprompt.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
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
        public void onError(String errorMsg) {
            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }

    };
}
