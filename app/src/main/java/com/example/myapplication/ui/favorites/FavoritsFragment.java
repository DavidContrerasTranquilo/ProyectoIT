package com.example.myapplication.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SongVerticalAdapter;
import com.example.myapplication.data.model.Song;
import com.example.myapplication.data.repository.FavoritesRepository;
import com.example.myapplication.ui.detail.DetailActivity;
import com.example.myapplication.ui.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class FavoritsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SongVerticalAdapter adapter;
    private List<Song> songList = new ArrayList<>();

    public FavoritsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorits, container, false);

        recyclerView = view.findViewById(R.id.rvFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SongVerticalAdapter(getContext(), songList, new SongVerticalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Constants.EXTRA_SONG, song);
                startActivity(intent);
            }

            @Override
            public void onRemoveClick(Song song) {
                FavoritesRepository.getInstance(getContext()).removeFavorite(song.getTrackId());
                loadFavorites();
            }
        });

        recyclerView.setAdapter(adapter);
        loadFavorites();

        return view;
    }

    //recarga la lista y el elemento eliminado ya no aparecera al volver atras
    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        songList.clear();
        songList.addAll(FavoritesRepository.getInstance(getContext()).getAllFavorites());
        adapter.notifyDataSetChanged();
    }
}
