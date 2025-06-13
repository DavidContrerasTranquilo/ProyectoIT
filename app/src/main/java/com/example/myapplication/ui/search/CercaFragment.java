package com.example.myapplication.ui.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AlbumHorizontalAdapter;
import com.example.myapplication.adapter.SongHorizontalAdapter;
import com.example.myapplication.data.model.Album;
import com.example.myapplication.data.model.Song;
import com.example.myapplication.data.repository.AlbumsRepository;
import com.example.myapplication.data.repository.SongsRepository;
import com.example.myapplication.ui.detail.AlbumSongsActivity;
import com.example.myapplication.ui.util.UiUtils;

import java.util.List;

public class CercaFragment extends Fragment {

    private SearchView etSearch;
    private RecyclerView rvSongs, rvAlbums;
    private SongHorizontalAdapter songAdapter;
    private AlbumHorizontalAdapter albumAdapter;
    private String currentSearchTerm = "top hits";
    private static final int DEFAULT_LIMIT = 15;

    public CercaFragment() {}

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cerca, container, false);

        etSearch = view.findViewById(R.id.search_view);
        rvSongs = view.findViewById(R.id.recycler_songs);
        rvAlbums = view.findViewById(R.id.recycler_albums);
        Button btnViewAllSongs = view.findViewById(R.id.btnViewAllSongs);
        Button btnViewAllAlbums = view.findViewById(R.id.btnViewAllAlbums);

        songAdapter = new SongHorizontalAdapter(requireContext(), List.of());
        albumAdapter = new AlbumHorizontalAdapter(requireContext(), List.of(), album -> {
            Intent intent = new Intent(getContext(), AlbumSongsActivity.class);
            intent.putExtra(AlbumSongsActivity.EXTRA_COLLECTION_ID, album.getCollectionId());
            intent.putExtra(AlbumSongsActivity.EXTRA_SEARCH_TERM, album.getCollectionName()); // o cualquier término de búsqueda relacionado

            startActivity(intent);
        });


        rvSongs.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvAlbums.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvSongs.setAdapter(songAdapter);
        rvAlbums.setAdapter(albumAdapter);

        loadDefaultContent();

        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearchTerm = query;
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        btnViewAllSongs.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SongsFullListActivity.class);
            intent.putExtra("search_term", currentSearchTerm);
            startActivity(intent);
        });

        btnViewAllAlbums.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AlbumsFullListActivity.class);
            intent.putExtra("search_term", currentSearchTerm);
            startActivity(intent);
        });


        return view;
    }

    private void performSearch(String query) {
        UiUtils.hideKeyboard(requireActivity());

        if (TextUtils.isEmpty(query)) {
            loadDefaultContent();
        } else {
            loadSongs(query);
            loadAlbums(query);
        }
    }

    private void loadDefaultContent() {
        loadSongs("top hits");
        loadAlbums("greatest albums");
    }


    private void loadSongs(String term) {
        new Thread(() -> {
            List<Song> songs = SongsRepository.getInstance().searchSongs(term, DEFAULT_LIMIT, 0);
            requireActivity().runOnUiThread(() -> songAdapter.updateData(songs));
        }).start();
    }

    private void loadAlbums(String term) {
        new Thread(() -> {
            List<Album> albums = AlbumsRepository.getInstance().searchAlbums(term, DEFAULT_LIMIT, 0);
            requireActivity().runOnUiThread(() -> albumAdapter.updateData(albums));
        }).start();
    }
}