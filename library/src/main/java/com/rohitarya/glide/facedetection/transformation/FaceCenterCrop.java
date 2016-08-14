package com.rohitarya.glide.facedetection.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.rohitarya.glide.facedetection.transformation.core.GlideFaceDetector;

/*
 * Copyright (C) 2016 Rohit Arya
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class FaceCenterCrop extends BitmapTransformation {

    public FaceCenterCrop() {
        this(GlideFaceDetector.getContext());
    }

    private FaceCenterCrop(Context context) {
        super(context);
    }

    /**
     * @param bitmapPool A {@link com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool} that can be used to obtain and
     *                   return intermediate {@link Bitmap}s used in this transformation. For every
     *                   {@link android.graphics.Bitmap} obtained from the pool during this transformation, a
     *                   {@link android.graphics.Bitmap} must also be returned.
     * @param original   The {@link android.graphics.Bitmap} to transform
     * @param width      The ideal width of the transformed bitmap
     * @param height     The ideal height of the transformed bitmap
     * @return a transformed bitmap with face being in center.
     */
    @Override
    protected Bitmap transform(BitmapPool bitmapPool, Bitmap original, int width, int height) {

        float scaleX = (float) width / original.getWidth();
        float scaleY = (float) height / original.getHeight();

        if (scaleX != scaleY) {

            Bitmap.Config config =
                    original.getConfig() != null ? original.getConfig() : Bitmap.Config.ARGB_8888;
            Bitmap result = bitmapPool.get(width, height, config);
            if (result == null) {
                result = Bitmap.createBitmap(width, height, config);
            }

            float scale = Math.max(scaleX, scaleY);

            float left = 0f;
            float top = 0f;

            float scaledWidth = width, scaledHeight = height;

            PointF focusPoint = new PointF();

            detectFace(original, focusPoint);

            if (scaleX < scaleY) {

                scaledWidth = scale * original.getWidth();

                float faceCenterX = scale * focusPoint.x;
                left = getLeftPoint(width, scaledWidth, faceCenterX);

            } else {

                scaledHeight = scale * original.getHeight();

                float faceCenterY = scale * focusPoint.y;
                top = getTopPoint(height, scaledHeight, faceCenterY);
            }

            RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(original, null, targetRect, null);
            //No need to recycle() original Bitmap as Glide will take care of returning our original Bitmap to the BitmapPool
            return result;
        } else {
            return original;
        }
    }

    /**
     * Calculates a point (focus point) in the bitmap, around which cropping needs to be performed.
     *
     * @param bitmap           Bitmap in which faces are to be detected.
     * @param centerOfAllFaces To store the center point.
     */
    private void detectFace(Bitmap bitmap, PointF centerOfAllFaces) {
        FaceDetector faceDetector = GlideFaceDetector.getFaceDetector();
        if (!faceDetector.isOperational()) {
            centerOfAllFaces.set(bitmap.getWidth() / 2, bitmap.getHeight() / 2); // center crop
            return;
        }
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        final int totalFaces = faces.size();
        if (totalFaces > 0) {
            float sumX = 0f;
            float sumY = 0f;
            for (int i = 0; i < totalFaces; i++) {
                PointF faceCenter = new PointF();
                getFaceCenter(faces.get(faces.keyAt(i)), faceCenter);
                sumX = sumX + faceCenter.x;
                sumY = sumY + faceCenter.y;
            }
            centerOfAllFaces.set(sumX / totalFaces, sumY / totalFaces);
            return;
        }
        centerOfAllFaces.set(bitmap.getWidth() / 2, bitmap.getHeight() / 2); // center crop
    }

    /**
     * Calculates center of a given face
     *
     * @param face   Face
     * @param center Center of the face
     */
    private void getFaceCenter(Face face, PointF center) {
        float x = face.getPosition().x;
        float y = face.getPosition().y;
        float width = face.getWidth();
        float height = face.getHeight();
        center.set(x + (width / 2), y + (height / 2)); // face center in original bitmap
    }

    private float getTopPoint(int height, float scaledHeight, float faceCenterY) {
        if (faceCenterY <= height / 2) { // Face is near the top edge
            return 0f;
        } else if ((scaledHeight - faceCenterY) <= height / 2) { // face is near bottom edge
            return height - scaledHeight;
        } else {
            return (height / 2) - faceCenterY;
        }
    }

    private float getLeftPoint(int width, float scaledWidth, float faceCenterX) {
        if (faceCenterX <= width / 2) { // face is near the left edge.
            return 0f;
        } else if ((scaledWidth - faceCenterX) <= width / 2) {  // face is near right edge
            return (width - scaledWidth);
        } else {
            return (width / 2) - faceCenterX;
        }
    }

    @Override
    public String getId() {
        return FaceCenterCrop.class.getCanonicalName();
    }
}
