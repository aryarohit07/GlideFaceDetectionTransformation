
# Glide face detection transformation

### An Android image transformation library providing cropping above Face Detection for [Glide](https://github.com/bumptech/glide)

Are you using **Picasso**? [PicassoFaceDetectionTransformation](https://github.com/aryarohit07/PicassoFaceDetectionTransformation).

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
    compile 'com.github.aryarohit07:glide-facedetection-transformation:0.2.1'
}
```
Or via Maven
```
<dependency>
  <groupId>com.github.aryarohit07</groupId>
  <artifactId>glide-facedetection-transformation</artifactId>
  <version>0.2.1</version>
</dependency>
```

STEP 2:

Initialize the detector (May be in `onCreate()` method)

```java
GlideFaceDetector.initialize(context);
```

STEP 3:

Set glide transform
-------

```java
Glide.with(yourFragment)
    .load(yourUrl)
    .transform(new CenterFaceCrop())
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
com.google.android.gms:play-services-vision:9.2.1
com.github.bumptech.glide:glide:3.7.0
```

**If you liked it, please Star it.**

TODO
----
* Support for multiple faces.
* Making it generic for any point.


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
