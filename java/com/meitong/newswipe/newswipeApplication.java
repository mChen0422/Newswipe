package com.meitong.newswipe;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.meitong.newswipe.database.newswipeDatabase;

public class newswipeApplication extends Application {
    private static newswipeDatabase database;

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, newswipeDatabase.class, "tinnews_db").build();
//        database.articleDao().getAllArticles()
        context = getApplicationContext();
    }

    public static newswipeDatabase getDatabase() {
        return database;
    }
}
