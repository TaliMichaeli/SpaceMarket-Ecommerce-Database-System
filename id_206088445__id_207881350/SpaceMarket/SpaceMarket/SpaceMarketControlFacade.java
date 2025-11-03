package SpaceMarket;

import java.util.List;
import java.util.Scanner;

public class SpaceMarketControlFacade {
    private static SpaceMarketControlFacade instance;
	private final Market market;
	private final Scanner scanner;
	
	private SpaceMarketControlFacade(Market market) {
		this.market = market;
        this.scanner = new Scanner(System.in);
	}
	
 
	//it makes SpaceMarketControlFacade be only one time -> Singleton
    public static SpaceMarketControlFacade getInstance(Market market) {  
        if (instance == null) {
            instance = new SpaceMarketControlFacade(market);
        }
        return instance;
    }
    
	public void start() {
		int choice;
		do { 
			displayMenu();
			choice = getUserChoice();
			handleUserChoice(choice);
		} while (choice != 0);
	}
	
	//Main Manu
	private void displayMenu() {
        System.out.println("\n🚀Welcome to our Ecommerce! The Only Store In The Galaxy!🚀");
        System.out.print("   Please choose one of the options below: \n");
        System.out.println("  1. Register New User");
        System.out.println("  2. View All Users By Role (Buyer/Seller/Admin)");
        System.out.println("  3. Add Product To Seller");
        System.out.println("  4. Seller Product Management");
        System.out.println("  5. View All Sellers And Their Products");
        System.out.println("  6. View All Products By Category");
        System.out.println("  7. View All Available Categories");
        System.out.println("  8. Add Product To Buyer's Cart");
        System.out.println("  9. Remove Product from Buyer's Cart");
        System.out.println("  10. View Buyer's Cart Items");
        System.out.println("  11. Calculate Total Price For Buyer Cart");
        System.out.println(" 12. Complete Purchase");
        System.out.println(" 13. View Order History For Buyer");
        System.out.println(" 14. Restore Cart From Order History");
        System.out.println(" 15. Leave A Review For Product");
        System.out.println(" 16. View Reviews For A Product");
        System.out.println(" 17. Promote User To Admin");
        System.out.println(" 18. Admin User Management");
        System.out.println("  0. Exit");
	}
	
	//Role Menu
	private Role chooseRoleFromMenu() {
	    Role[] roles = Role.values();
	    System.out.println("Choose a role:");
	    for (int i = 0; i < roles.length; i++) {
	        System.out.println("  " + (i + 1) + ". " + roles[i]);
	    }
	    System.out.print("Enter role number: ");
	    try {
	        int choice = Integer.parseInt(scanner.nextLine());
	        if (choice < 1 || choice > roles.length) {
	            System.out.println("❌ Invalid choice.");
	            return null;
	        }
	        return roles[choice - 1];
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Please enter a number.");
	        return null;
	    }
	}
	
	//Category Menu
	private Category chooseCategoryFromMenu() {
	    Category[] categories = Category.values();
	    System.out.println("Choose a category:");
	    for (int i = 0; i < categories.length; i++) {
	        System.out.println("  " + (i + 1) + ". " + categories[i]);
	    }
	    System.out.print("Enter category number: ");
	    try {
	        int choice = Integer.parseInt(scanner.nextLine());
	        if (choice < 1 || choice > categories.length) {
	            System.out.println("❌ Invalid choice.");
	            return null;
	        }
	        return categories[choice - 1];
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Please enter a valid number.");
	        return null;
	    }
	}
	
	//Menu for update product
	private int getProductUpdateChoice() {
	    System.out.println("""
	        ✏️ Choose what you want to update:
	        1. Product Name
	        2. Product Price
	        3. Product Category
	        4. Stock Quantity
	        5. Special Packaging""");
	    while (true) {
	        System.out.print("Enter choice number: ");
	        try {
	            int choice = Integer.parseInt(scanner.nextLine());
	            if (choice >= 1 && choice <= 5) {
	                return choice;
	            } else
	                System.out.println("❌ Invalid choice. Please enter a number between 1 and 5.");
	        } catch (NumberFormatException e) {
	            System.out.println("❌ Invalid input. Please enter a number.");
	        }
	    }
	}
	
