package SpaceMarket;

import java.util.Date;

public class EntityFactory {
	
	public static Product createProduct(String productName, double productPrice, Category category, 
														boolean specialPackaging, int stock) {
        return new Product(productName, productPrice, category, specialPackaging, stock);
    }
	
	public static ShoppingCart createShoppingCart(int userId) {
        return new ShoppingCart(userId);
    }
	
	public static Order createOrder(int userId, double totalPrice, Date purchaseDate) {
        return new Order(userId, totalPrice, purchaseDate);
    }
	
	public static Review createReview(int userId, int productId, String reviewTitle,
											int rating, String reviewContent, Date reviewDate) {
        return new Review(userId, productId, reviewTitle, rating, reviewContent, reviewDate);
    }
	
	public static OrderItem createOrderItem(int orderId, int productId, int quantity, 
																	double priceAtPurchase) {
        return new OrderItem(orderId, productId, quantity, priceAtPurchase);
    }
	
	public static ShoppingCartItem createShoppingCartItem(int cartId, int productId, int quantity) {
        return new ShoppingCartItem(cartId, productId, quantity);
    }
	
	
	
	
	
	
}
