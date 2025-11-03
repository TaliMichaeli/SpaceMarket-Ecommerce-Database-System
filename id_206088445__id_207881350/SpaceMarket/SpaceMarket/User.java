package SpaceMarket;

public class User {
	
	private int UID;
    private String userName;
    private String password;
    private String address;
    private Role role;
    
    public User(String userName, String password, String address, Role role) {
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.role = role;
    }
    
    public User(int UID, String userName, String password, String address, Role role) {
    	this.UID = UID;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.role = role;
    }
    
    public int getUID(){
    	return UID;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String setUserName(String userName) {
        return userName;
    }
    
    public String getPassword() {
    	return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public String toString() {
        return "userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
    
}
