package com.example.animeappjava.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.animeappjava.data.local.entities.AnimeRecommendationsConverter;

import java.util.List;

@Entity(tableName = "anime_recommendations")
@TypeConverters({AnimeRecommendationsConverter.class})
public class AnimeRecommendation {

    @PrimaryKey
    @NonNull
    private final String mal_id;
    private final List<AnimeHeader> entry;
    private final String content;
    private final String date;
    private final User user;

    public AnimeRecommendation(String mal_id, List<AnimeHeader> entry, String content, String date, User user) {
        this.mal_id = mal_id;
        this.entry = entry;
        this.content = content;
        this.date = date;
        this.user = user;
    }

    public String getMal_id() {
        return mal_id;
    }

    public List<AnimeHeader> getEntry() {
        return entry;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimeRecommendation that = (AnimeRecommendation) o;

        if (!mal_id.equals(that.mal_id)) return false;
        if (!entry.equals(that.entry)) return false;
        if (!content.equals(that.content)) return false;
        if (!date.equals(that.date)) return false;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = mal_id.hashCode();
        result = 31 * result + entry.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AnimeRecommendation{" +
                "mal_id='" + mal_id + '\'' +
                ", entry=" + entry +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", user=" + user +
                '}';
    }
}