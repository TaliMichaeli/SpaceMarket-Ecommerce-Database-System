package SpaceMarket;
import java.sql.*;
import java.util.*;

public class UserDAO {
	
	//INSERT
	
	public int insertUser(User user) {
	    String sql = "INSERT INTO users (username, password, address, roles) VALUES (?, ?, ?, ?) RETURNING uid";
	    try (Connection conn = DataBaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, user.getUserName());
	        stmt.setString(2, user.getPassword());
	        stmt.setString(3, user.getAddress());
	        stmt.setString(4, user.getRole().name());
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1;
	}
	
	//SELECT
    
    public User getUserById(int UID) {
        String sql = "SELECT * FROM users WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("uid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    Role.valueOf(rs.getString("roles"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DataBaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                    rs.getInt("uid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    Role.valueOf(rs.getString("roles"))
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public List<User> getUsersByRole(Role role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE roles = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getInt("uid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    Role.valueOf(rs.getString("roles"))
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public boolean checkUsernameExists(String userName) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User getUserByUserName(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("uid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    Role.valueOf(rs.getString("roles"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //UPDATE
    
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username=?, password=?, address=?, roles=? WHERE uid=?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getRole().name());
            stmt.setInt(5, user.getUID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateUserRole(int UID, Role newRole) {
        String sql = "UPDATE users SET roles = ? WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newRole.name());
            stmt.setInt(2, UID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //DELETE
    
    public boolean deleteUser(int UID) {
        String sql = "DELETE FROM users WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    
    public boolean deleteUserById(int UID) {
        String sql = "DELETE FROM users WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UID);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
