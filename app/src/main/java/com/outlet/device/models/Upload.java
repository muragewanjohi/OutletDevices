package com.outlet.device.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.String;

@Entity
public class Upload implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "userId")
    private String userId;

    @ColumnInfo(name = "assetId")
    private String assetId;

    @ColumnInfo(name = "outletId")
    private String outletId;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "Image")
    private String Image;

    @ColumnInfo(name = "barCode")
    private String barCode;

    @ColumnInfo(name = "qrCode")
    private String qrCode;

    @ColumnInfo(name = "stateId")
    private String stateId;

    @ColumnInfo(name = "remark")
    private String remark;

    @ColumnInfo(name = "synced", defaultValue = "false")
    private Boolean synced = false ;

    public Boolean getSynced() {
        return synced;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }

    /*
    public Upload(String userId, String assetId, String outletId, String latitude, String longitude, String datetime, String image, String barCode, String qrCode, String stateId, String remark) {
        this.userId = userId;
        this.assetId = assetId;
        this.outletId = outletId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.datetime = datetime;
        Image = image;
        this.barCode = barCode;
        this.qrCode = qrCode;
        this.stateId = stateId;
        this.remark = remark;
    }*/

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
