package com.example.animeappjava.models;

public class Pagination {
    private final int last_visible_page;
    private final boolean has_next_page;

    public Pagination(int last_visible_page, boolean has_next_page) {
        this.last_visible_page = last_visible_page;
        this.has_next_page = has_next_page;
    }

    public int getLastVisiblePage() {
        return last_visible_page;
    }

    public boolean isHasNextPage() {
        return has_next_page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pagination that = (Pagination) o;

        if (last_visible_page != that.last_visible_page) return false;
        return has_next_page == that.has_next_page;
    }

    @Override
    public int hashCode() {
        int result = last_visible_page;
        result = 31 * result + (has_next_page ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "last_visible_page=" + last_visible_page +
                ", has_next_page=" + has_next_page +
                '}';
    }
}