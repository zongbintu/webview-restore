package com.tu.webview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author Tu enum@foxmail.com.
 */

public class WebViewRestore extends WebView implements WebViewDelegate {
  Integer requestCode, resultCode;
  Intent data;

  public WebViewRestore(Context context) {
    super(context);
  }

  public WebViewRestore(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public WebViewRestore(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    this.requestCode = requestCode;
    this.resultCode = resultCode;
    this.data = data;
  }

  @Override public void onPageFinished() {
    if (getContext() instanceof ContextDelegate && requestCode != null && resultCode != null) {
      ((ContextDelegate) getContext()).setActivityResult(requestCode, resultCode, data);
    }
    requestCode = null;
    resultCode = null;
    data = null;
  }
}
