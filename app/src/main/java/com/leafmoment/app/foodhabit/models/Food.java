package com.leafmoment.app.foodhabit.models;

import android.graphics.Bitmap;

import java.util.List;

public class Food {
    public String name;
    public String ndbno;

    public Bitmap snapshot; // TODO: Make this not depend on Android
    public List<String> queries;
    public List<Food> alternatives;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public Bitmap getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Bitmap snapshot) {
        this.snapshot = snapshot;
    }

    public List<Food> getAlternatives() {
        return alternatives;
    }

    public List<String> getQueries() {
        return queries;
    }

    public void setQueries(List<String> queries) {
        this.queries = queries;
    }

    public void setAlternatives(List<Food> alternatives) {
        this.alternatives = alternatives;
    }
}
