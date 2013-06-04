//ふぁぼ爆とその進捗表示

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

public class FavBombWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame favbomb;
	
	public FavBombWindow () {//コンストラクタ
		favbomb = new JFrame();
		favbomb.setIconImage(MeltonV7XMain.icon);
		JProgressBar favbombtime = new JProgressBar(0,127);
		JLabel label = new JLabel("<html>200個のふぁぼを贈っています...<br>※すでにふぁぼっているツイートはあんふぁぼされます</html>");
		favbomb.setResizable(false);
		favbomb.getContentPane().setLayout(new BorderLayout());
		favbomb.getContentPane().add(BorderLayout.CENTER,label);
		favbomb.getContentPane().add(BorderLayout.SOUTH,favbombtime);
		favbomb.setTitle("Melton  - ふぁぼ爆撃 -");
		favbomb.setVisible(true);
		favbomb.setAlwaysOnTop(true);
		favbomb.setSize(250,120);
		Paging pages2 = null;
    	pages2 = new Paging(1,200);
    	List<Status> statuses = null;
    	int i = 0;
		try {
			if ( MeltonV7XMain.tabnum == 0 ) {
				statuses = MeltonV7XMain.twitter.getUserTimeline(((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getStatus().getUser().getId(), pages2);
			} else if ( MeltonV7XMain.tabnum == 1 ) {
				statuses = MeltonV7XMain.twitter.getUserTimeline(((TimeLineData)MeltonV7XMain.list1.getSelectedValue()).getStatus().getUser().getId(), pages2);
			} else if ( MeltonV7XMain.tabnum == 2 ) {
				statuses = MeltonV7XMain.twitter.getUserTimeline(((TimeLineData)MeltonV7XMain.list2.getSelectedValue()).getStatus().getUser().getId(), pages2);
			}
			//200ツイート取得
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("050049");
		}
		if ( MeltonV7XMain.tabnum == 0 ) {
			MeltonV7XMain.list0.clearSelection();
		} else if ( MeltonV7XMain.tabnum == 1 ) {
			MeltonV7XMain.list1.clearSelection();
		} else if ( MeltonV7XMain.tabnum == 2 ) {
			MeltonV7XMain.list2.clearSelection();
		}
		for (Status status : statuses) {
			try {
				if ( !(status.isFavorited()) ){
					MeltonV7XMain.twitter.createFavorite(status.getId());
				} else {
					MeltonV7XMain.twitter.destroyFavorite(status.getId());
				}
			} catch (TwitterException e) {
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("050068");
			}
			favbombtime.setValue(i);
			i++;
		}
		favbomb.setVisible(false);
		favbomb.dispose();
	}
}