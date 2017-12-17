package com.tu.webview;

import android.webkit.WebView;

/**
 * @author Tu enum@foxmail.com.
 */

public interface OnPageFinishedCallback {
  void onPageFinished(WebView view, String url);
}
