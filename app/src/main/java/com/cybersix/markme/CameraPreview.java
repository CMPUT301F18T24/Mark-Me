package com.cybersix.markme;

import android.Manifest;
import android.content.pm.PackageManager;
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

import java.util.Collections;

public class CameraPreview {
    CameraManager mManager = null;
    CameraDevice mDevice = null;
    CameraCaptureSession mCaptureSession = null;
    TextureView mTextureView = null;
    String mID = null;
    Size mPreviewSize = null;
    Handler mHandler = null;
    HandlerThread mHandlerThread = null;

    final TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
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

    final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            if (mDevice != null) {
                mDevice.close();
            }

            mDevice = camera;
            createPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            if (mDevice == null)
                return;

            mDevice.close();
            mDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            if (mDevice == null)
                return;

            mDevice.close();
            mDevice = null;
        }
    };

    public void open() {
        try {
            CameraCharacteristics cc = cameraManager.getCameraCharacteristics(cameraID);
            StreamConfigurationMap streamConfigurationMap = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(cameraID, stateCallback, cameraHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void closeCamera() {
        cameraCaptureSession.close();
        cameraCaptureSession = null;

        cameraDevice.close();
        cameraDevice = null;
    }

    private void createCameraHandlerThread() {
        cameraHandlerThread = new HandlerThread("camera_handler_thread");
        cameraHandlerThread.start();
        cameraHandler = new Handler(cameraHandlerThread.getLooper());
    }

    private void closeCameraHandlerThread() {
        cameraHandlerThread.quitSafely();
        cameraHandlerThread = null;
        cameraHandler = null;
    }

    private void createPreviewSession() {
        try {
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            final CaptureRequest.Builder captureRequestBuider = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuider.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Collections.singletonList(previewSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    CaptureRequest captureRequest = captureRequestBuider.build();
                    cameraCaptureSession = session;
                    try {
                        cameraCaptureSession.setRepeatingRequest(captureRequest, null, cameraHandler);
                    } catch (CameraAccessException e) { e.printStackTrace(); }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    return;
                }
            }, cameraHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
