package org.mykitchen.mcp;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("✅ Smart Pantry MCP Server started");

        SQLiteService pantry = new SQLiteService("data/pantry.db");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();

            try {
                JSONObject request = new JSONObject(line);
                String command = request.getString("command");

                switch (command) {
                    case "getPantryItems":
                        JSONArray items = pantry.getIngredients();
                        respondOk(items);
                        break;

                    case "addIngredient":
                        JSONObject ingredient = request.getJSONObject("data");
                        pantry.addIngredient(
                                ingredient.getString("name"),
                                ingredient.getDouble("quantity"),
                                ingredient.getString("unit"),
                                ingredient.getString("expiry")
                        );
                        respondOk("Ingredient added");
                        break;

                    default:
                        respondError("Unknown command: " + command);
                }

            } catch (Exception e) {
                respondError("Error parsing request: " + e.getMessage());
            }
        }
    }

    private static void respondOk(Object result) {
        JSONObject response = new JSONObject();
        response.put("status", "ok");
        response.put("result", result);
        System.out.println(response.toString());
    }

    private static void respondError(String msg) {
        JSONObject response = new JSONObject();
        response.put("status", "error");
        response.put("message", msg);
        System.out.println(response.toString());
    }
}