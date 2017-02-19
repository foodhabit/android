package com.example.hdavidzhu.foodhabit.models;

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

    public List<Food> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Food> alternatives) {
        this.alternatives = alternatives;
    }
}
