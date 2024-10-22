package com.example.animeappjava.data.local.entities;

import androidx.room.TypeConverter;

import com.example.animeappjava.models.Aired;
import com.example.animeappjava.models.Broadcast;
import com.example.animeappjava.models.CommonIdentity;
import com.example.animeappjava.models.Images;
import com.example.animeappjava.models.NameAndUrl;
import com.example.animeappjava.models.Relation;
import com.example.animeappjava.models.Theme;
import com.example.animeappjava.models.Title;
import com.example.animeappjava.models.Trailer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class AnimeDetailConverter {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromImages(Images images) {
        return gson.toJson(images);
    }

    @TypeConverter
    public static Images toImages(String imagesJson) {
        return gson.fromJson(imagesJson, Images.class);
    }

    @TypeConverter
    public static String fromTrailer(Trailer trailer) {
        return gson.toJson(trailer);
    }

    @TypeConverter
    public static Trailer toTrailer(String trailerJson) {
        return gson.fromJson(trailerJson, Trailer.class);
    }

    @TypeConverter
    public static String fromTitlesList(List<Title> titles) {
        return gson.toJson(titles);
    }

    @TypeConverter
    public static List<Title> toTitlesList(String titlesJson) {
        Type type = new TypeToken<List<Title>>() {}.getType();
        return gson.fromJson(titlesJson, type);
    }

    @TypeConverter
    public static String fromStringArray(String[] strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @TypeConverter
    public static String[] toStringArray(String string) {
        if (string == null || string.isEmpty()) {
            return new String[0];
        }
        return string.split(",");
    }

    @TypeConverter
    public static String fromAired(Aired aired) {
        return gson.toJson(aired);
    }

    @TypeConverter
    public static Aired toAired(String airedJson) {
        return gson.fromJson(airedJson, Aired.class);
    }

    @TypeConverter
    public static String fromBroadcast(Broadcast broadcast) {
        return gson.toJson(broadcast);
    }

    @TypeConverter
    public static Broadcast toBroadcast(String broadcastJson) {
        return gson.fromJson(broadcastJson, Broadcast.class);
    }

    @TypeConverter
    public static String fromCommonIdentityList(List<CommonIdentity> commonIdentities) {
        return gson.toJson(commonIdentities);
    }

    @TypeConverter
    public static List<CommonIdentity> toCommonIdentityList(String commonIdentitiesJson) {
        if (commonIdentitiesJson == null) {
            return null;
        }
        Type type = new TypeToken<List<CommonIdentity>>() {}.getType();
        return gson.fromJson(commonIdentitiesJson, type);
    }

    @TypeConverter
    public static String fromNullableRelationList(List<Relation> relations) {
        return gson.toJson(relations);
    }

    @TypeConverter
    public static List<Relation> toNullableRelationList(String relationsJson) {
        if (relationsJson == null) {
            return null;
        }
        Type type = new TypeToken<List<Relation>>() {}.getType();
        return gson.fromJson(relationsJson, type);
    }

    @TypeConverter
    public static String fromTheme(Theme theme) {
        return gson.toJson(theme);
    }

    @TypeConverter
    public static Theme toTheme(String themeJson) {
        return gson.fromJson(themeJson, Theme.class);
    }

    @TypeConverter
    public static String fromNameAndUrlList(List<NameAndUrl> nameAndUrls) {
        return gson.toJson(nameAndUrls);
    }

    @TypeConverter
    public static List<NameAndUrl> toNameAndUrlList(String nameAndUrlsJson) {
        if (nameAndUrlsJson == null) {
            return null;
        }
        Type type = new TypeToken<List<NameAndUrl>>() {}.getType();
        return gson.fromJson(nameAndUrlsJson, type);
    }
}