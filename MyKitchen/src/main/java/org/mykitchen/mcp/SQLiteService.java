package org.mykitchen.mcp;

import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class SQLiteService {

    private final String dbPath;

    public SQLiteService(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    public JSONArray getIngredients() throws SQLException {
        String sql = "SELECT name, quantity, unit, expiry FROM ingredients";
        JSONArray arr = new JSONArray();

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                JSONObject row = new JSONObject();
                row.put("name", rs.getString("name"));
                row.put("quantity", rs.getDouble("quantity"));
                row.put("unit", rs.getString("unit"));
                row.put("expiry", rs.getString("expiry"));
                arr.put(row);
            }
        }

        return arr;
    }

    public void addIngredient(String name, double quantity, String unit, String expiry) throws SQLException {
        String sql = "INSERT INTO ingredients(name, quantity, unit, expiry) VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setDouble(2, quantity);
            ps.setString(3, unit);
            ps.setString(4, expiry);
            ps.executeUpdate();
        }
    }
}