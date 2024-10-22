package com.example.animeappjava.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.animeappjava.data.local.entities.AnimeDetailConverter;

import java.util.List;

@Entity(tableName = "anime_detail")
@TypeConverters(AnimeDetailConverter.class)
public class AnimeDetail {

    @PrimaryKey
    @NonNull
    public final int mal_id;
    public final String url;
    public final Images images;
    public final Trailer trailer;
    public final boolean approved;
    public final List<Title> titles;
    public final String title;
    public final String title_english;
    public final String title_japanese;
    public final String[] title_synonyms;
    public final String type;
    public final String source;
    public final int episodes;
    public final String status;
    public final boolean airing;
    public final Aired aired;
    public final String duration;
    public final String rating;
    public final double score;
    public final int scored_by;
    public final int rank;
    public final int popularity;
    public final int members;
    public final int favorites;
    public final String synopsis;
    public final String background;
    public final String season;
    public final Integer year;
    public final Broadcast broadcast;
    public final List<CommonIdentity> producers;
    public final List<CommonIdentity> licensors;
    public final List<CommonIdentity> studios;
    public final List<CommonIdentity> genres;
    public final List<CommonIdentity> explicit_genres;
    public final List<CommonIdentity> themes;
    public final List<CommonIdentity> demographics;
    public final List<Relation> relations;
    public final Theme theme;
    public final List<NameAndUrl> external;
    public final List<NameAndUrl> streaming;


    public AnimeDetail(int mal_id, String url, Images images, Trailer trailer,
                       boolean approved, List<Title> titles, String title, String title_english,
                       String title_japanese, String[] title_synonyms, String type, String source,
                       int episodes, String status, boolean airing, Aired aired, String duration,
                       String rating, double score, int scored_by, int rank, int popularity,
                       int members, int favorites, String synopsis, String background, String season,
                       Integer year, Broadcast broadcast, List<CommonIdentity> producers,
                       List<CommonIdentity> licensors, List<CommonIdentity> studios,
                       List<CommonIdentity> genres, List<CommonIdentity> explicit_genres,
                       List<CommonIdentity> themes, List<CommonIdentity> demographics,
                       List<Relation> relations, Theme theme, List<NameAndUrl> external,
                       List<NameAndUrl> streaming) {
        this.mal_id = mal_id;
        this.url = url;
        this.images = images;
        this.trailer = trailer;
        this.approved = approved;
        this.titles = titles;
        this.title = title;
        this.title_english = title_english;
        this.title_japanese = title_japanese;
        this.title_synonyms = title_synonyms;
        this.type = type;
        this.source = source;
        this.episodes = episodes;
        this.status = status;
        this.airing = airing;
        this.aired = aired;
        this.duration = duration;
        this.rating = rating;
        this.score = score;
        this.scored_by = scored_by;
        this.rank = rank;
        this.popularity = popularity;
        this.members = members;
        this.favorites = favorites;
        this.synopsis = synopsis;
        this.background = background;
        this.season = season;
        this.year = year;
        this.broadcast = broadcast;
        this.producers = producers;
        this.licensors = licensors;
        this.studios = studios;
        this.genres = genres;
        this.explicit_genres = explicit_genres;
        this.themes = themes;
        this.demographics = demographics;
        this.relations = relations;
        this.theme = theme;
        this.external = external;
        this.streaming = streaming;
    }

    public int getMal_id() {return mal_id;}
    public String getUrl() {
        return url;
    }
    public Images getImages() {
        return images;
    }
    public Trailer getTrailer() {
        return trailer;
    }
    public boolean isApproved() {
        return approved;
    }
    public List<Title> getTitles() {
        return titles;
    }
    public String getTitle() {
        return title;
    }
    public String getTitle_english() {
        return title_english;
    }
    public String getTitle_japanese() {
        return title_japanese;
    }
    public String[] getTitle_synonyms() {
        return title_synonyms;
    }
    public String getType() {
        return type;
    }
    public String getSource() {
        return source;
    }
    public int getEpisodes() {
        return episodes;
    }
    public String getStatus() {
        return status;
    }
    public boolean isAiring() {
        return airing;
    }
    public Aired getAired() {
        return aired;
    }
    public String getDuration() {
        return duration;
    }
    public String getRating() {
        return rating;
    }
    public double getScore() {
        return score;
    }
    public int getScoredBy() {
        return scored_by;
    }
    public int getRank() {
        return rank;
    }
    public int getPopularity() {
        return popularity;
    }
    public int getMembers() {
        return members;
    }
    public int getFavorites() {
        return favorites;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public String getBackground() {
        return background;
    }
    public String getSeason() {
        return season;
    }
    public Integer getYear() {
        return year;
    }
    public Broadcast getBroadcast() {
        return broadcast;
    }
    public List<CommonIdentity> getProducers() {
        return producers;
    }
    public List<CommonIdentity> getLicensors() {
        return licensors;
    }
    public List<CommonIdentity> getStudios() {
        return studios;
    }
    public List<CommonIdentity> getGenres() {
        return genres;
    }
    public List<CommonIdentity> getExplicit_genres() {
        return explicit_genres;
    }
    public List<CommonIdentity> getThemes() {
        return themes;
    }
    public List<CommonIdentity> getDemographics() {
        return demographics;
    }
    public List<Relation> getRelations() {
        return relations;
    }
    public Theme getTheme() {
        return theme;
    }
    public List<NameAndUrl> getExternal() {
        return external;
    }
    public List<NameAndUrl> getStreaming() {
        return streaming;
    }
}