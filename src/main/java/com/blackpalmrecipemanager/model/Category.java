package com.blackpalmrecipemanager.model;

import java.util.Objects;

public class Category {

    // Properties
    private int id;
    private String name;

    // Constructors
    public Category(){

    }

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Category(String name){
        this.name = name;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
