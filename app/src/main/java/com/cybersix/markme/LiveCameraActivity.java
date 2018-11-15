package com.cybersix.markme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LiveCameraActivity extends AppCompatActivity {

    private static final int MAX_IMAGE_SIZE = 64000;

    private TextureView textureView = null;
    private View toggleViewButton = null;
    private View captureButton = null;
    private CameraPreview cameraPreview = null;

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

//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 65, stream);
//                byte[] bytes = stream.toByteArray();

                // Compress and output the bitmap.
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Bitmap newBitmap = cropAndScaleImage(bitmap);

                // The quality was chosen empirically, additional testing is required.
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                byte[] bytes = out.toByteArray();

                // Debug purposes.
                //compressionTest(bitmap, 50);
                //Log.d("TEST", "SuperTest");

                data.putExtra("image", bytes);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    // Generates an image that can be found here:
    // .../data/data/com.cybersix.markme/files/test.jpeg
    // This method is for debug purposes to help determine an appropriate quality
    // for our photos.
    public void compressionTest(Bitmap bitmap, int quality) {
        String filename = getApplicationContext().getFilesDir() + "/test.jpeg";
        try (FileOutputStream out = new FileOutputStream(filename)) {

            // Scale the bitmap
            Bitmap newBitmap = cropAndScaleImage(bitmap);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);

            // Output the bitmap with request quality.
            ByteArrayOutputStream supOut = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, supOut);
            byte[] bytesTest = supOut.toByteArray();

            newBitmap.recycle(); // Destroy the original bitmap.

            Log.d("Vishal_Compressed", Integer.toString(bytesTest.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Performs the following functions in order to cutdown on the image size:
    // 1. Crops into a square from the center.
    // 2. Scales the bitmap by a percentage.
    // Inputs: imageQuality - Must be between 0-100 where 100 is maximum quality,
    // and 0 is lowest quality.
    public Bitmap cropAndScaleImage(Bitmap bitmap) {

        // Always crop the image
        Bitmap croppedBitmap = cropBitmapFromCenter(bitmap);

        // Initially the image has 1 to 1 scaling.
        Bitmap scaledBitmap = scaleBitmap(1, croppedBitmap);

        // Scale based on resolution. Add more scaling as necessary.
        // Note: The image is cropped, so width = height for this bitmap.
        if (croppedBitmap.getWidth() > 2000) {
            scaledBitmap = scaleBitmap(0.25, croppedBitmap);
        } else if (croppedBitmap.getWidth() > 1000) {
            scaledBitmap = scaleBitmap(0.5, croppedBitmap);
        }
        croppedBitmap.recycle(); // Conserve memory.

        return scaledBitmap;
    }

    // Scales down by the requested percentange while maintaining aspect ratio.
    // Ensure you recycle all bitmaps after use.
    public Bitmap scaleBitmap(double scalePercentage, Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap,
                (int)(bitmap.getWidth() * scalePercentage),
                (int)(bitmap.getHeight() * scalePercentage),
                true);
    }

    // Creates a square crop of the bitmap focused on the center of the bitmap
    // and width = height = bitmap.getWidth().
    // See the documentation for the derivation of y.
    public Bitmap cropBitmapFromCenter(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap,
                0,
                (Math.abs(bitmap.getWidth() - bitmap.getHeight()) / 2),
                bitmap.getWidth(),
                bitmap.getWidth());
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
}
