package SpaceMarket;

public class ShoppingCart {
	
	private int CID;
	private int UID;
	
    public ShoppingCart(int CID) {
    	this.CID = CID;
    }
    
    public ShoppingCart(int CID, int UID) {
    	this.CID = CID;
    	this.UID = UID;
    }

	public int getCID() {
		return CID;
	}

	public void setCID(int cID) {
		CID = cID;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	@Override
	public String toString() {
		return "ShoppingCart {CID=" + CID + ", UID=" + UID + "}";
	}

	
}
