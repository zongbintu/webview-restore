package com.tu.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @author Tu enum@foxmail.com.
 */

public class RestoreWebView extends WebView
    implements OnActivityResultCallback, OnPageFinishedCallback {
  private boolean needRestoreResult;
  private ActivityResult activityResult;

  public RestoreWebView(Context context) {
    super(context);
  }

  public RestoreWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public RestoreWebView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    super.onRestoreInstanceState(state);
    needRestoreResult = true;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (needRestoreResult) {
      activityResult = new ActivityResult(requestCode, resultCode, data);
    }
    needRestoreResult = false;
  }

  @Override public void onPageFinished(WebView view, String url) {
    if (null != activityResult && getContext() instanceof RestoreActivityResultCallback) {
      ((RestoreActivityResultCallback) getContext()).restoreActivityResult(
          activityResult.getRequestCode(), activityResult.getResultCode(),
          activityResult.getData());
    }
    activityResult = null;
  }
}
