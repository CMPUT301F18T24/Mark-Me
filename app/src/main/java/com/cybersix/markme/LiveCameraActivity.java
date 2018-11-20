/**
 * This activity should be used to take photos using the phone's camera.
 *
 * It's meant to give easy access to the captured photo by returning the bitmap
 * through the resulting intent.
 *
 * Passing an intent containing the resource id of an @drawable will overlay
 * that @drawable on top of the TextureView containing the camera's output.
 */

package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class LiveCameraActivity extends AppCompatActivity {
    public static final String OVERLAY_RESOURCE_ID = "OVERLAY_RESOURCE_ID";
    private TextureView textureView = null;
    private View toggleViewButton = null;
    private View captureButton = null;
    private ImageView overlayView = null;
    private CameraPreview cameraPreview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_camera);
        GuiUtils.setFullScreen(this);

        textureView = findViewById(R.id.liveCameraView);
        toggleViewButton = findViewById(R.id.toggleCameraButton);
        captureButton = findViewById(R.id.captureCameraButton);
        overlayView = findViewById(R.id.overlayView);

        Intent intent = getIntent();
        int overlay_id = intent.getIntExtra(OVERLAY_RESOURCE_ID, 0);
        if (overlay_id != 0) {
            overlayView.setImageResource(overlay_id);
            overlayView.setAlpha((float) 0.75);
        }

        cameraPreview = new CameraPreview(this, textureView);
        cameraPreview.setToggleViewButton(toggleViewButton);
        cameraPreview.setCaptureButton(captureButton);
        cameraPreview.setOnCaptureListener(new OnCaptureListener() {
                    @Override
                    public void onCapture(Bitmap bitmap) {
                        Intent data = new Intent();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
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

    public CameraPreview getCameraPreview() {
        return cameraPreview;
    }

    public ImageView getOverlayView() {
        return overlayView;
    }
}
