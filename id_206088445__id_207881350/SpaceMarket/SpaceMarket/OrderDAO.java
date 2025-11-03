package SpaceMarket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
	
	//INSERT
	
    public int createOrder(int UID, double totalAmount, java.util.Date purchaseDate) {
        String sql = "INSERT INTO orders (uid, totalamount, purchasedate) VALUES (?, ?, ?) RETURNING oid";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UID);
            stmt.setDouble(2, totalAmount);
            stmt.setDate(3, new java.sql.Date(purchaseDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("oid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    //SELECT
    
    public List<Order> getOrdersByDate(Date date) {
        String sql = "SELECT * FROM orders WHERE purchasedate = ?";
        List<Order> result = new ArrayList<>();

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("oid"),
                    rs.getInt("uid"),
                    rs.getDouble("totalamount"),
                    rs.getDate("purchasedate")
                );
                result.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public List<Order> getOrdersByUserId(int UID) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE uid = ? ORDER BY purchasedate DESC";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int oid = rs.getInt("oid");
                Date purchaseDate = rs.getDate("purchasedate");
                double totalAmount = rs.getDouble("totalamount");
                Order order = new Order(oid, UID, totalAmount, purchaseDate);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<Order> getOrdersByBuyerUsername(String buyerUsername) {
        List<Order> orders = new ArrayList<>();
        String sql = """
            SELECT o.oid, o.uid, o.totalamount, o.purchasedate
            FROM orders o
            JOIN users u ON o.uid = u.uid
            WHERE u.username = ? """;
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, buyerUsername);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("oid"),
                    rs.getInt("uid"),
                    rs.getDouble("totalamount"),
                    rs.getDate("purchasedate")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<Integer> getOrderIdsByUserId(int uid) {
        List<Integer> orderIds = new ArrayList<>();
        String sql = "SELECT oid FROM orders WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, uid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderIds.add(rs.getInt("oid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderIds;
    }
    
    //DELETE
    
    public boolean deleteOrdersByUserId(int uid) {
        String sql = "DELETE FROM orders WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, uid);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
