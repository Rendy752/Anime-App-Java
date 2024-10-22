package com.example.animeappjava.models;

public class User {
    private final String url;
    private final String username;

    public User(String url, String username) {
        this.url = url;
        this.username = username;
    }

    public User(String url, String username, String s) {
        this(url, username);
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!url.equals(user.url)) return false;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}