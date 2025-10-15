package db.mysql;

import entity.Item;
import external.TicketMasterAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class MySQLConnection implements db.DBConnection{

    private Connection conn;

    public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                MySQLDBUtil.URL,
                MySQLDBUtil.USERNAME,
                MySQLDBUtil.PASSWORD
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }

    @Override
    public void setFavoriteItems(String userId, List<String> itemIds) {

    }

    @Override
    public void unsetFavoriteItems(String userId, List<String> itemIds) {

    }

    @Override
    public Set<String> getFavoriteItemIds(String userId) {
        return Set.of();
    }

    @Override
    public Set<Item> getFavoriteItems(String userId) {
        return Set.of();
    }

    @Override
    public Set<String> getCategories(String itemId) {
        return Set.of();
    }

    @Override
    public List<Item> searchItems(double lat, double lon, String term) {
        TicketMasterAPI tmAPI = new TicketMasterAPI();
        List<Item> items = tmAPI.search(lat, lon, term);
        for (Item item : items) {
            saveItem(item);
        }
        return items;

    }

    @Override
    public void saveItem(Item item) {
        if (conn == null) {
            return;
        }

        try {
            String sql = "INSERT IGNORE INTO items VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getItemId());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getRating());
            stmt.setString(4, item.getAddress());
            stmt.setString(5, item.getImageUrl());
            stmt.setString(6, item.getUrl());
            stmt.setDouble(7, item.getDistance());
            stmt.execute();

            sql = "INSERT IGNORE INTO categories VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            for (String category : item.getCategories()) {
                stmt.setString(1, item.getItemId());
                stmt.setString(2, category);
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getFullname(String userId) {
        return "";
    }

    @Override
    public boolean verifyLogin(String userId, String password) {
        return false;
    }
}
