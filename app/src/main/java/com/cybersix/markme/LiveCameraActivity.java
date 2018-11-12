package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class LiveCameraActivity extends AppCompatActivity {
    TextureView textureView = null;
    View toggleViewButton = null;
    View captureButton = null;
    CameraPreview cameraPreview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_camera);

        textureView = findViewById(R.id.liveCameraView);
        toggleViewButton = findViewById(R.id.toggleCameraButton);
        captureButton = findViewById(R.id.captureCameraButton);

        cameraPreview = new CameraPreview(this, textureView);
        cameraPreview.setToggleViewButton(toggleViewButton);
        cameraPreview.setCaptureButton(captureButton);
        cameraPreview.setOnCaptureListener(new OnCaptureListener() {
            @Override
            public void onCapture(Bitmap bitmap) {
                Intent data = new Intent();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                data.putExtra("image", bytes);

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraPreview.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraPreview.stop();
    }
}
