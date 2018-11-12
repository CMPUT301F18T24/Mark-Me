package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

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
