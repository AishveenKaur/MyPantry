package org.mykitchen.mcp;
import java.sql.*;
import java.util.*;
import org.mykitchen.mcp.models.Recipe;

public class RecipeService {

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:data/pantry.db");
    }

    public List<Recipe> listRecipes() {
        List<Recipe> list = new ArrayList<>();
        try (Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM recipes")) {
            while (rs.next()) {
                list.add(new Recipe(
                        rs.getString("name"),
                        rs.getString("ingredients"),
                        rs.getString("instructions")
                ));
            }
        } catch (Exception ignored) {}
        return list;
    }

    public List<String> suggestRecipes() {
        List<String> result = new ArrayList<>();

        try (Connection con = connect()) {
            List<String> pantryItems = new ArrayList<>();
            ResultSet rs = con.createStatement().executeQuery("SELECT name FROM ingredients");

            while (rs.next()) pantryItems.add(rs.getString(1));

            ResultSet rrec = con.createStatement().executeQuery("SELECT name, ingredients FROM recipes");

            while (rrec.next()) {
                String recipe = rrec.getString("name");
                String[] req = rrec.getString("ingredients").split(", ");
                boolean canMake = true;

                for (String r : req)
                    if (!pantryItems.contains(r)) canMake = false;

                if (canMake) result.add(recipe);
            }
        } catch (Exception ignored) {}

        return result;
    }
}