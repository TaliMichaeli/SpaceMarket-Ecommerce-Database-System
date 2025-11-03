package SpaceMarket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartItemDAO {
    
    public void addItemToCart(int CID, int PID, int quantity) {
        String selectSql = "SELECT quantity FROM shoppingcartitem WHERE cid = ? AND pid = ?";
        String insertSql = "INSERT INTO shoppingcartitem (cid, pid, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE shoppingcartitem SET quantity = quantity + ? WHERE cid = ? AND pid = ?";
        try (Connection conn = DataBaseManager.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, CID);
                selectStmt.setInt(2, PID);
                ResultSet rs = selectStmt.executeQuery();
                if (!rs.next()) { 
                	 try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                         insertStmt.setInt(1, CID);
                         insertStmt.setInt(2, PID);
                         insertStmt.setInt(3, quantity);
                         insertStmt.executeUpdate();}//if the product exists
                } else {
                	try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, quantity);
                        updateStmt.setInt(2, CID);
                        updateStmt.setInt(3, PID);
                        updateStmt.executeUpdate();}
                }
                conn.commit();
            }
            catch (SQLException innerEx) {
                if (conn != null) conn.rollback();
                throw innerEx;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //SELECT
    public double getTotalPrice(int CID) { 
        String sql = """
            SELECT SUM(p.productprice * sci.quantity) AS total
            FROM shoppingcartitem sci
            JOIN product p ON sci.pid = p.pid
            WHERE sci.cid = ? """;
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, CID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    public List<ShoppingCartItem> getItemsByCartId(int cartId) {
        List<ShoppingCartItem> items = new ArrayList<>();
        String sql = "SELECT * FROM shoppingcartitem WHERE cid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                int pid = rs.getInt("pid");
                int quantity = rs.getInt("quantity");
                items.add(new ShoppingCartItem(cid, pid, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    
    //DELETE
    
    public void removeItem(int CartItemID) {
        String sql = "DELETE FROM shoppingcartitem WHERE cartitemid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, CartItemID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void clearCart(int CartItemID) {
        String sql = "DELETE FROM shoppingcartitem WHERE cid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, CartItemID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean deleteItemFromCart(int cartId, int pid) {
        String sql = "DELETE FROM shoppingcartitem WHERE cid = ? AND pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, pid);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void deleteAllItemsFromCart(int cartId) {
        String sql = "DELETE FROM shoppingcartitem WHERE cid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
