package SpaceMarket;

public class Product {
	
	private int PID;
    private String productName;
    private double productPrice;
    private Category category;
    private boolean specialPackaging;
    private int stock;

    public Product(String name, double price, Category category, boolean specialPackaging, int stock) {
        this.productName = name;
        this.productPrice = price;
        this.category = category;
        this.specialPackaging = specialPackaging;
        this.stock = stock;
    }
    
    public Product(int PID, String name, double price, Category category, boolean specialPackaging, int stock) {
        this.PID = PID;
    	this.productName = name;
        this.productPrice = price;
        this.category = category;
        this.specialPackaging = specialPackaging;
        this.stock = stock;
    }
    
    public int getPID() {
    	return PID;
    }
    
    public void setPID(int pid) {
        this.PID = pid;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public Category getCategory() {
        return category;
    }

    public boolean hasSpecialPackaging() {
        return specialPackaging;
    }
    
    public int getStock() {
    	return stock;
    }
    
    public void setStock(int stock) {
    	this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("Product{name: '%s', price: %.2f, category: %s, special Packaging: %s, stock: %d}", 
            productName, productPrice, category, specialPackaging, stock);
    }
}
