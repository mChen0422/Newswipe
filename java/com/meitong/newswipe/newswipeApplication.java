package com.meitong.newswipe;

import android.app.Application;

import androidx.room.Room;

import com.meitong.newswipe.database.newswipeDatabase;

public class newswipeApplication extends Application {
    private static newswipeDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, newswipeDatabase.class, "newswipe_db").build();
//        database.articleDao().getAllArticles()
    }

    public static newswipeDatabase getDatabase() {
        return database;
    }
}
