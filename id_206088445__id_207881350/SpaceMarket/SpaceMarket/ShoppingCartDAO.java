package SpaceMarket;
import java.sql.*;

public class ShoppingCartDAO {
	
	//INSERT
	
    public int createCart(int userId) {
        String sql = "INSERT INTO shoppingcart (uid) VALUES (?) RETURNING cid";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) 
            	return rs.getInt("cid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    //SELECT
    
    public Integer getCartIdByUserId(int userId) {
        String sql = "SELECT cid FROM shoppingcart WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) 
            	return rs.getInt("cid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //DELETE
    
    public boolean deleteCart(int cartId) {
        String sql = "DELETE FROM shoppingcart WHERE cid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
