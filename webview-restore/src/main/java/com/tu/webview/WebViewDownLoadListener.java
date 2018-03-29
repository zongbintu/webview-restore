package com.tu.webview;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * @author Tu enum@foxmail.com.
 */

public class WebViewDownLoadListener implements DownloadListener {
  private Context context;
  private Fragment fragment;

  public WebViewDownLoadListener(Context context) {
    this.context = context;
  }

  public WebViewDownLoadListener(Fragment fragment) {
    this.fragment = fragment;
  }

  @Override public void onDownloadStart(String url, String userAgent, String contentDisposition,

      String mimetype, long contentLength) {
    if (!"data:null,null".equals(url)) {
      Uri uri = Uri.parse(url);
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      if (null != context) {
        context.startActivity(intent);
      }
      if (SDK_INT >= HONEYCOMB && fragment != null) {
        fragment.startActivity(intent);
      }
    }
  }
}
