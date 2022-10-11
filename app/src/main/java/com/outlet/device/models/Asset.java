package com.outlet.device.models;

import java.io.Serializable;
import java.lang.String;

public class Asset implements Serializable {
  private String outletName;

  private String assetId;

  private String outletId;

  private String typeName;

  private String assetName;

  private String typeId;

  public String getOutletName() {
    return this.outletName;
  }

  public void setOutletName(String outletName) {
    this.outletName = outletName;
  }

  public String getAssetId() {
    return this.assetId;
  }

  public void setAssetId(String assetId) {
    this.assetId = assetId;
  }

  public String getOutletId() {
    return this.outletId;
  }

  public void setOutletId(String outletId) {
    this.outletId = outletId;
  }

  public String getTypeName() {
    return this.typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getAssetName() {
    return this.assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public String getTypeId() {
    return this.typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }
}
