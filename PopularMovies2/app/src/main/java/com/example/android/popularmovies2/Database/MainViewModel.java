package com.example.android.popularmovies2.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavoriteEntry>> favorites;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the favorites from the DataBase");
        favorites = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<FavoriteEntry>> getFavorites() {
        return favorites;
    }


}
