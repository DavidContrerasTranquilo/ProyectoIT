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
import com.example.myapplication.data.model.Album;

import java.util.List;

public class AlbumHorizontalAdapter extends RecyclerView.Adapter<AlbumHorizontalAdapter.AlbumViewHolder> {

    private final Context context;
    private List<Album> albums;
    private final AlbumViewHolder.OnAlbumClickListener listener;

    public AlbumHorizontalAdapter(Context context, List<Album> albums, AlbumViewHolder.OnAlbumClickListener listener) {
        this.context = context;
        this.albums = albums;
        this.listener = listener;
    }

    //Metode per actualitzar la llista d'àlbums
    public void updateData(List<Album> newAlbums) {
        this.albums = newAlbums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album_horizontal, parent, false);
        return new AlbumViewHolder(view);
    }
    //Assigna les dades a la vista de cada item
    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.tvTitle.setText(album.getCollectionName());
        holder.tvArtist.setText(album.getArtistName());
        Glide.with(context)
                .load(album.getArtworkUrl100())
                .into(holder.ivCover);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onAlbumClick(album);
        });
    }


    @Override
    public int getItemCount() {
        return albums.size();
    }

    //ViewHolder per l'element de la llista
    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvArtist;
        public interface OnAlbumClickListener {
            void onAlbumClick(Album album);
        }

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivAlbumCover);
            tvTitle = itemView.findViewById(R.id.tvAlbumTitle);
            tvArtist = itemView.findViewById(R.id.tvAlbumArtist);
        }
    }
}
