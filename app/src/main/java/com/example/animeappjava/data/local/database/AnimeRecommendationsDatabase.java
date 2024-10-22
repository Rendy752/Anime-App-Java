package com.example.animeappjava.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.animeappjava.data.local.dao.AnimeRecommendationsDao;
import com.example.animeappjava.data.local.entities.AnimeRecommendationsConverter;
import com.example.animeappjava.models.AnimeRecommendation;

@Database(entities = {AnimeRecommendation.class}, version = 1, exportSchema = false)
@TypeConverters(AnimeRecommendationsConverter.class)
public abstract class AnimeRecommendationsDatabase extends RoomDatabase {

    public abstract AnimeRecommendationsDao getAnimeRecommendationsDao();

    private static volatile AnimeRecommendationsDatabase INSTANCE;

    public static AnimeRecommendationsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimeRecommendationsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AnimeRecommendationsDatabase.class, "anime_recommendations_db.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}