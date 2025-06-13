package com.example.myapplication.data.repository;


import com.example.myapplication.data.api.ItunesApiClient;
import com.example.myapplication.data.model.Song;
import com.example.myapplication.ui.util.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongsRepository {

    private static SongsRepository instance;
    private final Map<String, List<Song>> cache = new HashMap<>();

    private SongsRepository() {}

    public static SongsRepository getInstance() {
        if (instance == null) {
            instance = new SongsRepository();
        }
        return instance;
    }

    public List<Song> searchSongs(String term, int limit, int offset) {
        String jsonResponse = ItunesApiClient.searchSongs(term, limit, offset);
        return JsonUtils.parseSongs(jsonResponse);
    }
}

