package com.example.myapplication.ui.util;

import com.example.myapplication.data.model.Album;
import com.example.myapplication.data.model.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Song> parseSongs(String json) {
        List<Song> songs = new ArrayList<>();
        if (json == null || json.trim().isEmpty()) {
            return songs;
        }
        try {
            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject item = results.getJSONObject(i);

                // Només afegim si és una cançó
                if (!item.optString("wrapperType").equals("track") || !item.optString("kind").equals("song")) {
                    continue;
                }

                Song song = new Song();
                song.setTrackName(item.optString("trackName"));
                song.setTrackId(item.optLong("trackId", 0));

                song.setArtistName(item.optString("artistName"));
                song.setArtworkUrl100(item.optString("artworkUrl100"));
                song.setCollectionName(item.optString("collectionName"));
                song.setReleaseDate(item.optString("releaseDate"));
                song.setPrimaryGenreName(item.optString("primaryGenreName"));
                song.setTrackPrice(item.has("trackPrice") ? item.optDouble("trackPrice", 0.0) : 0.0);
                song.setPreviewUrl(item.optString("previewUrl"));
                song.setCollectionId(item.optLong("collectionId", 0));

                songs.add(song);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }


    public static List<Album> parseAlbums(String json, int limit) {
        List<Album> albums = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");
            int count = Math.min(results.length(), limit);

            for (int i = 0; i < count; i++) {
                JSONObject item = results.getJSONObject(i);

                // Només afegim si és un àlbum
                if (!item.optString("wrapperType").equals("collection") ||
                        !item.optString("collectionType").equals("Album")) {
                    continue;
                }

                Album album = new Album();
                album.setCollectionId(item.optLong("collectionId", 0));
                album.setcollectionName(item.optString("collectionName"));
                album.setArtistName(item.optString("artistName"));
                album.setArtworkUrl100(item.optString("artworkUrl100"));
                album.setReleaseDate(item.optString("releaseDate"));
                album.setPrimaryGenreName(item.optString("primaryGenreName"));
                album.setCollectionPrice(String.valueOf(item.has("collectionPrice") ? item.optDouble("collectionPrice", 0.0) : 0.0));

                albums.add(album);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return albums;
    }

}
