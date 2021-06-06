package com.gladkikh.tinkoffsiriusapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Gifs {
    @SerializedName("result")
    @Expose
    private ArrayList<Gif> gifs = null;

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;

    public ArrayList<Gif> getGifs() {
        return gifs;
    }

}
