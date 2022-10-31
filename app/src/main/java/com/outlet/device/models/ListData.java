package com.outlet.device.models;

public class ListData {
    private String outlet_id;
    private String device_name;
    private String condition;
    private String date;
    private String synced;
    private String imageId;

    public ListData(String outlet_id, String device_name, String condition, String date, String synced, String imageId) {
        this.outlet_id = outlet_id;
        this.device_name = device_name;
        this.condition = condition;
        this.date = date;
        this.synced = synced;
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }
}
