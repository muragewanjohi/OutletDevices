package com.outlet.device.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.String;

@Entity
public class Asset implements Serializable {

  @PrimaryKey(autoGenerate = true)
  public int uid;

  @ColumnInfo(name = "outletName")
  private String outletName;

  @ColumnInfo(name = "assetId")
  private String assetId;

  @ColumnInfo(name = "outletId")
  private String outletId;

  @ColumnInfo(name = "typeName")
  private String typeName;

  @ColumnInfo(name = "assetName")
  private String assetName;

  @ColumnInfo(name = "typeId")
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
