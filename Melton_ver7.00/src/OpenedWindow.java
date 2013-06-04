//ツイートオープン時に使うクラス

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import twitter4j.Status;
import twitter4j.TwitterException;

public class OpenedWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	JFrame openedwindow;
	JButton replybutton = new JButton(" リプライ   ");
	JButton retweetbutton = new JButton("リツイート");
    JButton ufretweetbutton = new JButton("  非公式RT ");
    JButton favorite = new JButton("   ふぁぼ     ");
    
	public OpenedWindow ( String text, Status st ) throws TwitterException {
		openedwindow = new JFrame();
		openedwindow.setIconImage(MeltonV7XMain.icon);
		JTextArea openedtext = new JTextArea( text );
		JPanel buttonpanel = new JPanel();
		JScrollPane scroll = new JScrollPane(openedtext);
		openedtext.setLineWrap(true);
		openedtext.setEditable(false);
		openedwindow.setTitle("Melton  - ツイート -");
		openedwindow.setResizable(false);
		openedwindow.getContentPane().setLayout(new BorderLayout());
		openedwindow.getContentPane().add(BorderLayout.CENTER,scroll);
		replybutton.addActionListener(new ReplyButtonActionAdapter());
		retweetbutton.addActionListener(new RetweetButtonActionAdapter());
		ufretweetbutton.addActionListener(new UfretweetButtonActionAdapter());
		favorite.addActionListener(new FavoriteButtonActionAdapter());
		buttonpanel.add(replybutton);
		buttonpanel.add(retweetbutton);
		buttonpanel.add(ufretweetbutton);
		buttonpanel.add(favorite);
		openedwindow.getContentPane().add(BorderLayout.SOUTH,buttonpanel);
		openedwindow.setBounds(500,300,500,300);
		openedwindow.setVisible(true);
		openedwindow.setAlwaysOnTop(true);
	}
	
	class ReplyButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			openedwindow.setVisible(false);
			if (MeltonV7XMain.replypushed == true){
				if ( MeltonV7XMain.tabnum == 0 ) {
					MeltonV7XMain.textArea.setText("@" + ((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getScreenName() + " ");
				} else if ( MeltonV7XMain.tabnum == 1 ) {
					MeltonV7XMain.textArea.setText("@" + ((TimeLineData)MeltonV7XMain.list1.getSelectedValue()).getScreenName() + " ");
				} else if ( MeltonV7XMain.tabnum == 2 ) {
					MeltonV7XMain.textArea.setText("@" + ((TimeLineData)MeltonV7XMain.list2.getSelectedValue()).getScreenName() + " ");
				}
				MeltonV7XMain.replyflug = true;
				MeltonV7XMain.replypushed = false;
			}else{
				MeltonV7XMain.textArea.setText("");
				MeltonV7XMain.replyflug = false;
				MeltonV7XMain.replypushed = true;
			}
			openedwindow.dispose();
		}
	}
    
    class FavoriteButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			openedwindow.setVisible(false);
			try {
				if ( !(((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getStatus().isFavorited()) ) {
					if ( MeltonV7XMain.tabnum == 0 ) {
						MeltonV7XMain.twitter.createFavorite(((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getStatusID());
						MeltonV7XMain.list0.clearSelection();
					} else if ( MeltonV7XMain.tabnum == 1 ) {
						MeltonV7XMain.twitter.createFavorite(((TimeLineData)MeltonV7XMain.list1.getSelectedValue()).getStatusID());
						MeltonV7XMain.list1.clearSelection();
					}
				} else {
					if ( MeltonV7XMain.tabnum == 0 ) {
						MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getStatusID());
						MeltonV7XMain.list0.clearSelection();
					} else if ( MeltonV7XMain.tabnum == 1 ) {
						MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)MeltonV7XMain.list1.getSelectedValue()).getStatusID());
						MeltonV7XMain.list1.clearSelection();
					}
				}
			} catch (TwitterException e) {
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("0B0089");
			}
			openedwindow.dispose();
		}
    }
    
    class RetweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			openedwindow.setVisible(false);
			try {
				if ( MeltonV7XMain.tabnum == 0 ) {
					MeltonV7XMain.twitter.retweetStatus(((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getStatusID());
					MeltonV7XMain.list0.clearSelection();
				} else if ( MeltonV7XMain.tabnum == 1 ) {
					MeltonV7XMain.twitter.retweetStatus(((TimeLineData)MeltonV7XMain.list1.getSelectedValue()).getStatusID());
					MeltonV7XMain.list1.clearSelection();
				} else if ( MeltonV7XMain.tabnum == 2 ) {
					MeltonV7XMain.twitter.retweetStatus(((TimeLineData)MeltonV7XMain.list2.getSelectedValue()).getStatusID());
					MeltonV7XMain.list2.clearSelection();
				}
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090455");
			}
			openedwindow.dispose();
		}
	}
    
    class UfretweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if ( MeltonV7XMain.tabnum == 0 ) {
				MeltonV7XMain.textArea.setText(" RT @"+ ((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getScreenName() + " " + ((TimeLineData)MeltonV7XMain.list0.getSelectedValue()).getTweetText());
				MeltonV7XMain.list0.clearSelection();
			} else if ( MeltonV7XMain.tabnum == 1 ) {
				MeltonV7XMain.textArea.setText(" RT @"+ ((TimeLineData)MeltonV7XMain.list1.getSelectedValue()).getScreenName() + " " + ((TimeLineData)MeltonV7XMain.list1.getSelectedValue()).getTweetText());
				MeltonV7XMain.list1.clearSelection();
			} else if ( MeltonV7XMain.tabnum == 2 ) {
				MeltonV7XMain.textArea.setText(" RT @"+ ((TimeLineData)MeltonV7XMain.list2.getSelectedValue()).getScreenName() + " " + ((TimeLineData)MeltonV7XMain.list2.getSelectedValue()).getTweetText());
				MeltonV7XMain.list2.clearSelection();
			}
		}
	}
}