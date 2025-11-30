package org.mykitchen.mcp;

import org.mykitchen.mcp.models.Ingredient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("✅ Smart Pantry MCP Server started");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = br.readLine()) != null) {

            String command = line.trim().toLowerCase().replaceAll("\\s+", " ");

            switch (command) {
                case "get pantry items" -> {
                    for (Ingredient ing : SmartPantryServer.getPantry().listIngredients()) {
                        System.out.println(ing.getName() + " (" + ing.getQuantity() + ")");
                    }
                }

                case "list ingredients" -> { // alternative wording
                    for (Ingredient ing : SmartPantryServer.getPantry().listIngredients()) {
                        System.out.println(ing.getName() + " (" + ing.getQuantity() + ")");
                    }
                }

                case "help" -> {
                    System.out.println("Commands available:");
                    System.out.println("- get pantry items");
                    System.out.println("- add <item name>");
                    System.out.println("- remove <item name>");
                }

                default -> {
                    // check for add/remove commands that start with a keyword
                    if (command.startsWith("add ")) {
                        String itemName = line.trim().substring(4);
                        Ingredient ing = new Ingredient(itemName, 1);
                        SmartPantryServer.getPantry().addIngredient(ing);
                        System.out.println("Added: " + itemName);
                    } else if (command.startsWith("remove ")) {
                        String itemName = line.trim().substring(7);
                        SmartPantryServer.getPantry().removeIngredient(itemName);
                        System.out.println("Removed: " + itemName);
                    } else {
                        System.out.println("Unknown command. Type 'help' for a list of commands.");
                    }
                }
            }
        }
    }
}