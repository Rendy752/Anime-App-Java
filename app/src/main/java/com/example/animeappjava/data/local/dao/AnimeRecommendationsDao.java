package com.example.animeappjava.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.animeappjava.models.AnimeRecommendation;

import java.util.List;

@Dao
public interface AnimeRecommendationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeRecommendation(List<AnimeRecommendation> animeRecommendations);

    @Query("SELECT * FROM anime_recommendations")
    LiveData<List<AnimeRecommendation>> getAllAnimeRecommendations();

    @Delete
    void deleteAnimeRecommendation(AnimeRecommendation animeRecommendation);
}