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
import com.example.myapplication.ui.detail.DetailActivity;
import com.example.myapplication.ui.util.Constants;

import java.util.List;

public class SongHorizontalAdapter extends RecyclerView.Adapter<SongHorizontalAdapter.SongViewHolder> {

    private final Context context;
    private List<Song> songs;

    public SongHorizontalAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    public void updateData(List<Song> newSongs) {
        this.songs = newSongs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_horizontal, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.tvTitle.setText(song.getTrackName());
        holder.tvArtist.setText(song.getArtistName());

        Glide.with(context)
                .load(song.getArtworkUrl100())
                .into(holder.ivCover);

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context, com.example.myapplication.ui.detail.DetailActivity.class);
            intent.putExtra(Constants.EXTRA_SONG, song); // Usa la key correcta
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvArtist;

        SongViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.imgCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
        }
    }
}
