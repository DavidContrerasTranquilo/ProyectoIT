package com.example.myapplication.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.data.model.Song;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;


public class FavoritesRepository {

    private static final String PREFS = "fav_prefs";
    private static final String KEY_IDS = "fav_ids";

    private SharedPreferences prefs;
    private Gson gson = new Gson();

    private static FavoritesRepository instance;

    private FavoritesRepository(Context ctx) {
        prefs = ctx.getApplicationContext()
                .getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static FavoritesRepository getInstance(Context ctx) {
        if (instance == null) {
            instance = new FavoritesRepository(ctx);
        }
        return instance;
    }

    public void addFavorite(Song s) {
        Set<String> updatedIds = new HashSet<>(prefs.getStringSet(KEY_IDS, new HashSet<>()));
        updatedIds.add(String.valueOf(s.getTrackId()));

        prefs.edit()
                .putString("song_" + s.getTrackId(), gson.toJson(s))
                .putStringSet(KEY_IDS, updatedIds)
                .apply();

        android.util.Log.d("FAV", "Añadido: " + s.getTrackName() + " | ID: " + s.getTrackId());
    }


    public void removeFavorite(long id) {
        Set<String> updatedIds = new HashSet<>(prefs.getStringSet(KEY_IDS, new HashSet<>()));
        updatedIds.remove(String.valueOf(id));

        prefs.edit()
                .remove("song_" + id)
                .putStringSet(KEY_IDS, updatedIds)
                .apply();
    }

    public boolean isFavorite(long id) {
        Set<String> currentIds = prefs.getStringSet(KEY_IDS, new HashSet<>());
        return currentIds.contains(String.valueOf(id));
    }

    public Set<Song> getAllFavorites() {
        Set<String> ids = prefs.getStringSet(KEY_IDS, new HashSet<>());
        Set<Song> favs = new HashSet<>();
        for (String sid : ids) {
            String json = prefs.getString("song_" + sid, null);
            if (json != null) {
                Song song = gson.fromJson(json, Song.class);
                favs.add(song);
                //Logs per debuggar
                android.util.Log.d("FAV", "Recuperado: " + song.getTrackName() + " | ID: " + song.getTrackId());
            } else {
                android.util.Log.d("FAV", " No encontrado JSON para ID: " + sid);
            }
        }
        return favs;
    }

}
