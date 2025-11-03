package SpaceMarket;
import java.sql.*;

public class DataBaseManager {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/SpaceMarket";
	private static final String USER = "postgres";
	private static final String PASS = "tali1995";
	Connection conn = null;
	
    static { //create the drive one time
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public static Connection getConnection(){
	     try {
	            return DriverManager.getConnection(URL, USER, PASS);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
    }

}