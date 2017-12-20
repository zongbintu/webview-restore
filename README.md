# webview-vestore

[![Release](https://jitpack.io/v/2tu/webview-retore.svg)](https://jitpack.io/#2tu/webview-retore)   

Fuck WebView Restore by don't keep activities.

## Installation
Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
Add the following dependency to your `build.gradle` file:

```
dependencies {
    compile 'com.github.2tu:webview-restore:0.1.0'
    }
```  

Activity WebView restore  
![Activity WebView restore](https://raw.githubusercontent.com/2tu/2tu.github.io/master/css/images/20171219_activity_webview_restore.jpeg)  
WebView restore not finished,so onActivityResult invoke  loadUrl not work.
  
![webview-restore](https://raw.githubusercontent.com/2tu/2tu.github.io/master/css/images/20171219_activity_webview_restore_fixed.jpeg) 