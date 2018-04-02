package com.tu.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.CUPCAKE;
import static android.os.Build.VERSION_CODES.ECLAIR;
import static android.os.Build.VERSION_CODES.ECLAIR_MR1;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * @author Tu enum@foxmail.com.
 */

public class RestoreWebView extends WebView
    implements OnActivityResultCallback, OnPageFinishedCallback {
  private boolean needRestoreResult;
  private ActivityResult activityResult;

  public RestoreWebView(Context context) {
    super(context);
    init();
  }

  public RestoreWebView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public RestoreWebView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
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

  @Override public boolean onTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
    }
    return super.onTouchEvent(ev);
  }

  private void init() {
    if (SDK_INT >= CUPCAKE && isInEditMode()) {
      return;
    }

    WebSettings settings = getSettings();
    settings.setSavePassword(false);

    //图片加载
    if (SDK_INT >= Build.VERSION_CODES.KITKAT) {
      settings.setLoadsImagesAutomatically(true);
    } else {
      settings.setLoadsImagesAutomatically(false);
      settings.setBlockNetworkImage(false);
    }
    if (SDK_INT >= 21) {
      settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    //js
    settings.setJavaScriptEnabled(true);
    settings.setJavaScriptCanOpenWindowsAutomatically(true);

    //缓存
    if (SDK_INT >= ECLAIR) {
      settings.setDatabaseEnabled(true);
    }
    if (SDK_INT >= ECLAIR_MR1) {
      settings.setDomStorageEnabled(true);
      settings.setAppCacheEnabled(true);
    }
    settings.setCacheMode(WebSettings.LOAD_DEFAULT);

    //文件访问
    if (SDK_INT >= HONEYCOMB) {
      settings.setAllowContentAccess(true);
    }
    if (SDK_INT >= CUPCAKE) {
      settings.setAllowFileAccess(true);
    }
    if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      settings.setAllowFileAccessFromFileURLs(true);
    }

    //显示
    settings.setUseWideViewPort(true);
    if (SDK_INT >= ECLAIR_MR1) {
      settings.setLoadWithOverviewMode(true);
    }
    settings.setSupportZoom(false);
    if (SDK_INT >= HONEYCOMB) {
      settings.setDisplayZoomControls(false);
    }

    if (SDK_INT >= ECLAIR) {
      settings.setGeolocationEnabled(true);
    }
    settings.setSupportMultipleWindows(true);
    settings.setSaveFormData(true);

    //允许跨域
    enablecrossdomain(this);
    setWebChromeClient(new WebChromeClient());
    setWebViewClient(new WebViewClient());
  }

  public void enablecrossdomain(WebView webView) {
    if (Build.VERSION.SDK_INT < 16) {
      try {
        Field field = webView.getClass().getDeclaredField("mWebViewCore");
        field.setAccessible(true);
        Object webviewcore = field.get(webView);
        Method method = webviewcore.getClass()
            .getDeclaredMethod("nativeRegisterURLSchemeAsLocal", String.class);
        method.setAccessible(true);
        method.invoke(webviewcore, "http");
        method.invoke(webviewcore, "https");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
    }
  }
}
