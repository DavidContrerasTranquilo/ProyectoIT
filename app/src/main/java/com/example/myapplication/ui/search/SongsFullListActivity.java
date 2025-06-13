package com.example.myapplication.ui.search;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SongVerticalAdapter;
import com.example.myapplication.data.model.Song;
import com.example.myapplication.data.repository.SongsRepository;

import java.util.ArrayList;
import java.util.List;

public class SongsFullListActivity extends AppCompatActivity {

    private static final int PAGE_SIZE = 20;
    private boolean isLoading = false;
    private int offset = 0;
    private String searchTerm = "top hits";

    private SongVerticalAdapter songAdapter;
    private List<Song> songList = new ArrayList<>();
    private RecyclerView rvSongs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_full_list);

        rvSongs = findViewById(R.id.rvSongsFull);
        songAdapter = new SongVerticalAdapter(this, songList, new SongVerticalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                // Abrir la pantalla de detalle
                Intent intent = new Intent(SongsFullListActivity.this, com.example.myapplication.ui.detail.DetailActivity.class);
                intent.putExtra(com.example.myapplication.ui.util.Constants.EXTRA_SONG, song);
                startActivity(intent);
            }

            @Override
            public void onRemoveClick(Song song) {
                // No hacemos nada aquí :D
            }
        });

        rvSongs.setLayoutManager(new LinearLayoutManager(this));
        rvSongs.setAdapter(songAdapter);
        searchTerm = getIntent().getStringExtra("search_term");
        if (searchTerm == null || searchTerm.isEmpty()) {
            searchTerm = "top hits";
        }
        loadMoreSongs();

        rvSongs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvSongs.getLayoutManager();
                if (!isLoading && layoutManager != null
                        && layoutManager.findLastCompletelyVisibleItemPosition() == songList.size() - 1) {
                    loadMoreSongs();
                }
            }
        });
    }

    private void loadMoreSongs() {
        isLoading = true;
        new Thread(() -> {
            List<Song> newSongs = SongsRepository.getInstance().searchSongs(searchTerm, PAGE_SIZE, offset);

            new Handler(Looper.getMainLooper()).post(() -> {
                songList.addAll(newSongs);
                songAdapter.notifyDataSetChanged();
                offset += PAGE_SIZE;
                isLoading = false;
            });
        }).start();
    }
}
