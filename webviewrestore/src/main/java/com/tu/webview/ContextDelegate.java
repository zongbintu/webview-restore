package com.tu.webview;

import android.content.Intent;

/**
 * @author Tu enum@foxmail.com.
 */

public interface ContextDelegate {
  void setActivityResult(int requestCode, int resultCode, Intent data);
}
