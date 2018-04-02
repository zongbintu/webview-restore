package com.example.webviewrestore;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.tu.webview.RestoreActivityResultCallback;
import com.tu.webview.RestoreWebChromeClient;
import com.tu.webview.RestoreWebView;

public class MainActivity extends Activity implements RestoreActivityResultCallback {
  private static final String URL_LOCAL = "file:///android_asset/index.htm";
  private static final int REQUEST_CODE_PICK_CONTACT = 100;

  RestoreWebView webView;
  ProgressBar progressBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    webView = findViewById(R.id.webview);
    progressBar = findViewById(R.id.progress_bar);

    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setDatabaseEnabled(true);
    webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setAppCachePath(getCacheDir().getPath());
    webView.getSettings().setAppCacheEnabled(true);

    webView.setWebChromeClient(new RestoreWebChromeClient() {
      @Override public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress == 100 && View.VISIBLE == progressBar.getVisibility()) {
          progressBar.setVisibility(View.GONE);
        }
      }
    });
    webView.addJavascriptInterface(new JSInteraction(), "contact");

    if (savedInstanceState != null) {
      webView.restoreState(savedInstanceState);
    } else {
      webView.loadUrl(URL_LOCAL);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    webView.saveState(outState);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_PICK_CONTACT) {
      webView.onActivityResult(requestCode, resultCode, data);
      if (Activity.RESULT_OK == resultCode && data != null) {
        setContact(data);
      }
    }
  }

  @Override public void restoreActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_PICK_CONTACT && RESULT_OK == resultCode) {
      setContact(data);
    }
  }

  public class JSInteraction {
    @JavascriptInterface public void getContact() {
      Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
      startActivityForResult(intent, REQUEST_CODE_PICK_CONTACT);
    }
  }

  private void setContact(Intent data) {
    String contact = readContactFormResult(data);
    webView.loadUrl("javascript:setContact(\"" + contact + "\")");
  }

  public String readContactFormResult(Intent data) {
    StringBuilder contact = new StringBuilder();
    if (data != null) {
      try {
        Uri uri = data.getData();
        ContentResolver contentResolver = this.getContentResolver();

        String contactId = uri.getLastPathSegment();

        Cursor cursor =
            contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

        if (cursor != null) {
          String contactName = "";
          String phoneNumber;

          while (cursor.moveToNext()) {
            String name = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contactName = !TextUtils.isEmpty(name) ? name : contactName;
            if (!TextUtils.isEmpty(number)) {
              phoneNumber = number.replaceAll(" |-", "");
              contact.append(contactName).append("  ").append(phoneNumber).append("\n");
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return contact.toString();
  }
}
