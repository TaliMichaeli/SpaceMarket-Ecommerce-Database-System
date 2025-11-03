package SpaceMarket;

import java.util.List;

public class Admin {
	
	private final UserDAO userDAO = new UserDAO();
	
    public boolean deleteUser(int UID) {
        return userDAO.deleteUser(UID);
    }

    public List<User> listAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean promoteToAdmin(int UID) {
        return userDAO.updateUserRole(UID, Role.ADMIN);
    }

    public List<User> getAllSellers() {
        return userDAO.getUsersByRole(Role.SELLER);
    }

    public List<User> getAllBuyers() {
        return userDAO.getUsersByRole(Role.BUYER);
    }

}
