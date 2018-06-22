package com.exama.exam.net.request.characters.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Character implements Serializable{
    @SerializedName("name")
    private String name;
    @SerializedName("thumbnail")
    private Image thumbnail;

    private String description;

    private Integer id;

    public void setPk(Integer id) {
        this.id = id;
    }

    public Integer getPk() {

        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Character(Integer id, String name, Image thumbnail, String description) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}