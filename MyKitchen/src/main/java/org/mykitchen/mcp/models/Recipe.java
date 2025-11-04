package org.mykitchen.mcp.models;

public class Recipe {
    public String name, ingredients, instructions;
    public Recipe(String name, String ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}