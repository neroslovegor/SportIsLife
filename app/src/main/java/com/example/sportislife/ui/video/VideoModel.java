package com.example.sportislife.ui.video;

public class VideoModel {

    private String id;
    private String title;
    private String channelTitle;
    private String imageUrl;

    public VideoModel(String id, String title, String channelTitle, String imageUrl) {
        this.id = id;
        this.title = title;
        this.channelTitle = channelTitle;
        this.imageUrl = imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
    public String getChannelTitle() {
        return channelTitle;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
