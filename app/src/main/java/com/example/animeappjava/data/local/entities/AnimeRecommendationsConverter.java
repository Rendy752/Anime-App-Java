package com.example.animeappjava.data.local.entities;

import androidx.room.TypeConverter;

import com.example.animeappjava.models.AnimeHeader;
import com.example.animeappjava.models.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnimeRecommendationsConverter {

    @TypeConverter
    public static String fromAnimeHeaderList(List<AnimeHeader> value) {
        if (value == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.size(); i++) {
            sb.append(value.get(i).getMal_id());
            if (i < value.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @TypeConverter
    public static List<AnimeHeader> toAnimeHeaderList(String value) {
        if (value == null || value.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> malIds = Arrays.asList(value.split(","));
        List<AnimeHeader> animeHeaders = new ArrayList<>();
        for (String malId : malIds) {
            animeHeaders.add(new AnimeHeader(Integer.parseInt(malId), ""));
        }
        return animeHeaders;
    }

    @TypeConverter
    public static String fromUser(User value) {
        if (value == null) {
            return null;
        }
        return value.getUsername();
    }

    @TypeConverter
    public static User toUser(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new User(value, "", "");
    }
}