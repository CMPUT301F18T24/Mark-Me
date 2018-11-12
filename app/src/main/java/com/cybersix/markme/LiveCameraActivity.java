package com.cybersix.markme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;

public class LiveCameraActivity extends AppCompatActivity {
    TextureView textureView = null;
    CameraPreview cameraPreview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_camera);
        textureView = findViewById(R.id.liveCameraView);

        cameraPreview = new CameraPreview(this, textureView);
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
