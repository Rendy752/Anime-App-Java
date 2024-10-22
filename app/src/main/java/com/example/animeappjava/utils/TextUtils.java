package com.example.animeappjava.utils;

import org.apache.commons.text.StringEscapeUtils;

import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextUtils {

    public static String formatSynopsis(String synopsis) {
        return StringEscapeUtils.unescapeJava(synopsis)
                .replaceAll(Pattern.compile("[\r\n]+").pattern(), ". ")
                .replaceAll("[^a-zA-Z0-9\\s.,?']", "");
    }

    public static <T> String joinOrNA(Collection<T> list, Function<T, String> transform) {
        return list != null ?
                (list.isEmpty() ? "-" : list.stream().map(transform).collect(Collectors.joining(", "))) :
                "-";
    }
}