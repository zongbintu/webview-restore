package com.tu.webview;

import android.content.Intent;

/**
 * @author Tu enum@foxmail.com.
 */

public interface RestoreActivityResultCallback {
  void restoreActivityResult(int requestCode, int resultCode, Intent data);
}
