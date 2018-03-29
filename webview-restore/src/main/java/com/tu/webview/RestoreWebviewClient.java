package com.tu.webview;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Tu enum@foxmail.com.
 */

public class RestoreWebviewClient extends WebViewClient {
  WebView webView;

  public RestoreWebviewClient(WebView webView) {
    this.webView = webView;
  }

  @Override public void onPageFinished(WebView view, String url) {
    WebSettings webSettings = webView.getSettings();
    if (!webSettings.getBlockNetworkImage()) {
      if (!webSettings.getLoadsImagesAutomatically()) {
        webSettings.setLoadsImagesAutomatically(true);
      }
    }
    super.onPageFinished(view, url);
  }
}
