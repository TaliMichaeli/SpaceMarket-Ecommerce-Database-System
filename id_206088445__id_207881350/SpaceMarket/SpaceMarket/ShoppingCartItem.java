package SpaceMarket;

public class ShoppingCartItem {
	
	private int CartItemID;
	private int CID;
	private int PID;
	private int quantity;

	public ShoppingCartItem(int CID, int PID, int quantity) {
        this.CID = CID;
        this.PID = PID;
        this.quantity = quantity;
    }
	
	public ShoppingCartItem(int CartItemID, int CID, int PID, int quantity) {
		this.CartItemID = CartItemID;
        this.CID = CID;
        this.PID = PID;
        this.quantity = quantity;
    }

	public int getCartItemID() {
		return CartItemID;
	}

	public void setCartItemID(int cartItemID) {
		CartItemID = cartItemID;
	}

	public int getCID() {
		return CID;
	}

	public void setCID(int cID) {
		CID = cID;
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

	@Override
	public String toString() {
		return "ShoppingCartItem [CartItemID=" + CartItemID + ", CID=" + CID + ", PID=" + PID + ", quantity=" + quantity
				+ "]";
	}

}
