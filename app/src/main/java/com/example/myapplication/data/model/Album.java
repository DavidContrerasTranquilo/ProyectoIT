package com.example.myapplication.data.model;

import java.io.Serializable;

public class Album implements Serializable {

    private String collectionName;
    private String artistName;
    private String artworkUrl100;
    private String releaseDate;
    private String primaryGenreName;
    private String collectionPrice;
    private long collectionId;
    public Album() {
        //constructor vacio
    }

    public Album(String collectionName, String artistName, String artworkUrl100,
                 String releaseDate, String primaryGenreName, String collectionPrice) {
        this.collectionName = collectionName;
        this.artistName = artistName;
        this.artworkUrl100 = artworkUrl100;
        this.releaseDate = releaseDate;
        this.primaryGenreName = primaryGenreName;
        this.collectionPrice = collectionPrice;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setcollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }



    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }



    public void setCollectionPrice(String collectionPrice) {
        this.collectionPrice = collectionPrice;
    }
    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

}
