package com.tetiana.bakingapp;


import android.app.Activity;
import android.util.DisplayMetrics;

class NumberOfColumns {
    int numberOfColumns(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 3) return 1;
        if (nColumns < 9) return 3;
        return nColumns;
    }
}
