package com.black.simpleapp;

import android.graphics.Bitmap;
/**
 * Created by blackerie on 13.4.2016 г..
 */
public interface  OcrScannerListener {

    public void onOcrScanStarted(String filePath);

    public void onOcrScanFinished(Bitmap bitmap, String recognizedText);
}
