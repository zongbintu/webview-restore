package com.tu.webview;

import android.content.Intent;
import java.io.Serializable;

/**
 * @author Tu enum@foxmail.com.
 */

public class ActivityResult implements Serializable {
  private static final long serialVersionUID = 2997916815606455622L;
  private Integer requestCode, resultCode;
  private Intent data;

  public ActivityResult(Integer requestCode, Integer resultCode, Intent data) {
    this.requestCode = requestCode;
    this.resultCode = resultCode;
    this.data = data;
  }

  public Integer getRequestCode() {
    return requestCode;
  }

  public void setRequestCode(Integer requestCode) {
    this.requestCode = requestCode;
  }

  public Integer getResultCode() {
    return resultCode;
  }

  public void setResultCode(Integer resultCode) {
    this.resultCode = resultCode;
  }

  public Intent getData() {
    return data;
  }

  public void setData(Intent data) {
    this.data = data;
  }
}
