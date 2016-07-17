package com.rohitarya.glide.facedetection.transformation.core;

import android.content.Context;

import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by Rohit Arya (http://rohitarya.com) on 18/7/16.
 */
public class FaceUtils {

    private static volatile FaceDetector faceDetector;
    private static Context prevContext;
    public static FaceDetector getFaceDetector(Context context) {

        if(prevContext!=null && prevContext!=context) {
            faceDetector = null; // in case user changes context, then provide new instance of detector
        }

        if(null==faceDetector) {
            synchronized ((FaceUtils.class)) {
                if(null==faceDetector) {
                    faceDetector = new
                            FaceDetector.Builder(context)
                            .setTrackingEnabled(false)
                            .build();
                }
            }
        }
        prevContext = context;
        return faceDetector;
    }
}
