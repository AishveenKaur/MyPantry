package org.mykitchen.mcp;
import java.sql.*;
import java.util.*;
import org.mykitchen.mcp.models.Ingredient;

public class PantryService {
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:data/pantry.db");
    }

    public String addIngredient(Ingredient ing) {
        try (Connection con = connect()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ingredients(name, quantity) VALUES (?, ?) " +
                            "ON CONFLICT(name) DO UPDATE SET quantity = quantity + ?;"
            );
            ps.setString(1, ing.name);
            ps.setInt(2, ing.quantity);
            ps.setInt(3, ing.quantity);
            ps.executeUpdate();
            return "Added";
        } catch (Exception e) { return e.getMessage(); }
    }

    public List<Ingredient> listIngredients() {
        List<Ingredient> list = new ArrayList<>();
        try (Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM ingredients")) {
            while (rs.next()) {
                list.add(new Ingredient(rs.getString("name"), rs.getInt("quantity")));
            }
        } catch (Exception ignored) {}
        return list;
    }

    public String removeIngredient(String name) {
        try (Connection con = connect()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM ingredients WHERE name=?");
            ps.setString(1, name);
            ps.executeUpdate();
            return "Removed";
        } catch (Exception e) { return e.getMessage(); }
    }
}
