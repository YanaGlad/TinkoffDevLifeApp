package com.example.tinkoffsiriusapp.models;


public class GifModel {
    private Integer id;
    private String description;
    private Integer votes;
    private String author;
    private String date;
    private String gifURL;
    private Integer gifSize;
    private String previewURL;
    private String videoURL;
    private String videoPath;
    private Integer videoSize;
    private String type;
    private String width;
    private String height;
    private Integer commentsCount;
    private Integer fileSize;
    private Boolean canVote;

    public GifModel(Integer id, String description, Integer votes, String author, String date, String gifURL, Integer gifSize, String previewURL, String videoURL, String videoPath, Integer videoSize, String type, String width, String height, Integer commentsCount, Integer fileSize, Boolean canVote) {
        this.id = id;
        this.description = description;
        this.votes = votes;
        this.author = author;
        this.date = date;
        this.gifURL = gifURL;
        this.gifSize = gifSize;
        this.previewURL = previewURL;
        this.videoURL = videoURL;
        this.videoPath = videoPath;
        this.videoSize = videoSize;
        this.type = type;
        this.width = width;
        this.height = height;
        this.commentsCount = commentsCount;
        this.fileSize = fileSize;
        this.canVote = canVote;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getVotes() {
        return votes;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getGifURL() {
        return gifURL;
    }

    public Integer getGifSize() {
        return gifSize;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public Integer getVideoSize() {
        return videoSize;
    }

    public String getType() {
        return type;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public Boolean getCanVote() {
        return canVote;
    }
}

