package SpaceMarket;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class Market implements AlienClub{

    private EntityFactory factory;
    private UserDAO userDAO;
    private ProductDAO productDAO;
    private ShoppingCartDAO shoppingCartDAO;
    private ShoppingCartItemDAO shoppingCartItemDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private ReviewDAO reviewDAO;

    public Market(EntityFactory factory) {
    	this.factory = factory;
        this.userDAO = new UserDAO();
        this.productDAO = new ProductDAO();
        this.shoppingCartDAO = new ShoppingCartDAO();
        this.shoppingCartItemDAO = new ShoppingCartItemDAO();
        this.orderDAO = new OrderDAO();
        this.orderItemDAO = new OrderItemDAO();
        this.reviewDAO = new ReviewDAO();
    }
    
    public void addUser(String userName, String password, String address, Role role) {
        User user = new User(userName, password, address, role);
        userDAO.insertUser(user);
        if (role == Role.BUYER) {
            int userId = userDAO.getUserByUserName(userName).getUID();
            shoppingCartDAO.createCart(userId);
        }
    }
    
    public void viewAllUsersByRole(Role role) {
        List<User> users = userDAO.getUsersByRole(role);
        users.sort(Comparator.comparing(User::getUserName));
        for (User user : users) {
            System.out.println(user.getUserName());
        }
    }
    
    public void viewAllSellersAndProducts() {
        List<User> sellers = userDAO.getUsersByRole(Role.SELLER);
        for (User seller : sellers) {
            System.out.println("Seller: " + seller.getUserName());
            List<Product> products = productDAO.getProductsByUserId(seller.getUID());
            for (Product product : products) {
                System.out.println("\t" + product);
            }
        }
    }
    
    public void displayAllProducts() {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("⚠️ No products found.");
            return;
        }
        System.out.println("\n🛒 Available Products:");
        for (Product product : products) {
            System.out.printf("ID: %d | Name: %s | Price: %.2f$ | Category: %s\n",
                    product.getPID(), product.getProductName(),
                    product.getProductPrice(), product.getCategory());
        }
    }
    
    public void viewCartItems(String buyerUsername) {
        User buyer = userDAO.getUserByUserName(buyerUsername);
        if (buyer == null || buyer.getRole() != Role.BUYER) {
            System.out.println("❌ Buyer not found.");
            return;
        }
        Integer cartId = shoppingCartDAO.getCartIdByUserId(buyer.getUID());
        if (cartId == null) {
            System.out.println("❌ No shopping cart found for this buyer.");
            return;
        }
        List<ShoppingCartItem> items = shoppingCartItemDAO.getItemsByCartId(cartId);
        if (items.isEmpty()) {
            System.out.println("🛒 Your cart is empty.");
            return;
        }
        System.out.println("🛒 Items in your cart:");
        for (ShoppingCartItem item : items) {
            Product product = productDAO.getProductById(item.getPID());
            System.out.printf("Product: %s | Quantity: %d | Unit Price: %.2f | Total: %.2f\n",
                    product.getProductName(),
                    item.getQuantity(),
                    product.getProductPrice(),
                    product.getProductPrice() * item.getQuantity());//getTotalprice on 122r
        }
    }
    
    public void addProductToSeller(String userName, String productName, double productPrice, 
    									Category category, boolean specialPackaging, int stock) {
        User seller = userDAO.getUserByUserName(userName);
        if (seller == null || seller.getRole() != Role.SELLER) {
            System.out.println("Invalid seller.");
            return;
        }
        Product product = EntityFactory.createProduct(productName, productPrice, category, specialPackaging, stock);
        productDAO.insertProductWithSeller(product, seller.getUID());
        System.out.println("✅ Product added successfully. Product ID is: " + product.getPID());
    }
    
    public void addProductToShoppingCart(String buyerUserName, int productId) {
        User buyer = userDAO.getUserByUserName(buyerUserName);
        if (buyer == null || buyer.getRole() != Role.BUYER) {
            System.out.println("Invalid buyer.");
            return;
        }
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        Integer cartId = shoppingCartDAO.getCartIdByUserId(buyer.getUID());
        if (cartId == null) {
            System.out.println("Shopping cart not found for buyer.");
            return;
        }
        shoppingCartItemDAO.addItemToCart(cartId, productId, 1); //each time add 1 product or more
        System.out.println("Product added to cart.");
    }
    
    public boolean isUsernameAvailable(String username) {
        return !userDAO.checkUsernameExists(username);
    }
    
    public double getTotalPriceForBuyer(String buyerUserName) {
        User buyer = userDAO.getUserByUserName(buyerUserName);
        if (buyer == null || buyer.getRole() != Role.BUYER) {
            System.out.println("Invalid buyer.");
            return 0;
        }
        Integer cartId = shoppingCartDAO.getCartIdByUserId(buyer.getUID());
        if (cartId == null)
        {
        	System.out.println("⚠️ Cart doesnt exist.");
        	return 0;
        }
        return shoppingCartItemDAO.getTotalPrice(cartId);
    }
    
    public void placeOrder(String buyerUsername) {
        User buyer = userDAO.getUserByUserName(buyerUsername);
        if (buyer == null || buyer.getRole() != Role.BUYER) {
            System.out.println("❌ Buyer not found or invalid role.");
            return;
        }
        Integer cartId = shoppingCartDAO.getCartIdByUserId(buyer.getUID());
        if (cartId == null) {
            System.out.println("❌ No shopping cart found.");
            return;
        }
        List<ShoppingCartItem> cartItems = shoppingCartItemDAO.getItemsByCartId(cartId);
        if (cartItems.isEmpty()) {
            System.out.println("🛒 Cart is empty. Cannot place order.");
            return;
        }
        for (ShoppingCartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getPID());
            if (product == null) {
                System.out.println("⚠️ Product with ID " + item.getPID() + " not found.");
                return;
            }
            if (product.getStock() < item.getQuantity()) {
                System.out.println("⚠️ Not enough stock for product: " + product.getProductName());
                return;
            }
        }
        double totalPrice = shoppingCartItemDAO.getTotalPrice(cartId);
        Date purchaseDate = new Date(System.currentTimeMillis());
        int orderId = orderDAO.createOrder(buyer.getUID(), totalPrice, purchaseDate);
        if (orderId == -1) {
            System.out.println("❌ Failed to create order.");
            return;
        }
        for (ShoppingCartItem item : cartItems) {
            Product product = productDAO.getProductById(item.getPID());
            double priceAtPurchase = product.getProductPrice();
            int updatedStock = product.getStock() - item.getQuantity();
            productDAO.updateProductStock(product.getPID(), updatedStock);
            orderItemDAO.insertOrderItem(orderId, item.getPID(), item.getQuantity(), priceAtPurchase);
        }
        shoppingCartItemDAO.clearCart(cartId);
        System.out.println("✅ Order placed successfully. Total: " + totalPrice + "$");
    }
    
    public void viewProductsByCategory(Category category) {
        List<Product> products = productDAO.getProductsByCategory(category);
        if (products.isEmpty()) {
            System.out.println("⚠️ No products found in category: " + category);
            return;
        }
        for (Product product : products) {
            System.out.println(product);
        }
    }
    
    public void viewAllAvailableCategories() {
        List<Category> categories = productDAO.getDistinctCategories();
        if (categories.isEmpty()) {
            System.out.println("⚠️ No categories found.");
            return;
        }
        for (Category cat : categories) {
            System.out.println(" - " + cat);
        }
    }
    
    public void viewOrdersForBuyer(String buyerUsername) {
        User buyer = userDAO.getUserByUserName(buyerUsername);
        if (buyer == null || buyer.getRole() != Role.BUYER) {
            System.out.println("❌ Buyer not found or invalid role.");
            return;
        }
        List<Order> orders = orderDAO.getOrdersByUserId(buyer.getUID());
        if (orders.isEmpty()) {
            System.out.println("❌ No orders found for this buyer.");
            return;
        }
        System.out.println("🧾 Order history for: " + buyerUsername);
        for (Order order : orders) {
            System.out.printf("- Order #%d | Date: %s | Total: %.2f$\n",
                    order.getOID(), order.getPurchaseDate(), order.getTotalAmount());
        }
    }
    
    public void restoreCartFromOrder(String buyerUsername, int orderId) {
        User buyer = userDAO.getUserByUserName(buyerUsername);
        if (buyer == null || buyer.getRole() != Role.BUYER) {
            System.out.println("❌ Invalid buyer.");
            return;
        }
        List<OrderItem> orderItems = orderItemDAO.getItemsByOrderId(orderId);
        if (orderItems == null || orderItems.isEmpty()) {
            System.out.println("⚠️ No items found in that order.");
            return;
        }
        Integer cartId = shoppingCartDAO.getCartIdByUserId(buyer.getUID());
        if (cartId == null) {
            System.out.println("⚠️ No shopping cart found.");
            return;
        }
        for (OrderItem item : orderItems) {
            shoppingCartItemDAO.addItemToCart(cartId, item.getPID(), item.getQuantity());
        }
        System.out.println("✅ Cart restored from previous order!");
    }
    
    public List<Order> getOrdersByBuyerUsername(String buyerUsername) {
        return orderDAO.getOrdersByBuyerUsername(buyerUsername);
    }
    
    public void leaveReview(String buyerUsername, int productId, double rating, String content) {
        User buyer = userDAO.getUserByUserName(buyerUsername);
        if (buyer == null || buyer.getRole() != Role.BUYER) {
            System.out.println("❌ Invalid buyer.");
            return;
        }
        Date reviewDate = new Date(System.currentTimeMillis());
        String title = "Rating: " + rating;
        reviewDAO.addReview(buyer.getUID(), productId, title, rating, content, reviewDate);
        System.out.println("✅ Review added.");
    }
    
    public void viewReviewsForProduct(int productId) {
        List<Review> reviews = reviewDAO.getReviewsByProductId(productId);
        if (reviews.isEmpty()) {
            System.out.println("📭 No reviews for this product.");
            return;
        }
        System.out.println("📝 Reviews for product ID: " + productId);
        for (Review review : reviews) {
            System.out.println("- " + review.getReviewTitle() + ": " + review.getReviewContent());
        }
    }
    
    public void promoteUserToAdmin(String username) {
        User user = userDAO.getUserByUserName(username);
        if (user == null) {
            System.out.println("❌ User not found.");
            return;
        }
        if (user.getRole() == Role.ADMIN) {
            System.out.println("⚠️ User is already an Admin.");
            return;
        }
        boolean success = userDAO.updateUserRole(user.getUID(), Role.ADMIN);
        if (success) {
            System.out.println("✅ User promoted to Admin.");
        } else {
            System.out.println("❌ Failed to promote user.");
        }
    }
    
    public void deleteUser(String username) {
        User user = userDAO.getUserByUserName(username);
        if (user == null) {
            System.out.println("❌ User not found.");
            return;
        }
        boolean success = userDAO.deleteUserById(user.getUID());
        if (success) {
            System.out.println("✅ User deleted successfully.");
        } else {
            System.out.println("❌ Failed to delete user.");
        }
    }
    
    public User getUserByUsername(String username) {
        return userDAO.getUserByUserName(username);
    }
    
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
    
    public boolean removeProductFromCart(String buyerUsername, int pid) {
        User buyer = userDAO.getUserByUserName(buyerUsername);
        if (buyer == null || buyer.getRole() != Role.BUYER)
        	return false;
        Integer cartId = shoppingCartDAO.getCartIdByUserId(buyer.getUID());
        if (cartId == null)
        	return false;
        return shoppingCartItemDAO.deleteItemFromCart(cartId, pid);
    }
    
    public List<Product> getProductsBySeller(String username) {
        User seller = userDAO.getUserByUserName(username);
        if (seller == null || seller.getRole() != Role.SELLER)
        	return List.of();
        return productDAO.getProductsByUserId(seller.getUID());
    }
    
    public boolean updateProductName(int productId, String newName) {
        return productDAO.updateProductName(productId, newName);
    }

    public boolean updateProductPrice(int productId, double newPrice) {
        return productDAO.updateProductPrice(productId, newPrice);
    }

    public boolean updateProductCategory(int productId, String category) {
        return productDAO.updateProductCategory(productId, category);
    }

    public boolean updateProductStock(int productId, int stock) {
        return productDAO.updateProductStock(productId, stock);
    }

    public boolean updateProductPackaging(int productId, boolean special) {
        return productDAO.updateProductPackaging(productId, special);
    }
    
    public void deleteUserAndDependencies(String username) {
        User user = userDAO.getUserByUserName(username);
        if (user == null) {
            System.out.println("❌ User not found.");
            return;
        }
        Role role = user.getRole();
        int uid = user.getUID();
        if (role == Role.SELLER) {
            productDAO.deleteProductsBySellerId(uid);
        } else if (role == Role.BUYER) {
            Integer cartId = shoppingCartDAO.getCartIdByUserId(uid);
            if (cartId != null) {
                shoppingCartItemDAO.deleteAllItemsFromCart(cartId);
                shoppingCartDAO.deleteCart(cartId);
            }
            List<Integer> orderIds = orderDAO.getOrderIdsByUserId(uid);
            for (int oid : orderIds) {
                orderItemDAO.deleteItemsByOrderId(oid);
            }
            orderDAO.deleteOrdersByUserId(uid);
        }
        reviewDAO.deleteReviewsByUserId(uid);
        boolean deleted = userDAO.deleteUserById(uid);
        if (deleted)
            System.out.println("✅ User deleted successfully.");
        else
            System.out.println("❌ Failed to delete user.");
    }
    

}
