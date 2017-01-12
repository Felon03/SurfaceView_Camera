package com.example.preview;

import android.hardware.Camera;

import java.util.List;

/**
 * Created by Freedom.Ly on 2017-1-12 10:31
 * Email  Freedom.JFL@Live.com
 */

public class PreviewSize {

    /**
     * 通过对比得到与宽高比最接近的预览尺寸（如果有相同尺寸，优先选择）
     *
     * @param isPortrait 是否竖屏
     * @param surfaceWidth 需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param previewSizeList 需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getBestPreviewSize(boolean isPortrait, int surfaceWidth,
                                                 int surfaceHeight, List<Camera.Size> previewSizeList) {
        int reqTmpWidth;
        int reqTmpHeight;
        // 当屏幕垂直的时候需要把宽高值进行交换，保证宽大于高
        if (isPortrait) {
            reqTmpWidth = surfaceHeight;
            reqTmpHeight = surfaceWidth;
        } else {
            reqTmpWidth = surfaceWidth;
            reqTmpHeight = surfaceHeight;
        }
        // 先查找preview中是否存在与surfaceView相同宽高的尺寸
        for (Camera.Size size : previewSizeList) {
            if ((size.width == reqTmpWidth) && (size.height == reqTmpHeight)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) reqTmpWidth) /reqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : previewSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }
        return retSize;
    }
}

/*
在初始化Camera的时候设置预览尺寸的参数：
Camera.Parameters params = mCamera.getParameters();
Camera.Size preSize = PreviewSize.getBestPreviewSize(true, screenWidth, screenHeight, params.getSupportedPreviewSizes());
params.setPreviewSize(preSize.width, preSize.height);
mCamera.setParameters(params)
 */
