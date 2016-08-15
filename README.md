
# Glide face detection transformation

[ ![Download](https://api.bintray.com/packages/aryarohit07/android/glide-facedetection-transformation/images/download.svg) ](https://bintray.com/aryarohit07/android/glide-facedetection-transformation/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GlideFaceDetectionTransformation-blue.svg?style=flat)](http://android-arsenal.com/details/1/4018)

### An Android image transformation library providing cropping above Face Detection for [Glide](https://github.com/bumptech/glide)

Are you using **Picasso**? [PicassoFaceDetectionTransformation](https://github.com/aryarohit07/PicassoFaceDetectionTransformation).

Are you using **Fresco**? [FrescoFaceDetectionProcessor](https://github.com/aryarohit07/FrescoFaceDetectionProcessor).

Results
------

**Original Image**

![original image 1](/images/original_image1.jpg?raw=true )

**Results after cropping**

![resulting image 1](/images/result_image1.jpg?raw=true)


**Original Image**

![original image 2](/images/original_image2.jpg?raw=true )

**Results after cropping**

![resulting image 2](/images/result_image2.jpg?raw=true)


**Original Image**

![original image 3](/images/original_image3.jpg?raw=true )

**Results after cropping**

![resulting image 3](/images/result_image3.jpg?raw=true)

**Original Image**

![original image 4](/images/original_image4.jpg?raw=true )

**Results after cropping**

![resulting image 4](/images/result_image4.jpg?raw=true)

You can read more on [my Medium article](https://medium.freecodecamp.com/face-centering-android-library-build-on-top-of-google-vision-api-f88661b97959).

### How to use it?

STEP 1:

Grab via Gradle

```
repositories {
    jcenter()
}
dependencies {
    compile 'com.github.aryarohit07:glide-facedetection-transformation:0.3.0'
}
```
Or via Maven
```
<dependency>
  <groupId>com.github.aryarohit07</groupId>
  <artifactId>glide-facedetection-transformation</artifactId>
  <version>0.3.0</version>
</dependency>
```

STEP 2:

Initialize the detector (May be in `onCreate()` method)

```java
GlideFaceDetector.initialize(context);
```

STEP 3:

Set glide transform

```java
Glide.with(yourFragment)
    .load(yourUrl)
    .transform(new FaceCenterCrop())
    .into(yourView);
```


STEP 4:

The face detector uses native resources in order to do detection. For this reason, it is necessary to release the detector instance once it is no longer needed (May be in `onDestory()` method)

```java
GlideFaceDetector.releaseDetector();
```

**Note:** If no face is detected, it will fallback to CENTER CROP.

Library dependencies:
------
```java
com.google.android.gms:play-services-vision:9.4.0
com.github.bumptech.glide:glide:3.7.0
```


**If you liked it, please Star it.**


TODO
----
* Making it generic for any point.

**Performance:**
Time taken to detect faces in the original image.


| width | height | time taken(ms) |
|-------|--------|----------------|
|  640  |  360   |  60-150        |
|  900  |  600   |  100-200       |
|  1280 |  720   |  250-350       |
|  1920 |  1080  |  350-400       |
|  2048 |  1536  |  500-550       |


License
-------

    Copyright 2016 Rohit Arya

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
