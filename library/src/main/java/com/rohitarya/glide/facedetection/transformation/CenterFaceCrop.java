package com.rohitarya.glide.facedetection.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.SparseArray;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.rohitarya.glide.facedetection.transformation.core.GlideFaceDetector;

/**
 * Created by Rohit Arya (http://rohitarya.com) on 18/7/16.
 */
public class CenterFaceCrop extends BitmapTransformation {

    public CenterFaceCrop(){
        this(GlideFaceDetector.getContext());
    }

    private CenterFaceCrop(Context context) {
        super(context);
    }

    /**
     *
     * @param bitmapPool A {@link com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool} that can be used to obtain and
     *             return intermediate {@link Bitmap}s used in this transformation. For every
     *             {@link android.graphics.Bitmap} obtained from the pool during this transformation, a
     *             {@link android.graphics.Bitmap} must also be returned.
     * @param original The {@link android.graphics.Bitmap} to transform
     * @param width The ideal width of the transformed bitmap
     * @param height The ideal height of the transformed bitmap
     * @return a transformed bitmap with face being in center.
     */
    @Override
    protected Bitmap transform(BitmapPool bitmapPool, Bitmap original, int width, int height) {

        Bitmap.Config config =
                original.getConfig() != null ? original.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap result = bitmapPool.get(width, height, config);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, config);
        }

        float scaleX = (float) width / original.getWidth();
        float scaleY = (float) height / original.getHeight();
        float scale = Math.max(scaleX, scaleY);

        float left = 0f;
        float top = 0f;

        float scaledWidth = width, scaledHeight = height;

        if(scaleX!=scaleY) {

            int[] faceRect = new int[4];
            boolean faceDetected = detectFace(original, faceRect);

            if(scaleX < scaleY) {

                scaledWidth = scale * original.getWidth();

                if(faceDetected) {
                    float scaledFaceLeft = scale * faceRect[0];
                    float scaledFaceWidth = scale * faceRect[2];
                    float faceCenterX = scaledFaceLeft + (scaledFaceWidth/2);
                    left = getLeftPoint(width, scaledWidth, faceCenterX);
                }else {
                    left = (width - scaledWidth) / 2; // center crop
                }

            }else {

                scaledHeight = scale * original.getHeight();

                if(faceDetected) {
                    float scaledFaceTop = scale * faceRect[1];
                    float scaledFaceHeight = scale * faceRect[3];
                    float faceCenterY = scaledFaceTop + (scaledFaceHeight/2);
                    top = getTopPoint(height, scaledHeight, faceCenterY);
                }else {
                    top = (height - scaledHeight) / 2; // center crop
                }
            }
        }

        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(original, null, targetRect, null);

        return result;
    }

    private float getTopPoint(int height, float scaledHeight, float faceCenterY) {
        if(faceCenterY <= height/2) { // Face is near the top edge
            return 0f;
        } else if((scaledHeight - faceCenterY) <= height/2) { // face is near bottom edge
            return height - scaledHeight;
        } else {
            return (height/2) - faceCenterY;
        }
    }

    private float getLeftPoint(int width, float scaledWidth, float faceCenterX) {
        if(faceCenterX <= width/2) { // face is near the left edge.
            return 0f;
        } else if((scaledWidth-faceCenterX) <= width/2) {  // face is near right edge
            return (width - scaledWidth);
        } else {
            return (width/2) - faceCenterX;
        }
    }

    @Override
    public String getId() {
        return "com.rohitarya.glide.fecedetection.transformation";
    }

    private boolean detectFace(Bitmap bitmap, int[] faceRect) {
        FaceDetector faceDetector = GlideFaceDetector.getFaceDetector();
        if(!faceDetector.isOperational()){
            return false;
        }
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        if(faces!=null && faces.size()>0) {
            Face face1 = faces.valueAt(0);
            faceRect[0] = (int)face1.getPosition().x;
            faceRect[1] = (int)face1.getPosition().y;
            faceRect[2] = (int)face1.getWidth();
            faceRect[3] = (int)face1.getHeight();
            return true;
        }
        return false;
    }
}
