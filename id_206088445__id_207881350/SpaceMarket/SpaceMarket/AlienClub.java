package SpaceMarket;

public interface AlienClub {
	
	void addUser(String userName, String password, String address, Role role);
    
    void addProductToSeller(String sellerUsername, String productName, double productPrice,
            Category category, boolean specialPackaging, int stock);
    
    void addProductToShoppingCart(String buyerUserName, int productId);
    
    void placeOrder(String buyerUsername);
    
    void viewAllSellersAndProducts();
    
    void viewCartItems(String buyerUsername);
    
    public void leaveReview(String buyerUsername, int productId, double rating, String content);
}
