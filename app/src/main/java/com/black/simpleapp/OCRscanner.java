package com.black.simpleapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
/**
 * Created by blackerie on 13.4.2016 г..
 */
public class OCRscanner {
    protected Activity mActivity;
    private String directoryPathOriginal;
    private String filePathOriginal;
    private int requestCode;
    private OcrScannerListener mOcrScannerListener;
    private String trainedDataCode;

    public OCRscanner(Activity activity, String directoryPath, int requestCode, String trainedDataCode){
        this.mActivity = activity;
        this.directoryPathOriginal = directoryPath;
        this.requestCode = requestCode;
        this.trainedDataCode = trainedDataCode;
    }
    //method to take pictures
    public void takePicture(){
        Intent e = new Intent("android.media.action.IMAGE_CAPTURE");
        this.filePathOriginal = FileUtils.getDirectory(this.directoryPathOriginal) + File.separator + Calendar.getInstance().getTimeInMillis() + ".jpg";
        e.putExtra("output", Uri.fromFile(new File(this.filePathOriginal)));

        startActivity(e);
    }

    public void onImageTaken(){
        Log.d(Config.TAG, "onImageTaken with path " + this.filePathOriginal);
        ImageProcessingThread thread = new ImageProcessingThread(this.mOcrScannerListener,
                this.filePathOriginal, this.directoryPathOriginal, this.mActivity, this.trainedDataCode);
        thread.execute();
    }

    private void startActivity(Intent intent){
        if(this.mActivity != null) {
            this.mActivity.startActivityForResult(intent, this.requestCode);
        }
    }

    public void setOcrScannerListener(OcrScannerListener mOcrScannerListener) {
        this.mOcrScannerListener = mOcrScannerListener;
    }

    //method to select pictures from gallery
    public void selectFromGallery(){
        Intent e = new Intent();
        e.setType("image/*");
        e.setAction(Intent.ACTION_GET_CONTENT);
        startActivityGallery(e);
/*        Intent chooser = Intent.createChooser(e, getString(R.string.image_source));
        try {
            startActivityForResult(chooser, REQUEST_CODE_PICK_PHOTO);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_gallery_found, Toast.LENGTH_LONG).show();
        }*/
    }
    private void startActivityGallery(Intent intent){
        if(this.mActivity != null) {
            this.mActivity.startActivityForResult(intent, this.requestCode);
        }
    }

    /*public void onSelectedImage(){
        Log.d(Config.TAG, "onSelectedImage with path " + this.filePathOriginal);

        filePathOriginal = Uri selected
        ImageProcessingThread thread = new ImageProcessingThread(this.mOcrScannerListener,
                this.filePathOriginal, this.directoryPathOriginal, this.mActivity, this.trainedDataCode);
        thread.execute();
    }*/

}
