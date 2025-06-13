package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Song;

import java.util.List;

public class AlbumSongsAdapter
        extends RecyclerView.Adapter<AlbumSongsAdapter.SongViewHolder> {

    public interface OnSongClickListener {
        void onSongClick(Song song);
    }

    private final Context context;
    private final List<Song> songs;
    private final OnSongClickListener listener;

    public AlbumSongsAdapter(Context ctx,
                             List<Song> songs,
                             OnSongClickListener listener) {
        this.context  = ctx;
        this.songs    = songs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_album_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder h, int pos) {
        Song s = songs.get(pos);
        h.tvTitle.setText(s.getTrackName());
        h.tvArtist.setText(s.getArtistName());

        h.itemView.setOnClickListener(v -> listener.onSongClick(s));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
    //ViewHolder per a les cançons
    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvArtist;

        public SongViewHolder(@NonNull View iv) {
            super(iv);
            tvTitle  = iv.findViewById(R.id.tvSongTitleSmall);
            tvArtist = iv.findViewById(R.id.tvSongArtistSmall);
        }
    }
}
