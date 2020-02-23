package com.devnovikov.keepapp;

public class Note {

    private String id, image, title, subTitle, addTimeStamp, updateTimeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getImage() {
        return image;
    }


    String getTitle() {
        return title;
    }


    String getSubTitle() {
        return subTitle;
    }


    String getAddTimeStamp() {
        return addTimeStamp;
    }


    String getUpdateTimeStamp() {
        return updateTimeStamp;
    }


    Note(String id, String image, String name, String subTitle, String addTimeStamp, String updateTimeStamp) {
        this.id = id;
        this.image = image;
        this.title = name;
        this.subTitle = subTitle;
        this.addTimeStamp = addTimeStamp;
        this.updateTimeStamp = updateTimeStamp;
    }
}
