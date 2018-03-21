package com.linkedin.replica.jobs.models;

import java.util.ArrayList;

public class Media {
    private ArrayList<String> images;
    private ArrayList<String> videos;
    private ArrayList<String> urls;

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setVideos(ArrayList<String> videos) {
        this.videos = videos;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public ArrayList<String> getImages() {

        return images;
    }

    public ArrayList<String> getVideos() {
        return videos;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }
}
