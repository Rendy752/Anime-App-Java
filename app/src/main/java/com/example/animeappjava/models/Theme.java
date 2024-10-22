package com.example.animeappjava.models;

import java.util.List;

public class Theme {
    private final List<String> openings;
    private final List<String> endings;

    public Theme(List<String> openings, List<String> endings) {
        this.openings = openings;
        this.endings = endings;
    }

    public List<String> getOpenings() {
        return openings;
    }

    public List<String> getEndings() {
        return endings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theme theme = (Theme) o;

        if (openings != null ? !openings.equals(theme.openings) : theme.openings != null) return false;
        return endings != null ? endings.equals(theme.endings) : theme.endings == null;
    }

    @Override
    public int hashCode() {
        int result = openings != null ? openings.hashCode() : 0;
        result = 31 * result + (endings != null ? endings.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "openings=" + openings +
                ", endings=" + endings +
                '}';
    }
}