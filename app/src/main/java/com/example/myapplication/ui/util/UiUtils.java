package com.example.myapplication.ui.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UiUtils {
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // Oculta el teclado una vez se a escrito
        }
    }
}
