package com.wyjson.snapshotmonitor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * 截图监听+画笔标记+马赛克
 *
 * @author Wyjson
 * @version 1
 * @date 2019-11-05 16:58
 */
public class MainActivity extends AppCompatActivity {

    private ScreenShotListenManager screenShotListenManager;

    private int CODE_FOR_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_FOR_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            startListen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_FOR_WRITE_EXTERNAL_STORAGE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                startListen();
                if (screenShotListenManager != null)
                    screenShotListenManager.startListen();
            } else {
                //用户不同意，自行处理即可
                Toast.makeText(this, "不能使用截图监听,没有权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startListen() {
        screenShotListenManager = ScreenShotListenManager.newInstance(this);
        screenShotListenManager.setListener(new ScreenShotListenManager.OnScreenShotListener() {
            @Override
            public void onShot(String imagePath) {
                //具体截图进行的操作
                Toast.makeText(MainActivity.this, "path: " + imagePath, Toast.LENGTH_SHORT).show();
                Log.e("ScreenShot", "imagePath:" + imagePath);
                Intent intent = new Intent(MainActivity.this, ImageEditActivity.class);
                intent.putExtra(ImageEditActivity.INTENT_PARAM_SNAP_SHOT_PATH, imagePath);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (screenShotListenManager != null)
            screenShotListenManager.startListen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (screenShotListenManager != null)
            screenShotListenManager.stopListen();
    }
}
