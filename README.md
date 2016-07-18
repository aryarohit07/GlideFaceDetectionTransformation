
# Glide face detection transformation

### An Android image transformation library providing cropping above Face Detection for [Glide](https://github.com/bumptech/glide)

Please feel free to use this.

How to use:

```java
Glide.with(yourFragment)
    .load(yourUrl)
    .transform(new MyTransformation(context))
    .into(yourView);
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
