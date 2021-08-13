package com.wyjson.snapshotmonitor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wyjson.snapshotmonitor.costom.PaintableImageView;


/**
 * 画笔标记+马赛克
 *
 * @author Wyjson
 * @version 1
 * @date 2019-11-05 16:59
 */
public class ImageEditActivity extends AppCompatActivity {

    public static final String INTENT_PARAM_SNAP_SHOT_PATH = "intent_param_snap_shot_path";

    private PaintableImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        String imagePath = getIntent().getStringExtra(INTENT_PARAM_SNAP_SHOT_PATH);
        imageView = findViewById(R.id.iv_image);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.withDrawLastLine();
            }
        });

        findViewById(R.id.btn_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setLineType(PaintableImageView.LineType.NormalLine);
            }
        });

        findViewById(R.id.btn_mosaic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setLineType(PaintableImageView.LineType.MosaicLine);
            }
        });

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ImageEditActivity.this, "进入反馈页面提交快照", Toast.LENGTH_SHORT).show();
                Bitmap imgBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            }
        });
    }

}
