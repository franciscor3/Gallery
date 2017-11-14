package com.example.mx057377.gallery;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mx057377 on 11/13/2017.
 */

public class Image {
    private String description;
    private int id;
    private JSONObject file;
    private int likes;
    private String userName;
    private String createDate;
    private JSONArray comments;

    public Image (String desc, int id, JSONObject file, int likes, String userName, String createDate, JSONArray comments){
        this.description = desc;
        this.id = id;
        this.file = file;
        this.likes = likes;
        this.userName = userName;
        this.createDate = createDate;
        this.comments = comments;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONObject getFile() {
        return file;
    }

    public void setFile(JSONObject file) {
        this.file = file;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public JSONArray getComments() {
        return comments;
    }

    public void setComments(JSONArray comments) {
        this.comments = comments;
    }
}
