package com.tu.webview;

import android.content.Intent;

/**
 * @author Tu enum@foxmail.com.
 */

public interface OnActivityResultCallback {
  void onActivityResult(int requestCode, int resultCode, Intent data);
}
