//タイムラインのデータを格納するためのクラス

import java.util.Date;
import twitter4j.Status;

public class TimeLineData{
	private static int nextTweetId = 0;
	private String ScreenName;
	private long StatusID;
	private int TweetID;
	private String TweetText;
	private String UserName;
	private Date date;
	private Status status;
	private String ImageURL;
	private String cliantname;
	
	public TimeLineData(Status st, String name, long sId, String t, String Uname, Date d, String u,String c){
		//コンストラクタ
		setStatus(st);
		TweetID = nextTweetId;
		ScreenName = name;
		StatusID = sId;
		TweetText = t;
		UserName = Uname;
		date = d;
		ImageURL = u;
		cliantname = c;
		nextTweetId++;
	}

	public String getScreenName() {
		return ScreenName;
	}

	public void setScreenName(String screenName) {
		ScreenName = screenName;
	}

	public long getStatusID() {
		return StatusID;
	}

	public void setStatusID(long statusID) {
		StatusID = statusID;
	}

	public int getTweetID() {
		return TweetID;
	}

	public void setTweetID(int tweetID) {
		TweetID = tweetID;
	}

	public String getTweetText() {
		return TweetText;
	}

	public void setTweetText(String tweetText) {
		TweetText = tweetText;
	}

	public static int getNextTweetId() {
		return nextTweetId;
	}

	public static void setNextTweetId(int nextTweetId) {
		TimeLineData.nextTweetId = nextTweetId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getImageURL() {
		return ImageURL;
	}

	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}

	public String getCliantname() {
		return cliantname;
	}

	public void setCliantname(String cliantname) {
		this.cliantname = cliantname;
	}
}