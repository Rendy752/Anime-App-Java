package com.example.animeappjava.models;

import java.util.List;

public class Relation {
    private final String relation;
    private final List<CommonIdentity> entry;

    public Relation(String relation, List<CommonIdentity> entry) {
        this.relation = relation;
        this.entry = entry;
    }

    public String getRelation() {
        return relation;
    }

    public List<CommonIdentity> getEntry() {
        return entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relation relation1 = (Relation) o;

        if (!relation.equals(relation1.relation)) return false;
        return entry.equals(relation1.entry);
    }

    @Override
    public int hashCode() {
        int result = relation.hashCode();
        result = 31 * result + entry.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "relation='" + relation + '\'' +
                ", entry=" + entry +
                '}';
    }
}