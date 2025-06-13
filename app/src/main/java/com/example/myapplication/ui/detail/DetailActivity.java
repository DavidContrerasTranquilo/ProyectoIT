package com.example.myapplication.ui.detail;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Song;
import com.example.myapplication.data.repository.FavoritesRepository;
import com.example.myapplication.ui.util.Constants;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivArtwork;
    private TextView tvTitle, tvArtist, tvAlbum, tvReleaseDate, tvPrice, tvGenre;
    private Button btnFavorite;
    private ImageButton btnPlay;
    private MediaPlayer mediaPlayer;

    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivArtwork      = findViewById(R.id.ivArtwork);
        tvTitle        = findViewById(R.id.tvTitle);
        tvArtist       = findViewById(R.id.tvArtist);
        tvAlbum        = findViewById(R.id.tvAlbum);
        tvReleaseDate  = findViewById(R.id.tvReleaseDate);
        tvPrice        = findViewById(R.id.tvPrice);
        tvGenre        = findViewById(R.id.tvGenre);
        btnFavorite    = findViewById(R.id.btnFavorite);
        btnPlay        = findViewById(R.id.btnPlay);

        song = (Song) getIntent().getSerializableExtra(Constants.EXTRA_SONG);
        if (song == null) {
            Toast.makeText(this, "Error al cargar la canción", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        tvTitle.setText(song.getTrackName());
        tvArtist.setText(song.getArtistName());
        tvAlbum.setText(song.getCollectionName());
        tvReleaseDate.setText(formatDate(song.getReleaseDate()));
        tvPrice.setText(String.format(Locale.getDefault(),
                "%.2f %s", song.getTrackPrice(), "€"));
        tvGenre.setText(song.getPrimaryGenreName());

        Glide.with(this)
                .load(song.getArtworkUrl100())
                .placeholder(R.drawable.discover)
                .into(ivArtwork);


        updateFavoriteButton();
        btnFavorite.setOnClickListener(v -> {
            FavoritesRepository favMgr = FavoritesRepository.getInstance(this);
            if (favMgr.isFavorite(song.getTrackId())) {
                favMgr.removeFavorite(song.getTrackId());
                Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
            } else {
                favMgr.addFavorite(song);
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            }
            updateFavoriteButton();
        });
        // Reproducir la canción
        btnPlay.setOnClickListener(v -> handlePlayPause());
    }

    private void updateFavoriteButton() {
        boolean isFav = FavoritesRepository.getInstance(this)
                .isFavorite(song.getTrackId());
        btnFavorite.setText(isFav ? "Quitar de favoritos" : "Añadir a favoritos");
    }

    private void handlePlayPause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.ic_play_arrow);
        } else {
            if (mediaPlayer == null) initMediaPlayer();
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.ic_pause);
            mediaPlayer.setOnCompletionListener(mp ->
                    btnPlay.setImageResource(R.drawable.ic_play_arrow));
        }
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(song.getPreviewUrl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo reproducir la canción", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    //Para formatear la fecha
    private String formatDate(String isoDate) {

        try {
            SimpleDateFormat src = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            Date date = src.parse(isoDate);
            SimpleDateFormat dst = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return (date != null) ? dst.format(date) : "";
        } catch (ParseException e) {
            return isoDate;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
