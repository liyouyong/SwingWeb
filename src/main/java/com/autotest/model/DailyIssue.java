package com.autotest.model;

/**
 * Created by z1245 on 11/30/2017.
 */
public class DailyIssue {
    private String item;
    private String feature;
    private String type;
    private String description;
    private String remark;

    public DailyIssue() {
    }

    public DailyIssue(String item, String feature, String type, String description, String remark) {
        this.item = item;
        this.feature = feature;
        this.type = type;
        this.description = description;
        this.remark = remark;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DailyIssue{" +
                "item='" + item + '\'' +
                ", feature='" + feature + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
