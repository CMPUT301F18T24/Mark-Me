/**
 * The following code is based off of:
 *  https://android.jlelse.eu/the-least-you-can-do-with-camera2-api-2971c8c81b8b
 * Which was authored by:
 *  Mateusz Dziubek
 *
 * The code was translated into an easy to use object
 * for later convenience.
 *
 * Ideally the developer should use the LiveCameraActivity class instead. However,
 * if he wishes to output the camera's bitmap stream into a TextureView, then
 * the CameraPreview object will do it for you.
 **/

package com.cybersix.markme.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.util.Collections;

public class CameraPreview {
    static public final int CAMERA_FACE_VIEW = 1;
    static public final int CAMERA_FRONT_VIEW = 0;
    static private final int CAMERA_REQUEST_CODE = 0;
    private Context mContext = null;
    private CameraManager mManager = null;
    private CameraDevice mDevice = null;
    private CameraCaptureSession mCaptureSession = null;
    private CaptureRequest.Builder mCaptureRequestBuider = null;
    private TextureView mTextureView = null;
    private String mID = null;
    private Size mPreviewSize = null;
    private Handler mHandler = null;
    private HandlerThread mHandlerThread = null;
    private View mCaptureButton = null;
    private View mToggleViewButton = null;
    private int mCurrentView = CAMERA_FRONT_VIEW;
    private OnCaptureListener mCaptureListener = null;

    final TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            open();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    final CameraDevice.StateCallback mDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            closeDevice(); // assuming we somehow have a pre-existing CameraDevice opened

            mDevice = camera;
            createPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            closeDevice();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            closeDevice();
        }
    };

    public CameraPreview(Activity context, TextureView textureView) {
        if (context == null || textureView == null)
            return;

        mContext = context;
        mTextureView = textureView;
        initialize();
    }

    /**
     * Performs the basic initialization for the CameraManager object.
     * This includes getting permission from the user to the camera on the phone.
     */
    public void initialize() {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

        mManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        try {
            mID = mManager.getCameraIdList()[mCurrentView];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the camera service in the given texture view.
     */
    public void start() {
        if (mTextureView == null)
            return;

        createCameraHandlerThread();
        if (mTextureView.isAvailable()) {
            open();
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    /**
     * Stops the camera service in the given texture view.
     */
    public void stop() {
        close();
        closeCameraHandlerThread();
    }

    /**
     * Sets the onClick parameter of the button to switch the next camera device by
     * invoking nextDevice(). mToggleViewButton is also set to the given button view.
     * @param button is the view which acts as the toggle button.
     */
    public void setToggleViewButton(View button) {
        if (button == null)
            return;

        mToggleViewButton = button;
        mToggleViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDevice();
            }
        });
    }

    /**
     * Sets the onClick parameter of the button to "capture" or "take a photo" in the current
     * camera device by invoking capture(). mCaptureButton is also set to the given button view.
     * @param button is the view which acts as the capture button.
     */
    public void setCaptureButton(View button) {
        if (button == null)
            return;

        mCaptureButton = button;
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });
    }

    /**
     * Sets mCaptureListener to the supplied listener. The listener's method will be called
     * when the capture() method is invoked.
     * @param listener
     */
    public void setOnCaptureListener(OnCaptureListener listener) {
        mCaptureListener = listener;
    }

    /**
     * @return mToggleViewButton
     */
    public View getToggleViewButton() {
        return mToggleViewButton;
    }

    /**
     * @return mCaptureButton
     */
    public View getCaptureButton() {
        return mCaptureButton;
    }

    /**
     * @return the current bitmap being displayed on the given TextureView.
     */
    public Bitmap getBitmap() {
        return mTextureView.getBitmap();
    }

    /**
     * @return mContext
     */
    public Context getContext() {
        return mContext;
    }

    public int getCurrentView() {
        return mCurrentView;
    }

    /**
     * Safely stops the capture session on the current device before switching
     * and starting the capture session on the next camera device.
     *
     * Currently there are only two camera devices that are considered on the phone:
     *  The "Front" camera device and the "Face" camera device.
     */
    public void nextDevice() {
        if (mManager == null)
            return;

        if (mCurrentView == CAMERA_FRONT_VIEW)
            mCurrentView = CAMERA_FACE_VIEW;
        else
            mCurrentView = CAMERA_FRONT_VIEW;

        stop();
        try {
            mID = mManager.getCameraIdList()[mCurrentView];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        start();
    }

    /**
     * Captures the photo on the current capture session.
     * Consequently, the onCapture method is called, assuming it exists.
     */
    public void capture() {
        try {
            mCaptureSession.capture(
                    mCaptureRequestBuider.build(),
                    null,
                    mHandler
            );
            if (mCaptureListener != null)
                mCaptureListener.onCapture(getBitmap());
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supposedly the capture session is "locked" after a capture occurs (this is to be tested).
     * If so, we can restart the capture session using this method.
     */
    public void restartCaptureSession() {
        try {
            mCaptureSession.setRepeatingRequest(
                    mCaptureRequestBuider.build(),
                    null,
                    mHandler
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Safely opens the camera using the CameraManger.openCamera(), assuming permission is given.
     */
    private void open() {
        if (mManager == null)
            return;

        try {
            CameraCharacteristics cc = mManager.getCameraCharacteristics(mID);
            StreamConfigurationMap streamConfigurationMap = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mPreviewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                mManager.openCamera(mID, mDeviceStateCallback, mHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the capture session and camera device.
     */
    private void close() {
        closeCaptureSession();
        closeDevice();
    }

    /**
     * Safely closes the capture session.
     */
    private void closeCaptureSession() {
        if (mCaptureSession == null)
            return;

        mCaptureSession.close();
        mCaptureSession = null;
    }

    /**
     * Safely closes the camera device.
     */
    private void closeDevice() {
        if (mDevice == null)
            return;

        mDevice.close();
        mDevice = null;
    }

    /**
     * Safely creates a camera handler thread to take care of all the camera processing.
     */
    private void createCameraHandlerThread() {
        closeCameraHandlerThread(); // close any pre-existing thread, if it somehow exists.

        mHandlerThread = new HandlerThread("camera_handler_thread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    /**
     * Safely closes the camera handler thread.
     */
    private void closeCameraHandlerThread() {
        if (mHandlerThread == null)
            return;

        mHandlerThread.quitSafely();
        mHandlerThread = null;
        mHandler = null;
    }

    /**
     * Creates a preview capture session which allows us to stream the contents of the camera directly to
     * the mTextureView.
     */
    private void createPreviewSession() {
        if (mTextureView == null || mPreviewSize == null)
            return;

        try {
            SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);

            mCaptureRequestBuider = mDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuider.addTarget(previewSurface);

            mDevice.createCaptureSession(Collections.singletonList(previewSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    CaptureRequest captureRequest = mCaptureRequestBuider.build();
                    mCaptureSession = session;
                    try {
                        mCaptureSession.setRepeatingRequest(captureRequest, null, mHandler);
                    } catch (CameraAccessException e) { e.printStackTrace(); }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    return;
                }
            }, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public interface OnCaptureListener {
        void onCapture(Bitmap bitmap);
    }
}
