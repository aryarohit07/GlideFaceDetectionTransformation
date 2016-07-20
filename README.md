
# Glide face detection transformation

### An Android image transformation library providing cropping above Face Detection for [Glide](https://github.com/bumptech/glide)

You can see the results on [my Medium blog](https://medium.com/@rohitarya/face-centering-android-library-build-on-top-of-google-vision-api-f88661b97959#.h2efha7xf).

Are you using **Picasso**? [PicassoFaceDetectionTransformation](https://github.com/aryarohit07/PicassoFaceDetectionTransformation).

### How to use it?

STEP 1:
Gradle
-------

```
repositories {
    jcenter()
}
dependencies {
    compile 'com.github.aryarohit07:glide-facedetection-transformation:0.2'
}
```

STEP 2:

Initialize the detector

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

Release the detector when you are done with the detector. (May be in ```onDestory()``` method)

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
