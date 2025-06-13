package com.example.myapplication.ui.guess;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Song;
import com.example.myapplication.data.repository.FavoritesRepository;
import com.example.myapplication.ui.util.UiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EsbrinaFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private List<Song> songList;
    private Song currentSong;
    private int currentIndex = 0;
    private int attempts = 0;
    private int guessedCount = 0;
    private static final int MAX_ATTEMPTS = 3;

    private Button btnRestart;
    private TextView tvGuessed, tvRemaining, tvAttempts;

    private Button btnPlay;
    private EditText etGuess;
    private TextView tvFeedback, tvProgress;

    public EsbrinaFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_esbrina, container, false);

        btnPlay = view.findViewById(R.id.btnPlay);
        etGuess = view.findViewById(R.id.etGuess);
        tvFeedback = view.findViewById(R.id.tvFeedback);
        tvGuessed = view.findViewById(R.id.tvGuessed);
        tvRemaining = view.findViewById(R.id.tvRemaining);
        tvAttempts = view.findViewById(R.id.tvAttempts);
        btnRestart = view.findViewById(R.id.btnRestart);

        songList = new ArrayList<>(FavoritesRepository.getInstance(requireContext()).getAllFavorites());
        Collections.shuffle(songList);

        setupListeners();
        loadNextSong();

        return view;
    }

    private void setupListeners() {
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setText(R.string.play);
            } else {
                if (mediaPlayer == null) {
                    prepareMediaPlayer();
                }
                mediaPlayer.start();
                btnPlay.setText(R.string.pause);
            }
        });

        etGuess.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                checkGuess();
                return true;
            }
            return false;
        });
        btnRestart.setOnClickListener(v -> restartGame());

    }
    private void restartGame() {
        songList = new ArrayList<>(FavoritesRepository.getInstance(requireContext()).getAllFavorites());
        Collections.shuffle(songList);
        currentIndex = 0;
        guessedCount = 0;
        attempts = 0;

        btnPlay.setEnabled(true);
        etGuess.setEnabled(true);
        btnRestart.setVisibility(View.GONE);
        loadNextSong();
    }

    private void loadNextSong() {
        releaseMediaPlayer();

        if (currentIndex >= songList.size()) {
            tvFeedback.setText(R.string.finished_all_songs);
            btnPlay.setEnabled(false);
            etGuess.setEnabled(false);
            btnRestart.setVisibility(View.VISIBLE);
            updateProgress();
            return;
        }

        currentSong = songList.get(currentIndex);
        attempts = 0;
        updateProgress();

        btnPlay.setText(R.string.play);
        etGuess.setText("");
        tvFeedback.setText("");
    }



    private void checkGuess() {
        String userGuess = etGuess.getText().toString().trim();
        if (TextUtils.isEmpty(userGuess)) return;

        UiUtils.hideKeyboard(requireActivity());

        if (userGuess.equalsIgnoreCase(currentSong.getTrackName())) {
            guessedCount++;
            tvFeedback.setText(R.string.correct_guess);

            tvFeedback.postDelayed(() -> {
                currentIndex++;
                loadNextSong();
            }, 1000);
        }else {
            attempts++;
            updateProgress();

            if (attempts >= MAX_ATTEMPTS) {
                String msg = getString(R.string.wrong_guess_limit, currentSong.getTrackName());
                tvFeedback.setText(msg);

                tvFeedback.postDelayed(() -> {
                    currentIndex++;
                    loadNextSong();
                }, 1500);
            }
            else {
                int remaining = MAX_ATTEMPTS - attempts;
                String msg = getString(R.string.wrong_guess_retry, remaining);
                tvFeedback.setText(msg);
            }
        }
    }


    private void updateProgress() {
        int remaining = songList.size() - currentIndex;

        tvGuessed.setText(getString(R.string.guessed_label, guessedCount, songList.size()));
        tvRemaining.setText(getString(R.string.remaining_label, remaining));
        tvAttempts.setText(getString(R.string.attempts_label, attempts, MAX_ATTEMPTS));
    }



    private void prepareMediaPlayer() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(currentSong.getPreviewUrl());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(mp -> btnPlay.setText(R.string.play));
        } catch (Exception e) {
            tvFeedback.setText(R.string.media_error);
            e.printStackTrace();
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseMediaPlayer();
    }
}