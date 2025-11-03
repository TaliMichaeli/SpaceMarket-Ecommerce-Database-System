package SpaceMarket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
	
	//INSERT
	
    public void addReview(int UID, int PID, String title, double rating, String content, java.util.Date reviewDate) {
        String sql = "INSERT INTO review (uid, pid, reviewtitle, rating, reviewcontent, reviewdate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UID);
            stmt.setInt(2, PID);
            stmt.setString(3, title);
            stmt.setDouble(4, rating);
            stmt.setString(5, content);
            stmt.setDate(6, new java.sql.Date(reviewDate.getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //SELECT

    public List<Review> getReviewsByProductId(int productId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE pid = ? ORDER BY reviewdate DESC";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("rid"),
                        rs.getInt("uid"),
                        rs.getInt("pid"),
                        rs.getString("reviewtitle"),
                        rs.getDouble("rating"),
                        rs.getString("reviewcontent"),
                        rs.getDate("reviewdate")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    
    //DELETE
    
    public void deleteReviewsByUserId(int uid) {
        String sql = "DELETE FROM review WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, uid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
