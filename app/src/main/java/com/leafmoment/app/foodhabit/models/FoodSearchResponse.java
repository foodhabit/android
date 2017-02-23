package com.leafmoment.app.foodhabit.models;

import java.util.ArrayList;
import java.util.List;

public class FoodSearchResponse {
    public String query;
    public List<String> queries;
    public List<Food> searchResults;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getQueries() {
        return queries;
    }

    public void setQueries(List<String> queries) {
        this.queries = queries;
    }

    public List<Food> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Food> searchResults) {
        this.searchResults = searchResults;
    }

    public Food getFood() {
        return getFood(0);
    }

    public Food getFood(int foodIndex) {
        Food food = getSearchResults().get(foodIndex);
        food.setQueries(queries);
        List<Food> alternatives = new ArrayList<>(getSearchResults());
        alternatives.remove(foodIndex);
        food.setAlternatives(alternatives);
        return food;
    }
}
