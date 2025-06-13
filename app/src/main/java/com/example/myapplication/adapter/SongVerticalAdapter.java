package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Song;

import java.util.List;

public class SongVerticalAdapter extends RecyclerView.Adapter<SongVerticalAdapter.SongViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Song song);
        void onRemoveClick(Song song);
    }

    private final Context context;
    private final List<Song> songList;
    private final OnItemClickListener listener;

    public SongVerticalAdapter(Context context, List<Song> songList, OnItemClickListener listener) {
        this.context = context;
        this.songList = songList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_vertical, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);

        holder.tvTitle.setText(song.getTrackName());
        holder.tvArtist.setText(song.getArtistName());

        Glide.with(context)
                .load(song.getArtworkUrl100())
                .into(holder.ivCover);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(song);
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) listener.onRemoveClick(song);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvArtist;
        ImageView btnRemove;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivSongCover);
            tvTitle = itemView.findViewById(R.id.tvSongTitle);
            tvArtist = itemView.findViewById(R.id.tvSongArtist);
            btnRemove = itemView.findViewById(R.id.ivRemoveFavorite);
        }
    }
}
