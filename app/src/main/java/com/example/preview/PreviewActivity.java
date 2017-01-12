package com.example.preview;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Freedom.Ly on 2017-1-10 16:29
 * Email  Freedom.JFL@Live.com
 */
public class PreviewActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    Camera mCamera;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    int mWidth;
    int mHeight;

    private final String TAG = "Preview";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        initViews();
    }

    private void initViews() {
        mSurfaceView = (SurfaceView) findViewById(R.id.preview_sv);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mCamera = getCamera();
//        ViewTreeObserver vto = mSurfaceView.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mSurfaceView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                mWidth = mSurfaceView.getWidth();
//                mHeight = mSurfaceView.getHeight();
//                Log.i(TAG, "onGlobalLayout: w = " + mWidth + " h = " + mHeight);
//            }
//        });
    }

    /**
     * 初始化相机
     */

    private Camera getCamera() {
//        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Log.i(TAG, "getCamera: width = " + width + "height = " + height);

        Camera camera;
        try {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            Camera.Parameters params = camera.getParameters();
            Camera.Size preSize = PreviewSize.getBestPreviewSize(true, width, height,
                    params.getSupportedPreviewSizes());
            params.setPreviewSize(preSize.width, preSize.height);
            Log.i(TAG, "getCamera: preSize.width = " + preSize.width + " preSize.height = " + preSize.height);
            camera.setParameters(params);
        } catch (Exception e) {
            camera = null;
        }
        return camera;
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 在SurfaceView中预览相机内容
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
            camera.setOneShotPreviewCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查是否具有相机功能
     */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated: ");
        setStartPreview(mCamera, mSurfaceHolder);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        if (null == mSurfaceHolder.getSurface()) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStartPreview(mCamera, mSurfaceHolder);
        Log.d(TAG, "surfaceChanged: ");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
        Log.d(TAG, "surfaceDestroyed: ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.checkCameraHardware(this) && (null == mCamera)) {
            mCamera = getCamera();
            if (null != mSurfaceHolder) {
                setStartPreview(mCamera, mSurfaceHolder);
            }
        }
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
        Log.d(TAG, "onDestroy: ");
    }



    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
//        Thread thread = new Thread();
//        try {
//            thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Log.d(TAG, "onPreviewFrame: Get Preview Frame");
//        String data  = Arrays.toString(bytes);
//        Log.i(TAG, "onPreviewFrame: data = " + data);
//        data = null;

    }




//    private void getPreviewImage() {
//        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
//            @Override
//            public void onPreviewFrame(byte[] bytes, Camera camera) {
//                Camera.Size size = camera.getParameters().getPreviewSize();
//                try {
//                    YuvImage image = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
//                    if (null != image) {
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
//
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
//                        // 旋转图片
//                        Matrix matrix = new Matrix();
//                        matrix.postRotate(90);
//
//                        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//                        Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }


}
