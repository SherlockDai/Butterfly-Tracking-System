package nixyteam;
import java.io.Serializable;

public class FeedbackBean implements Serializable {
	private static final long serialVersionUID = -6983755291840805971L;
	private int feedbackID;
	private String dateTime;
	private String userName;
	private String title;
	private String comment;
	private int rating;
	public int getFeedbackID() {
		return feedbackID;
	}
	public String getDateTime() {
		return dateTime;
	}
	public String getUserName() {
		return userName;
	}
	public String getTitle() {
		return title;
	}
	public String getComment() {
		return comment;
	}
	public int getRating() {
		return rating;
	}
	public void setFeedbackID(int feedbackID) {
		this.feedbackID = feedbackID;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
}