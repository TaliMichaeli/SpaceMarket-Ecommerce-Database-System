package SpaceMarket;

public class OrderItem {
	
	private int OrderItemID;
	private int OID;
	private int PID;
	private int quantity;
	private double priceAtPurchase;
	
    public OrderItem(int OID, int PID, int quantity, double priceAtPurchase) {
        this.OID = OID;
        this.PID = PID;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public OrderItem(int OrderItemID, int OID, int PID, int quantity, double priceAtPurchase) {
        this.OrderItemID = OrderItemID;
        this.OID = OID;
        this.PID = PID;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

	public int getOrderItemID() {
		return OrderItemID;
	}

	public void setOrderItemID(int orderItemID) {
		OrderItemID = orderItemID;
	}

	public int getOID() {
		return OID;
	}

	public void setOID(int oID) {
		OID = oID;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPriceAtPurchase() {
		return priceAtPurchase;
	}

	public void setPriceAtPurchase(double priceAtPurchase) {
		this.priceAtPurchase = priceAtPurchase;
	}

	@Override
	public String toString() {
		return "OrderItem [OrderItemID=" + OrderItemID + ", OID=" + OID + ", PID=" + PID + ", quantity=" + quantity
				+ ", priceAtPurchase=" + priceAtPurchase + "]";
	}

}
