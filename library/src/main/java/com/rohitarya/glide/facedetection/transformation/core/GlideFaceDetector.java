package com.rohitarya.glide.facedetection.transformation.core;

import android.content.Context;

import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by Rohit Arya (http://rohitarya.com) on 18/7/16.
 */
public class GlideFaceDetector {

    private static volatile FaceDetector faceDetector;
    private static Context mContext;

    public static Context getContext() {
        if(mContext==null) {
            throw new RuntimeException("Initialize GlideFaceDetector by calling GlideFaceDetector.initialize(context).");
        }
        return mContext;
    }

    public static void initialize(Context context) {
        mContext = context;
        initDetector();
    }

    private static void initDetector() {
        if(null==faceDetector) {
            synchronized ((GlideFaceDetector.class)) {
                if(null==faceDetector) {
                    faceDetector = new
                            FaceDetector.Builder(mContext)
                            .setTrackingEnabled(false)
                            .build();
                }
            }
        }
    }

    public static FaceDetector getFaceDetector() {
        if(mContext==null) {
            throw new RuntimeException("Initialize GlideFaceDetector by calling GlideFaceDetector.initialize(context).");
        }
        initDetector();
        return faceDetector;
    }

    public static void releaseDetector() {
        if(faceDetector!=null) {
            faceDetector.release();
            faceDetector = null;
        }
    }
}
