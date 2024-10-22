package com.example.animeappjava.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.animeappjava.models.AnimeDetail;

@Dao
public interface AnimeDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnimeDetail(AnimeDetail animeDetail);

    @Query("SELECT * FROM anime_detail WHERE mal_id = :id")
    AnimeDetail getAnimeDetailById(int id);

    @Delete
    void deleteAnimeDetail(AnimeDetail animeDetail);
}