	private int getUserChoice() {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline
                return choice;
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next();
            }
        }
    }

	private void handleUserChoice(int choice) {
		 switch (choice) {
         case 1 -> handleAddUser();
         case 2 -> UsersByRoleMenu();
         case 3 -> handleAddProductToSeller();
         case 4 -> handleUpdateProduct();
         case 5 -> SellersAndProducts();
         case 6 -> handleViewProductsByCategory();
         case 7 -> handleViewAllCategories();
         case 8 -> handleAddProductToBuyerCart();
         case 9 -> handleRemoveProductFromCart();
         case 10 -> handleViewCartItems();
         case 11 -> handleCalculateTotalPrice();
         case 12 -> handlePurchase();
         case 13 -> handleViewOrderHistory();
         case 14 -> handleRestoreCartFromOrderHistory();
         case 15 -> handleLeaveReviewForProduct();
         case 16 -> handleViewReviewsForProduct();
         case 17 -> handlePromoteUserToAdmin();
         case 18 -> handleDeleteUserByAdmin();
         case 0 -> System.out.println("Exiting... 🚀");
         default -> System.out.println("Invalid option, try again.");
     } 
	}
	
	//1
	private void handleAddUser() {
	    Role selectedRole = chooseRoleFromMenu();
	    if (selectedRole == null) return;
	    System.out.print("Enter Username: ");
	    String userName = scanner.nextLine();
	    if (!market.isUsernameAvailable(userName)) {
	        System.out.println("❌ Username already taken. Try another one.");
	        return;
	    }
	    System.out.print("Enter Password: ");
	    String password = scanner.nextLine();
	    System.out.print("Enter Address: ");
	    String address = scanner.nextLine();
	    market.addUser(userName, password, address, selectedRole);
	    System.out.println("\n✅ " + selectedRole + " added successfully!");
	}
	
	//2
	private void UsersByRoleMenu(){
	    Role selectedRole = chooseRoleFromMenu();
	    if (selectedRole == null) return;
	    System.out.println("\n🔎 Users with role: " + selectedRole);
	    market.viewAllUsersByRole(selectedRole);
	}
	
	//3
	private void handleAddProductToSeller() {
	    System.out.print("Enter Seller's Username: ");
	    String sellerUsername = scanner.nextLine();
	    System.out.print("Enter Product Name: ");
	    String productName = scanner.nextLine();
	    System.out.print("Enter Product Price: ");
	    double price;
	    try {
	        price = Double.parseDouble(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Invalid price.");
	        return;
	    }
	    Category category = chooseCategoryFromMenu();
	    if (category == null) return;
	    System.out.print("Special Packaging? (true/false): ");
	    boolean specialPackaging = Boolean.parseBoolean(scanner.nextLine());
	    System.out.print("Enter Stock Quantity: ");
	    int stock;
	    try {
	        stock = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Invalid stock number.");
	        return;
	    }
	    market.addProductToSeller(sellerUsername, productName, price, category, specialPackaging, stock);
	}
	
	//4
	private void handleUpdateProduct() {
	    System.out.print("Enter your username: ");
	    String username = scanner.nextLine();
	    User user = market.getUserByUsername(username);
	    if (user == null || user.getRole() != Role.SELLER) {
	        System.out.println("❌ Only sellers can update products.");
	        return;
	    }
	    List<Product> products = market.getProductsBySeller(username);
	    if (products.isEmpty()) {
	        System.out.println("❌ You have no products to update.");
	        return;
	    }
	    displaySellerProducts(products);
	    System.out.print("\nEnter the Product ID to update: ");
	    int productId = Integer.parseInt(scanner.nextLine());
	    int choice = getProductUpdateChoice();
	    updateProductByChoice(choice, productId, username);
	}
	
	private void displaySellerProducts(List<Product> products) {
	    System.out.println("\n🛒 Your Products:");
	    for (Product product : products) {
	        System.out.printf("PID: %d | Name: %s | Price: %.2f | Category: %s | Stock: %d | Special Packaging: %s%n",
	                product.getPID(), product.getProductName(), product.getProductPrice(),
	                product.getCategory(), product.getStock(), product.hasSpecialPackaging());
	    }
	}
	
	private void updateProductByChoice(int choice, int productId, String username) {
	    boolean success = false;
	    switch (choice) {
	        case 1 -> {
	            System.out.print("Enter new name: ");
	            String name = scanner.nextLine();
	            success = market.updateProductName(productId, name);
	        }
	        case 2 -> {
	            System.out.print("Enter new price: ");
	            double price = Double.parseDouble(scanner.nextLine());
	            success = market.updateProductPrice(productId, price);
	        }
	        case 3 -> {
	            System.out.print("Enter new category: ");
	            String category = scanner.nextLine();
	            success = market.updateProductCategory(productId, category);
	        }
	        case 4 -> {
	            System.out.print("Enter new stock quantity: ");
	            int stock = Integer.parseInt(scanner.nextLine());
	            success = market.updateProductStock(productId, stock);
	        }
	        case 5 -> {
	            System.out.print("Special packaging (true/false): ");
	            boolean special = Boolean.parseBoolean(scanner.nextLine());
	            success = market.updateProductPackaging(productId, special);
	        }
	        default -> System.out.println("❌ Invalid choice.");
	    }
	    if (success)
	        System.out.println("✅ Product updated successfully.");
	    else
	        System.out.println("❌ Failed to update product.");
	}
	
	//5
	private void SellersAndProducts() {
	    System.out.println("\n📦 Sellers and their Products:");
	    market.viewAllSellersAndProducts();
	}
	
	//6
	private void handleViewProductsByCategory() {
	    Category category = chooseCategoryFromMenu();
	    if (category == null) return;
	    System.out.println("\n🛒 Products in category: " + category);
	    market.viewProductsByCategory(category);
	}
	
	//7
	private void handleViewAllCategories() {
	    System.out.println("\n📚 Available Categories:");
	    market.viewAllAvailableCategories();
	}
	
	//8
	private void handleAddProductToBuyerCart() {
	    System.out.print("Enter Buyer's Username: ");
	    String buyerUsername = scanner.nextLine();
	    market.displayAllProducts();
	    System.out.print("\nEnter Product ID to Add: ");
	    int productId;
	    try {
	        productId = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Invalid product ID.");
	        return;
	    }
	    market.addProductToShoppingCart(buyerUsername, productId);
	}
	
	//9
	private void handleRemoveProductFromCart() {
	    System.out.print("Enter Buyer Username: ");
	    String username = scanner.nextLine();
	    System.out.print("Enter Product ID to remove: ");
	    int productId;
	    try {
	        productId = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Invalid product ID.");
	        return;
	    }
	    boolean success = market.removeProductFromCart(username, productId);
	    if (success)
	        System.out.println("✅ Product removed from cart.");
	    else
	        System.out.println("❌ Failed to remove product.");
	}
	
	//10
	private void handleViewCartItems() {
	    System.out.print("Enter Buyer's Username: ");
	    String buyerUsername = scanner.nextLine();
	    market.viewCartItems(buyerUsername);
	}
	
	//11
	private void handleCalculateTotalPrice() {
	    System.out.print("Enter Buyer's Username: ");
	    String buyerUsername = scanner.nextLine();
	    double total = market.getTotalPriceForBuyer(buyerUsername);
	    System.out.printf("🧾 Total price for cart: %.2f₪\n", total);
	}
	
	//12
	private void handlePurchase() {
	    System.out.print("Enter Buyer's Username: ");
	    String buyerUsername = scanner.nextLine();
	    market.placeOrder(buyerUsername);
	}
	
	//13
	private void handleViewOrderHistory() {
	    System.out.print("Enter Buyer's Username: ");
	    String buyerUsername = scanner.nextLine();
	    market.viewOrdersForBuyer(buyerUsername);
	}
	
	//14
	private void handleRestoreCartFromOrderHistory() {
	    System.out.print("Enter Buyer Username: ");
	    String buyerUsername = scanner.nextLine();
	    List<Order> orders = market.getOrdersByBuyerUsername(buyerUsername);
	    if (orders == null || orders.isEmpty()) {
	        System.out.println("❌ No orders found for this user.");
	        return;
	    }
	    System.out.println("📦 Previous Orders:");
	    for (Order order : orders) {
	        System.out.printf("Order #%d | Date: %s | Total: %.2f₪\n",
	                order.getOID(), order.getPurchaseDate(), order.getTotalAmount());
	    }
	    System.out.print("Enter Order ID to restore to cart: ");
	    int selectedOID = Integer.parseInt(scanner.nextLine());
	    market.restoreCartFromOrder(buyerUsername, selectedOID);
	}
	
	//15
	private void handleLeaveReviewForProduct() {
	    System.out.print("Enter your username: ");
	    String username = scanner.nextLine();
	    market.displayAllProducts();
	    System.out.print("Enter Product ID: ");
	    int productId;
	    try {
	        productId = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Invalid product ID.");
	        return;
	    }
	    System.out.print("Enter rating (1–5): ");
	    int rating;
	    try {
	        rating = Integer.parseInt(scanner.nextLine());
	        if (rating < 1 || rating > 5) {
	            System.out.println("❌ Rating must be between 1 and 5.");
	            return;
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Invalid rating input.");
	        return;
	    }
	    System.out.print("Enter review content: ");
	    String content = scanner.nextLine();
	    market.leaveReview(username, productId, rating, content);
	}
	
	//16
	private void handleViewReviewsForProduct() {
		market.displayAllProducts();
	    System.out.print("Enter Product ID: ");
	    int productId;
	    try {
	        productId = Integer.parseInt(scanner.nextLine());
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Invalid product ID.");
	        return;
	    }
	    market.viewReviewsForProduct(productId);
	}
	
	//17
	private void handlePromoteUserToAdmin() {
	    System.out.print("Enter the username to promote to Admin: ");
	    String username = scanner.nextLine();
	    market.promoteUserToAdmin(username);
	}
	
	//18
	private void handleDeleteUserByAdmin() {
	    System.out.print("Enter your admin username: ");
	    String adminUsername = scanner.nextLine();
	    User admin = market.getUserByUsername(adminUsername);
	    if (admin == null || admin.getRole() != Role.ADMIN) {
	        System.out.println("❌ Only admins can delete users.");
	        return;
	    }
	    System.out.print("Enter the username of the user to delete: ");
	    String targetUsername = scanner.nextLine();
	    market.deleteUserAndDependencies(targetUsername);
	}

	
}
