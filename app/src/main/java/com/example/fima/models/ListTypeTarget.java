package com.example.fima.models;

public class ListTypeTarget {
    private String imgpath;
    private String title;

    public ListTypeTarget(String imgpath, String title) {
        this.imgpath = imgpath;
        this.title = title;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
