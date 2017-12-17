package com.tu.webview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author Tu enum@foxmail.com.
 */

public class RestoreWebView extends WebView
    implements OnActivityResultCallback, OnPageFinishedCallback {
  private Integer requestCode, resultCode;
  private Intent data;

  public RestoreWebView(Context context) {
    super(context);
  }

  public RestoreWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public RestoreWebView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    this.requestCode = requestCode;
    this.resultCode = resultCode;
    this.data = data;
  }

  @Override public void onPageFinished(WebView view, String url) {
    if (getContext() instanceof RestoreActivityResultCallback
        && requestCode != null
        && resultCode != null) {
      ((RestoreActivityResultCallback) getContext()).restoreActivityResult(requestCode, resultCode,
          data);
    }
    requestCode = null;
    resultCode = null;
    data = null;
  }
}
