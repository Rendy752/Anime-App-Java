package com.example.animeappjava.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.animeappjava.data.local.dao.AnimeDetailDao;
import com.example.animeappjava.data.local.entities.AnimeDetailConverter;
import com.example.animeappjava.models.AnimeDetail;

@Database(entities = {AnimeDetail.class}, version = 6, exportSchema = false)
@TypeConverters(AnimeDetailConverter.class)
public abstract class AnimeDetailDatabase extends RoomDatabase {

    public abstract AnimeDetailDao getAnimeDetailDao();

    private static volatile AnimeDetailDatabase INSTANCE;

    public static AnimeDetailDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimeDetailDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AnimeDetailDatabase.class, "anime_detail_db.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}