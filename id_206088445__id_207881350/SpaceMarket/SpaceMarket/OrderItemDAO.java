package SpaceMarket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
	
	//INSERT
	
    public void addOrderItem(int OID, int PID, int quantity, double priceAtPurchase) {
        String sql = "INSERT INTO orderitem (oid, pid, quantity, priceatpurchase) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, OID);
            stmt.setInt(2, PID);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, priceAtPurchase);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertOrderItem(int oid, int pid, int quantity, double priceAtPurchase) {
        String sql = "INSERT INTO orderitem (oid, pid, quantity, priceatpurchase) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oid);
            stmt.setInt(2, pid);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, priceAtPurchase);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //SELECT
    
    public List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT orderitemid, oid, pid, quantity, priceatpurchase FROM orderitem WHERE oid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem(
                    rs.getInt("orderitemid"),
                    rs.getInt("oid"),
                    rs.getInt("pid"),
                    rs.getInt("quantity"),
                    rs.getDouble("priceatpurchase")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    //DELETE
    
    public boolean deleteItemsByOrderId(int oid) {
        String sql = "DELETE FROM orderitem WHERE oid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oid);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
