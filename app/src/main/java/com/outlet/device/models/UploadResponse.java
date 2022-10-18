package com.outlet.device.models;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

public class UploadResponse implements Serializable {
  private Integer data;

  private String status;

  public Integer getData() {
    return this.data;
  }

  public void setData(Integer data) {
    this.data = data;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
