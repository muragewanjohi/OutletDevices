package com.outlet.device.view_assets;

public class ListData {
    private String device_name;
    private String device_id;
    private String condition;
    private String imgId;

    public ListData(String device_name, String device_id, String condition, String imgId) {
        this.device_name = device_name;
        this.device_id = device_id;
        this.condition = condition;
        this.imgId = imgId;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
}
