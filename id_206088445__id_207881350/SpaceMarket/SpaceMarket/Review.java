package SpaceMarket;
import java.util.Date;

public class Review{
	
	private int RID;
	private int UID;
	private int PID;
	private String reviewTitle;
	private double rating;
	private String reviewContent;
	private Date reviewDate;
	
	
	public Review(int UID, int PID, String reviewTitle, double rating, String reviewContent, 
																Date reviewDate){
		this.UID = UID;
		this.PID = PID;
		this.reviewTitle = reviewTitle;
		this.rating = rating;
		this.reviewContent = reviewContent;
		this.reviewDate = reviewDate;
	}
	
	public Review(int RID, int UID, int PID, String reviewTitle, double rating, String reviewContent, 
																						Date reviewDate){
		this.RID = RID;
		this.UID = UID;
		this.PID = PID;
		this.reviewTitle = reviewTitle;
		this.rating = rating;
		this.reviewContent = reviewContent;
		this.reviewDate = reviewDate;
	}

	public int getRID() {
		return RID;
	}

	public void setRID(int rID) {
		RID = rID;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	@Override
	public String toString() {
		return "Review [RID=" + RID + ", UID=" + UID + ", PID=" + PID + ", reviewTitle=" + reviewTitle + ", rating="
				+ rating + ", reviewContent=" + reviewContent + ", reviewDate=" + reviewDate + "]";
	}	

}
