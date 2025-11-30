package org.mykitchen.mcp;

import java.util.*;
import com.google.gson.*;
import org.mykitchen.mcp.models.Ingredient;

public class SmartPantryServer {

    private static PantryService pantry = new PantryService();
    private static RecipeService recipes = new RecipeService();
    private static Gson gson = new Gson();

    public static PantryService getPantry() {
        return pantry;
    }

    public static void main(String[] args) {
        System.out.println("SmartPantry MCP Server Running...");

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            JsonObject req = JsonParser.parseString(input).getAsJsonObject();
            String method = req.get("method").getAsString();

            JsonObject resp = new JsonObject();

            switch(method) {
                case "addIngredient":
                    Ingredient ing = gson.fromJson(req.get("params"), Ingredient.class);
                    resp.addProperty("result", pantry.addIngredient(ing));
                    break;

                case "listIngredients":
                    resp.add("result", gson.toJsonTree(pantry.listIngredients()));
                    break;

                case "removeIngredient":
                    String ingName = req.get("params").getAsJsonObject().get("name").getAsString();
                    resp.addProperty("result", pantry.removeIngredient(ingName));
                    break;

                case "listRecipes":
                    resp.add("result", gson.toJsonTree(recipes.listRecipes()));
                    break;

                case "suggestRecipes":
                    resp.add("result", gson.toJsonTree(recipes.suggestRecipes()));
                    break;

                default:
                    resp.addProperty("error", "unknown method");
            }

            System.out.println(gson.toJson(resp));
        }
    }
}