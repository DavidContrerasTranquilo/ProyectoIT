package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.favorites.FavoritsFragment;
import com.example.myapplication.ui.guess.EsbrinaFragment;
import com.example.myapplication.ui.search.CercaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_search) {
                selectedFragment = new CercaFragment();
            }

            else if (itemId == R.id.nav_guess) {
                selectedFragment = new EsbrinaFragment();
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritsFragment();
            }



            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Carregar per defecte CercaFragment
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
    }
}