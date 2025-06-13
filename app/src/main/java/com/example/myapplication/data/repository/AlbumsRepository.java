package com.example.myapplication.data.repository;

import com.example.myapplication.data.api.ItunesApiClient;
import com.example.myapplication.data.model.Album;
import com.example.myapplication.ui.util.JsonUtils;

import java.util.List;

public class AlbumsRepository {

    private static AlbumsRepository instance;

    private AlbumsRepository() {}

    public static AlbumsRepository getInstance() {
        if (instance == null) {
            instance = new AlbumsRepository();
        }
        return instance;
    }

    public List<Album> searchAlbums(String term, int limit, int offset) {
        String response = ItunesApiClient.searchAlbums(term, limit, offset);
        return JsonUtils.parseAlbums(response, limit);
    }
}
