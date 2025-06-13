package com.example.myapplication.data.model;

import java.io.Serializable;

public class Song implements Serializable {
    private long trackId;
    private String trackName;
    private String artistName;
    private String artworkUrl100;
    private String collectionName;
    private String releaseDate;
    private String primaryGenreName;
    private double trackPrice;
    private String previewUrl;
    private String currency;
    private long collectionId;

    public Song() {
        //constructor vacio
    }

    public Song(long trackId, String trackName, String artistName, String artworkUrl100, String collectionName,
                String releaseDate, String primaryGenreName, double trackPrice, String previewUrl, String currency, long collectionId) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.artworkUrl100 = artworkUrl100;
        this.collectionName = collectionName;
        this.releaseDate = releaseDate;
        this.primaryGenreName = primaryGenreName;
        this.trackPrice = trackPrice;
        this.previewUrl = previewUrl;
        this.trackId = trackId;
        this.currency = currency;
        this.collectionId=collectionId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
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

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }
    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }
    public double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
    public long getTrackId() {
        return trackId;
    }
    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

}