
# Glide face detection transformation

### An Android image transformation library providing cropping above Face Detection for [Glide](https://github.com/bumptech/glide)

### How to use it?

STEP 1:
Gradle
-------

```
repositories {
    jcenter()
}
dependencies {
    compile 'com.github.aryarohit07:glide-facedetection-transformation:0.1@aar'
}
```

STEP 2:
Set glide transform
-------

```java
Glide.with(yourFragment)
    .load(yourUrl)
    .transform(new CenterFaceCrop(context))
    .into(yourView);
```

That's it! It will do the rest.

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
