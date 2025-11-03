package SpaceMarket;

import java.util.Date;

public class Order {

	private int OID;
	private int UID;
	private double totalAmount;
	private Date purchaseDate;

	public Order(int UID, double totalAmount, Date purchaseDate) {
		this.UID = UID;
		this.totalAmount = totalAmount;
		this.purchaseDate = purchaseDate;
	}

	public Order(int OID, int UID, double totalAmount, Date purchaseDate) {
		this.OID = OID;
		this.UID = UID;
		this.totalAmount = totalAmount;
		this.purchaseDate = purchaseDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public int getOID() {
		return OID;
	}

	public void setOID(int oID) {
		OID = oID;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "Order {OID=" + OID + ", UID=" + UID + ", totalAmount=" + totalAmount + ", purchaseDate=" + purchaseDate
				+ "}";
	}
	 
}
