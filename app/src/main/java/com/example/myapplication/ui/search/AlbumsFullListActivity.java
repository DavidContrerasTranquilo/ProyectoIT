package com.example.myapplication.ui.search;

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
import com.example.myapplication.adapter.AlbumVerticalAdapter;
import com.example.myapplication.data.model.Album;
import com.example.myapplication.data.repository.AlbumsRepository;
import com.example.myapplication.ui.detail.AlbumSongsActivity;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFullListActivity extends AppCompatActivity {

    private static final int PAGE_SIZE = 20;
    private boolean isLoading = false;
    private int offset = 0;
    private String searchTerm = "greatest albums";

    private AlbumVerticalAdapter albumAdapter;
    private List<Album> albumList = new ArrayList<>();
    private RecyclerView rvAlbums;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_full_list);

        rvAlbums = findViewById(R.id.rvAlbumsFull);

        albumAdapter = new AlbumVerticalAdapter(this, albumList, album -> {
            Intent intent = new Intent(this, AlbumSongsActivity.class);
            intent.putExtra(AlbumSongsActivity.EXTRA_COLLECTION_ID, album.getCollectionId());
            intent.putExtra(AlbumSongsActivity.EXTRA_SEARCH_TERM, album.getArtistName()); // o collectionName


            startActivity(intent);
        });

        rvAlbums.setLayoutManager(new LinearLayoutManager(this));
        rvAlbums.setAdapter(albumAdapter);

        searchTerm = getIntent().getStringExtra("search_term");
        if (searchTerm == null || searchTerm.isEmpty()) {
            searchTerm = "greatest albums";
        }

        loadMoreAlbums();

        rvAlbums.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvAlbums.getLayoutManager();
                if (!isLoading && layoutManager != null
                        && layoutManager.findLastCompletelyVisibleItemPosition() == albumList.size() - 1) {
                    loadMoreAlbums();
                }
            }
        });
    }


    private void loadMoreAlbums() {
        isLoading = true;
        new Thread(() -> {
            List<Album> newAlbums = AlbumsRepository.getInstance().searchAlbums(searchTerm, PAGE_SIZE, offset);
            new Handler(Looper.getMainLooper()).post(() -> {
                albumList.addAll(newAlbums);
                albumAdapter.notifyDataSetChanged();
                offset += PAGE_SIZE;
                isLoading = false;
            });
        }).start();
    }
}
