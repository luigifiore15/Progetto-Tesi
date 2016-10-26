package it.luigi.acquedotto.app.progettotesi.vista;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;


public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {
    private static final String TAG = CameraPreview.class.getName();

    SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    Camera.Size mPreviewSize;
    List<Camera.Size> mSupportedPreviewSize;
    private Camera mCamera;

    public CameraPreview(Context context, SurfaceView sv) {
        super(context);
        mSurfaceView = sv;
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCamera(Camera camera){
        //metodo per il controllo e il settaggio dei parametri della fotocamera
        mCamera = camera;
        if(mCamera != null){
            mSupportedPreviewSize = mCamera.getParameters().getSupportedPreviewSizes();
            requestLayout();
            Camera.Parameters params = mCamera.getParameters();
            List<String> focusModes = params.getSupportedFocusModes();
            if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)){
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
            params.setRotation(90);
            List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
            /*for(Camera.Size size : pictureSizes){
                Log.d(TAG, "Size: "+ size.width +"x" +size.height);
            }*/
            int last = pictureSizes.size();
            params.setPictureSize(pictureSizes.get(last - 1).width, pictureSizes.get(last - 1).height);
            mCamera.setParameters(params);
            mCamera.setDisplayOrientation(90);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
        if(mSupportedPreviewSize != null){
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSize, width, height);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed && getChildCount() > 0){
            final View child = getChildAt(0);
            final int width = r - l;
            final int height = b - t;
            int previewWidth = width;
            int previewHeight = height;
            if(mPreviewSize != null){
                previewWidth = mPreviewSize.width;
                previewHeight = mPreviewSize.height;
            }
            if(width * previewHeight > height * previewWidth){
                final int scaledChildWith = previewWidth * height / previewHeight;
                child.layout((width - scaledChildWith)/2, 0, (width + scaledChildWith)/2, height);
            }else {
                final int scaledChildHeight = previewHeight * width/ previewWidth;
                child.layout(0, (height - scaledChildHeight)/2, width, (height + scaledChildHeight)/2);
            }
        }
    }

    public  void surfaceCreated(SurfaceHolder holder){
        try {
           if(mCamera!=null){
               mCamera.setPreviewDisplay(holder);
           }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        if(mCamera!=null){
            mCamera.stopPreview();
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h){
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = w/h;
        if(sizes == null){
            return null;
        }
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;
        for (Camera.Size size : sizes){
            double ratio = size.width/size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){
       if(mCamera != null){
           Camera.Parameters parameters =  mCamera.getParameters();
           parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
           requestLayout();
           mCamera.setParameters(parameters);
           mCamera.startPreview();
       }
    }

}
