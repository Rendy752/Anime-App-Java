package com.example.animeappjava.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class Resource<T> {
    @Nullable
    private final T data;
    @Nullable
    private final String message;

    private Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    @Nullable
    public final T getData() {
        return this.data;
    }

    @Nullable
    public final String getMessage() {
        return this.message;
    }


    public static final class Error<T> extends Resource<T> {
        public Error(@NonNull String message, @Nullable T data) {
            super(data, message);
        }
    }

    public static <T> Resource<T> loading() {
        return new Loading<>();
    }

    public static final class Loading<T> extends Resource<T> {
        public Loading() {
            super(null, null);
        }
    }

    public static final class Success<T> extends Resource<T> {
        public Success(T data) {
            super(data, null);
        }
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Success<>(data);
    }

    public static <T> Resource<T> error(@NonNull String msg, @Nullable T data) {
        return new Error<>(msg, data);
    }
}