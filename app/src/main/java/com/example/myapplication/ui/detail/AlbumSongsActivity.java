package com.example.myapplication.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AlbumSongsAdapter;
import com.example.myapplication.data.api.ItunesApiClient;
import com.example.myapplication.data.model.Song;
import com.example.myapplication.ui.util.Constants;
import com.example.myapplication.ui.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class AlbumSongsActivity extends AppCompatActivity {

    public static final String EXTRA_COLLECTION_ID = "collectionId";
    public static final String EXTRA_SEARCH_TERM = "searchTerm";

    private RecyclerView rvSongs;
    private AlbumSongsAdapter adapter;
    private List<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_songs);

        rvSongs = findViewById(R.id.rvAlbumSongs);

        adapter = new AlbumSongsAdapter(this, songList, song -> {
            Intent intent = new Intent(AlbumSongsActivity.this, DetailActivity.class);
            intent.putExtra(Constants.EXTRA_SONG, song);
            startActivity(intent);
        });

        rvSongs.setLayoutManager(new LinearLayoutManager(this));
        rvSongs.setAdapter(adapter);

        long collectionId = getIntent().getLongExtra(EXTRA_COLLECTION_ID, -1);
        String searchTerm = getIntent().getStringExtra(EXTRA_SEARCH_TERM);

        if (collectionId > 0 && searchTerm != null && !searchTerm.isEmpty()) {
            loadSongsByCollectionId(searchTerm, collectionId);
        } else {
            Toast.makeText(this, "Faltan datos para cargar las canciones del álbum.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSongsByCollectionId(String term, long collectionId) {
        new Thread(() -> {
            String json = ItunesApiClient.searchSongs(term, 200, 0);
            List<Song> allSongs = JsonUtils.parseSongs(json);

            List<Song> filtered = new ArrayList<>();
            for (Song s : allSongs) {
                if (s.getCollectionId() == collectionId) {
                    filtered.add(s);
                }
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                if (filtered.isEmpty()) {
                    Toast.makeText(this, "Este álbum no tiene canciones disponibles.", Toast.LENGTH_SHORT).show();
                }
                songList.clear();
                songList.addAll(filtered);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }
}
