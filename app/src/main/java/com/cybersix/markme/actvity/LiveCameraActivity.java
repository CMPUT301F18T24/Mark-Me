/**
 * This activity should be used to take photos using the phone's camera.
 *
 * It's meant to give easy access to the captured photo by returning the bitmap
 * through the resulting intent.
 *
 * Passing an intent containing the resource id of an @drawable will overlay
 * that @drawable on top of the TextureView containing the camera's output.
 *
 * Issues: - The photo compression has extremely varied results and it needs more work to ensure
 * consistency of photo size that's returned.
 */

package com.cybersix.markme.actvity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.cybersix.markme.controller.CameraPreview;
import com.cybersix.markme.utils.GuiUtils;
import com.cybersix.markme.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LiveCameraActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE = "image";
    public static final String OVERLAY_RESOURCE_ID = "OVERLAY_RESOURCE_ID";
    public static final int IMAGE_QUALITY = 75;
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

//        textureView = findViewById(R.id.liveCameraView);
//        toggleViewButton = findViewById(R.id.toggleCameraButton);
//        captureButton = findViewById(R.id.captureCameraButton);
//        overlayView = findViewById(R.id.overlayView);
//
//        Intent intent = getIntent();
//        int overlay_id = intent.getIntExtra(OVERLAY_RESOURCE_ID, 0);
//        if (overlay_id != 0) {
//            overlayView.setImageResource(overlay_id);
//            overlayView.setAlpha((float) 0.75);
//        }
//
//        cameraPreview = new CameraPreview(this, textureView);
//        cameraPreview.setToggleViewButton(toggleViewButton);
//        cameraPreview.setCaptureButton(captureButton);
//        cameraPreview.setOnCaptureListener(new CameraPreview.OnCaptureListener() {
//                    @Override
//                    public void onCapture(Bitmap bitmap) {
//                        Intent data = new Intent();
//
//                        // Empirical result: 75% seems to be working out well.
//                        byte[] bytes = compressBitmap(bitmap, IMAGE_QUALITY);
//
//                        // Debug purposes. Use this for testing compression quality. Comment out
//                        // everything else to avoid crashes due to bitmaps getting recycled.
//                        // compressionTest(bitmap, 10);
//
//                        data.putExtra(EXTRA_IMAGE, bytes);
//                        setResult(RESULT_OK, data);
//                        finish();
//
//                    }
//        });

        testEVERYTHING();
    }

    public void testEVERYTHING() {

        // 1-12 real quick boi
        for (int i = 1; i <= 12; i++) {
            // Generate the appropriate standard file name.
            String filename = getApplicationContext().getFilesDir() + "/picsum.photos_" + i + ".jpg";
            File file = new File(filename);

            try { // Open the file.

                // Read and decode the file
                //BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap myBoi =  BitmapFactory.decodeStream(new FileInputStream(file)); // Let android decode it

                // Print the original sizes
                ByteArrayOutputStream supIn = new ByteArrayOutputStream();
                myBoi.compress(Bitmap.CompressFormat.JPEG, 75, supIn);
                byte[] bytesTestIn = supIn.toByteArray();
                Log.d("Vishal_B_" + i, Integer.toString(bytesTestIn.length));

                // Scale the bitmap
                Bitmap newBitmap = scaleImage(myBoi);

                // Output the bitmap with request quality.
                ByteArrayOutputStream supOut = new ByteArrayOutputStream();
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 75, supOut);
                byte[] bytesTest = supOut.toByteArray();

                newBitmap.recycle(); // Destroy the original bitmap.

                Log.d("Vishal_A_" + i, Integer.toString(bytesTest.length));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public byte[] compressBitmap(Bitmap bitmap,  int quality) {
        // Compress and output the bitmap.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Bitmap newBitmap = scaleImage(bitmap);

        // The quality was chosen empirically, additional testing is required.
        newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);


        return out.toByteArray();
    }

    /**
     * Generates an image that can be found here:
     * .../data/data/com.cybersix.markme/files/test.jpeg
     * This method is for debug purposes to help determine an appropriate quality
     * for our photos. It prints the # of bytes needed for the corresponding quality of bitmap.
     * @param bitmap The raw bitmap to compress.
     * @param quality The quality to which we want to compress the bitmap.
     */
    public void DEBUGONLY_compressionTest(Bitmap bitmap, int quality) {
        String filename = getApplicationContext().getFilesDir() + "/test_" + quality + ".jpeg";
        try (FileOutputStream out = new FileOutputStream(filename)) {

            // Scale the bitmap
            Bitmap newBitmap = scaleImage(bitmap);
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

    /**
     * Performs the following functions in order to cutdown on the image size:
     * 1. Crops into a square from the center.
     * 2. Scales the bitmap by a percentage.
     *
     * A good size appears to be ~750px.
     * @param bitmap The original bitmap.
     * @return Cropped and scaled bitmap.
     */
    public Bitmap scaleImage(Bitmap bitmap) {

        //Log.d("Vishal_info", "Height: " + bitmap.getHeight() + " Width: " + bitmap.getWidth());
        // Initially the image has 1 to 1 scaling.
        Bitmap scaledBitmap = scale(1, bitmap);

        // Scale based on resolution. Add more scaling as necessary.
        if (bitmap.getWidth() > 2000) {
            scaledBitmap = scale(0.12, bitmap);
        } else if (bitmap.getWidth() > 1000) {
            scaledBitmap = scale(0.25, bitmap);
        }

        bitmap.recycle(); // Conserve memory by destroying the original.

        return scaledBitmap;
    }

    /**
     * Scales down by the requested percentage while maintaining aspect ratio.
     * Note: Save the result to a new bitmap and ensure you recycle the original bitmap.
     * @param scalePercentage Percent value to scale bitmap down. ex) 0.5 = 50% reduction in size.
     * @param bitmap The original bitmap.
     * @return The scaled bitmap.
     */
    public Bitmap scale(double scalePercentage, Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap,
                (int)(bitmap.getWidth() * scalePercentage),
                (int)(bitmap.getHeight() * scalePercentage),
                true);
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
