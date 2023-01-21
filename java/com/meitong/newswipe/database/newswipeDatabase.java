package com.meitong.newswipe.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.meitong.newswipe.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class newswipeDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}