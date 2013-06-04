import twitter4j.Status;

//リプライ通知のためのスレッド

public class InfomationNotifier extends Thread {
	private Status status;
	private int type;
	
	public InfomationNotifier( Status status, int type ) {
		this.status = status;
		this.type = type;
	}
	
	public void run () {
		@SuppressWarnings("unused")
		InfomationNotice notice = new InfomationNotice(status, type);
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}