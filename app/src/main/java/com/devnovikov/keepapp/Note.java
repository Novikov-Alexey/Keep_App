package com.devnovikov.keepapp;

public class Note {

        String id, image, title, subTitle, addTimeStamp, updateTimeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAddTimeStamp() {
        return addTimeStamp;
    }

    public void setAddTimeStamp(String addTimeStamp) {
        this.addTimeStamp = addTimeStamp;
    }

    public String getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(String updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }

    public Note(String id, String image, String name, String subTitle, String addTimeStamp, String updateTimeStamp) {
        this.id = id;
        this.image = image;
        this.title = name;
        this.subTitle = subTitle;
        this.addTimeStamp = addTimeStamp;
        this.updateTimeStamp = updateTimeStamp;
    }
}
