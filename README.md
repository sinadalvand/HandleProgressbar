# HandleProgressbar
[![Developer](https://img.shields.io/badge/developer-sina%20dalvand-orange)](https://github.com/sinadalvand)
[![Lisense](https://img.shields.io/badge/License-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://img.shields.io/github/v/release/sinadalvand/HandleProgressbar)](https://bintray.com/sinadalvand/maven/HandleProgressbar)


animation Progressbar that can transform to handle

<img src="https://github.com/sinadalvand/HandleProgressbar/blob/master/art/preview.gif" width="250"/>

<img src="https://github.com/sinadalvand/HandleProgressbar/blob/master/art/preview2.gif" width="250"/>


## Gradle & Maven
```
 implementation 'ir.sinadalvand.libs:handleProgressbar:{Last_release_version}'
```
```
<dependency>
	<groupId>ir.sinadalvand.libs</groupId>
	<artifactId>handleProgressbar</artifactId>
	<version>1.0.3</version>
	<type>pom</type>
</dependency>
```


## How use this Progressbar :
```
<ir.lib.sinadalvand.HandleProgressbar
        android:layout_width="match_parent"
        android:layout_height="3dp"
        app:handleSize="30dp"                      ==> handle mode width
        app:progressbarColor="@color/colorAccent"  ==> progress and handle color
        app:progressbarState="Handle"              ==> init mode ("progress" or "handle")
        />
```

### For start progressing :
```
progress.start()
```

### For transform to Handle :
```
progress.stop()
```


and also you can set details programically:

handle size:
```
progress.setHandleSize(int)
* value should be in dp 
```


progress and handle color:

```
progress.setHandleColor(int)

```



# License

    Copyright 2016 Nightonke

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
