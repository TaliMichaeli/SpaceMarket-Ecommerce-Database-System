package SpaceMarket;
import java.sql.*;
import java.util.*;

public class ProductDAO {
	
	//INSERT
	
    public int insertProduct(Product product) {
        String sql = "INSERT INTO product (productname, productprice, category, specialpackaging, stock) "
        			+ "VALUES (?, ?, ?, ?, ?) RETURNING pid";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getProductPrice());
            stmt.setString(3, product.getCategory().name());
            stmt.setBoolean(4, product.hasSpecialPackaging());
            stmt.setInt(5, product.getStock());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void insertProductWithSeller(Product product, int UID) {//UID that he is seller
        String sql = "INSERT INTO product (productname, productprice, category, specialpackaging, stock, uid) VALUES (?, ?, ?, ?, ?, ?) RETURNING pid";
        try (Connection conn = DataBaseManager.getConnection();
        	     PreparedStatement stmt = conn.prepareStatement(sql)) {
        	    stmt.setString(1, product.getProductName());
        	    stmt.setDouble(2, product.getProductPrice());
        	    stmt.setString(3, product.getCategory().name());
        	    stmt.setBoolean(4, product.hasSpecialPackaging());
        	    stmt.setInt(5, product.getStock());
        	    stmt.setInt(6, UID);
        	    ResultSet rs = stmt.executeQuery();
        	    if (rs.next()) {
        	        int generatedPid = rs.getInt(1);
        	        product.setPID(generatedPid);
        	    }
        	} catch (SQLException e) {
        	    e.printStackTrace();
        	}
    }
    
    //SELECT
    
    public Product getProductById(int pid) {
        String sql = "SELECT * FROM product WHERE pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("pid"),
                        rs.getString("productname"),
                        rs.getDouble("productprice"),
                        Category.valueOf(rs.getString("category")),
                        rs.getBoolean("specialpackaging"),
                        rs.getInt("stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE stock > 0";
        try (Connection conn = DataBaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("pid"),
                        rs.getString("productname"),
                        rs.getDouble("productprice"),
                        Category.valueOf(rs.getString("category")),
                        rs.getBoolean("specialpackaging"),
                        rs.getInt("stock")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<Product> getProductsByUserId(int UID) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE uid = ? AND stock > 0";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                	rs.getInt("pid"),
                    rs.getString("productname"),
                    rs.getDouble("productprice"),
                    Category.valueOf(rs.getString("category")),
                    rs.getBoolean("specialpackaging"),
                    rs.getInt("stock")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public void viewAllCategories() {
        String sql = "SELECT DISTINCT category FROM product";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("All Categories:");
            while (rs.next()) {
                String category = rs.getString("category");
                System.out.println("\t" + category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Product> getProductsByCategory(Category category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE category = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("pid"),
                    rs.getString("productname"),
                    rs.getDouble("productprice"),
                    Category.valueOf(rs.getString("category")),
                    rs.getBoolean("specialpackaging"),
                    rs.getInt("stock")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<Category> getDistinctCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM product";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String categoryStr = rs.getString("category");
                try {
                    categories.add(Category.valueOf(categoryStr));
                } catch (IllegalArgumentException e) {
                    System.out.println("⚠️ Unknown category found in DB: " + categoryStr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    
    //UPDATE
	
    public boolean updateProduct(Product product) {
        String sql = "UPDATE product SET productname=?, productprice=?, category=?, specialpackaging=?, "
        																		+ "stock=? WHERE pid=?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getProductPrice());
            stmt.setString(3, product.getCategory().name());
            stmt.setBoolean(4, product.hasSpecialPackaging());
            stmt.setInt(5, product.getStock());
            stmt.setInt(6, product.getPID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateProductName(int productId, String newName) {
        String sql = "UPDATE product SET productname = ? WHERE pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductPrice(int productId, double newPrice) {
        String sql = "UPDATE product SET productprice = ? WHERE pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductCategory(int productId, String category) {
        String sql = "UPDATE product SET category = ? WHERE pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductStock(int productId, int newStock) {
        String sql = "UPDATE product SET stock = ? WHERE pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newStock);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductPackaging(int productId, boolean special) {
        String sql = "UPDATE product SET specialpackaging = ? WHERE pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, special);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //DELETE
    
    public boolean deleteProduct(int pid) {
        String sql = "DELETE FROM product WHERE pid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void deleteProductsBySellerId(int uid) {
        String sql = "DELETE FROM product WHERE uid = ?";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, uid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
