import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TalkView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame talkview;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model = new DefaultListModel();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JList list = new JList(model);
	String[] cliantname = new String[2];
	private TimeLineData spdata;
	JButton openbutton = new JButton("ひらく");
	JButton clearbutton = new JButton("クリア");
    JButton linkbutton = new JButton("リンク");
    JButton replybutton = new JButton("リプライ");
    JButton retweetbutton = new JButton("リツイート");
    JButton ufretweetbutton = new JButton("非公式RT");
    JButton favorite = new JButton("ふぁぼ");
    JButton copybutton = new JButton("パクる");
    JPanel panel = new JPanel();
	
	@SuppressWarnings("unchecked")
	public TalkView ( Status status ) {
		talkview = new JFrame();
		talkview.setIconImage(MeltonV7XMain.icon);
		talkview.setBounds(500,300,650,400);
		talkview.setTitle("Melton  - 会話表示 -");
		talkview.setAlwaysOnTop(true);
		talkview.setVisible(true);
		openbutton.addActionListener(new OpenButtonActionAdapter());
		replybutton.addActionListener(new ReplyButtonActionAdapter());
		retweetbutton.addActionListener(new RetweetButtonActionAdapter());
		ufretweetbutton.addActionListener(new UfretweetButtonActionAdapter());
		favorite.addActionListener(new FavoriteButtonActionAdapter());
		copybutton.addActionListener(new CopyButtonActionAdapter());
		clearbutton.addActionListener(new ClearButtonActionAdapter());
		linkbutton.addActionListener(new LinkButtonActionAdapter());
		cliantname = status.getSource().split(">");
		if ( !(cliantname[0].equals("web")) ) {
			cliantname = cliantname[1].split("<");
		}
		list.setCellRenderer(new TextAreaRenderer());
		list.setFixedCellHeight(-1);
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scroll = new JScrollPane(list);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		spdata  = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
		model.addElement(spdata);
		talkview.getContentPane().add(BorderLayout.CENTER,scroll);
		panel.add(replybutton);
		panel.add(retweetbutton);
		panel.add(ufretweetbutton);
		panel.add(favorite);
		panel.add(copybutton);
		panel.add(clearbutton);
		panel.add(openbutton);
		panel.add(linkbutton);
		talkview.getContentPane().add(BorderLayout.SOUTH,panel);
		TalkSearch(status,30);
	}
	
	@SuppressWarnings("unchecked")
	private void TalkSearch ( Status status, int pagenum ) {
		String user_name;
		long search_id;
		Paging pages = new Paging(1,pagenum);
		user_name = status.getInReplyToScreenName();
		search_id = status.getInReplyToStatusId();
		if ( search_id == 0 || user_name == null ) {
			return;
		}
		try {
    		Thread.sleep(10);
    	} catch ( InterruptedException e ) {
			System.out.println(e);
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0E0089");
		}
		List<Status> statuses = null;
		try {
			statuses = MeltonV7XMain.twitter.getUserTimeline(user_name,pages);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0E098");
		}
		for (Status status2 : statuses) {
			if (status2.getId() == search_id ) {
				cliantname = status2.getSource().split(">");
				if ( !(cliantname[0].equals("web")) ) {
					cliantname = cliantname[1].split("<");
				}
				spdata  = new TimeLineData(status2, status2.getUser().getScreenName(), status2.getId(), status2.getText(), status2.getUser().getName(), status2.getCreatedAt(), status2.getUser().getProfileImageURL(),cliantname[0]);
				model.addElement(spdata);
				pagenum += 30;
				TalkSearch(status2, pagenum);
			}
		}
	}
	
	class OpenButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			//ツイートオープン
			try {
				@SuppressWarnings("unused")
				OpenedWindow openedwindow = new OpenedWindow("" + ((TimeLineData)list.getSelectedValue()).getScreenName() + " -" + ((TimeLineData)list.getSelectedValue()).getUserName() + "-\n" + ((TimeLineData)list.getSelectedValue()).getTweetText() + "\n" + ((TimeLineData)list.getSelectedValue()).getDate() + "\nvia " + ((TimeLineData)list.getSelectedValue()).getCliantname(), ((TimeLineData)list.getSelectedValue()).getStatus());
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("0E0124");
			}
			list.clearSelection();
		}
	}
	
	class ReplyButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			MeltonV7XMain.textArea.setText("@" + ((TimeLineData)list.getSelectedValue()).getScreenName() + " ");
			MeltonV7XMain.replyflug = true;
			MeltonV7XMain.replypushed = false;
			talkview.dispose();
		}
	}
    
    class FavoriteButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				if ( !(((TimeLineData)list.getSelectedValue()).getStatus().isFavorited()) ) {
					MeltonV7XMain.twitter.createFavorite(((TimeLineData)list.getSelectedValue()).getStatusID());
					list.clearSelection();
				} else {
					MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)list.getSelectedValue()).getStatusID());
					list.clearSelection();
				}
			} catch (TwitterException e) {
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("0E0152");
			}
		}
    }

    class RetweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				MeltonV7XMain.twitter.retweetStatus(((TimeLineData)list.getSelectedValue()).getStatusID());
				list.clearSelection();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("0E0166");
			}
		}
	}
    
    class UfretweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			MeltonV7XMain.textArea.setText(" RT @"+ ((TimeLineData)list.getSelectedValue()).getScreenName() + " " + ((TimeLineData)list.getSelectedValue()).getTweetText());
			list.clearSelection();
		}
	}
    
    class ClearButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		list.clearSelection();
    		MeltonV7XMain.replyflug = false;
    	}
    }
    
    class LinkButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		@SuppressWarnings("unused")
    		//リンクウィンドウ
    		LinkWindow linkWindow = new LinkWindow(((TimeLineData)list.getSelectedValue()).getStatus());
        	list.clearSelection();
		}
    }
    
    class CopyButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		//パクる
    		MeltonV7XMain.textArea.setText(((TimeLineData)list.getSelectedValue()).getTweetText());
        	list.clearSelection();
    	}
    }
}