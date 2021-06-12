package com.gladkikh.tinkoffsiriusapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Gif {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("votes")
    @Expose
    private Integer votes;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("gifURL")
    @Expose
    private String gifURL;

    @SerializedName("gifSize")
    @Expose
    private Integer gifSize;

    @SerializedName("previewURL")
    @Expose
    private String previewURL;

    @SerializedName("videoURL")
    @Expose
    private String videoURL;

    @SerializedName("videoPath")
    @Expose
    private String videoPath;

    @SerializedName("videoSize")
    @Expose
    private Integer videoSize;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("width")
    @Expose
    private String width;

    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("commentsCount")
    @Expose
    private Integer commentsCount;

    @SerializedName("fileSize")
    @Expose
    private Integer fileSize;

    @SerializedName("canVote")
    @Expose
    private Boolean canVote;

    public GifModel createGifModel() {
        return new GifModel(id, description, votes, author, date, gifURL, gifSize, previewURL, videoURL, videoPath, videoSize, type, width, height, commentsCount, fileSize, canVote);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gif gif = (Gif) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(id, gif.id) &&
                    Objects.equals(description, gif.description) &&
                    Objects.equals(votes, gif.votes) &&
                    Objects.equals(author, gif.author) &&
                    Objects.equals(date, gif.date) &&
                    Objects.equals(gifURL, gif.gifURL) &&
                    Objects.equals(gifSize, gif.gifSize) &&
                    Objects.equals(previewURL, gif.previewURL) &&
                    Objects.equals(videoURL, gif.videoURL) &&
                    Objects.equals(videoPath, gif.videoPath) &&
                    Objects.equals(videoSize, gif.videoSize) &&
                    Objects.equals(type, gif.type) &&
                    Objects.equals(width, gif.width) &&
                    Objects.equals(height, gif.height) &&
                    Objects.equals(commentsCount, gif.commentsCount) &&
                    Objects.equals(fileSize, gif.fileSize) &&
                    Objects.equals(canVote, gif.canVote);
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, description, votes, author, date, gifURL, gifSize, previewURL, videoURL, videoPath, videoSize, type, width, height, commentsCount, fileSize, canVote);
    }
}
