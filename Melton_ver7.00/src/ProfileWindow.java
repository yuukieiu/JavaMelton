import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

public class ProfileWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame profilewindow;
	JLabel profileicon = new JLabel();
	@SuppressWarnings("rawtypes")
	DefaultListModel model = new DefaultListModel();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JList list = new JList(model);
	JButton followbutton;
	JTextArea profiletext;
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JLabel following;
	long target;
	Paging pages;
	JScrollPane scroll;
	Status targetstatus;
	JPanel buttonpanel = new JPanel();
	
	@SuppressWarnings("unchecked")
	public ProfileWindow ( Status status ) {
		targetstatus = status;
		profilewindow = new JFrame();
		setTitle("Melton  - プロフィール -");
		setIconImage(MeltonV7XMain.icon);
		profiletext = new JTextArea();
		profiletext.setLineWrap(true);
		profiletext.setEditable(false);
		list.setCellRenderer(new TextAreaRenderer());
		list.setFixedCellHeight(85);
		list.setLayoutOrientation(JList.VERTICAL);
		scroll = new JScrollPane(list);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(7);
		try {
			URL url = new URL(status.getUser().getProfileImageURLHttps());
			ImageIcon icon = new ImageIcon(url);
			profileicon.setIcon(icon);
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0C0062");
			e.printStackTrace();
		}
		profileicon.setText("<html><b>" + status.getUser().getScreenName() + "</b>  - " + status.getUser().getName() + " -<br>" + status.getUser().getDescription() +"</html>");
		profilewindow.setLayout(new BorderLayout());
		profiletext.setText(status.getUser().getDescription());
		panel1.add(profiletext);
		target = status.getUser().getId();
		try {
			if ( MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), status.getUser().getId()).isSourceFollowingTarget() ) {
				//ユーザーが対象をフォローしている
				following = new JLabel("片思い");
				followbutton = new JButton("リムーブ");
				if ( MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), status.getUser().getId()).isSourceFollowedByTarget() ) {
					//相互フォロー
					following.setText("相互フォロー");
				}
			} else if ( !(MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), status.getUser().getId()).isSourceFollowingTarget()) ) {
				//ユーザーが対象をフォローしていない
				following = new JLabel("未フォロー");
				followbutton = new JButton("フォロー");
			} else if ( MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), status.getUser().getId()).isSourceFollowedByTarget() ) {
				//ユーザーが対象にフォローされている
				following = new JLabel("フォローされている");
				followbutton = new JButton("フォロー");
			} else if ( !(MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), status.getUser().getId()).isSourceFollowedByTarget()) ) {
				//ユーザーが対象をフォローしていない
				following = new JLabel("無関心");
				followbutton = new JButton("フォロー");
			}
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0C0095");
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0C0100");
			e.printStackTrace();
		}
		followbutton.addActionListener(new FollowButtonActionAdapter());
		panel2.add(following);
		panel2.add(followbutton);
        JButton retweetbutton = new JButton("リツイート");
        retweetbutton.addActionListener(new RetweetButtonActionAdapter());
        JButton ufretweetbutton = new JButton("  非公式RT ");
        ufretweetbutton.addActionListener(new UfretweetButtonActionAdapter());
        JButton favorite = new JButton("   ふぁぼ     ");
        favorite.addActionListener(new FavoriteButtonActionAdapter());
        JButton copybutton = new JButton("   パクる     ");
        copybutton.addActionListener(new CopyButtonActionAdapter());
        JButton linkbutton = new JButton("   リンク     ");
        linkbutton.addActionListener(new LinkButtonActionAdapter());
        JButton openbutton = new JButton("   ひらく     ");
        openbutton.addActionListener(new OpenButtonActionAdapter());
        JButton talkbutton = new JButton("    かいわ    ");
        talkbutton.addActionListener(new TalkButtonActionAdapter());
        buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.Y_AXIS));
        buttonpanel.add(retweetbutton);
        buttonpanel.add(ufretweetbutton);
        buttonpanel.add(favorite);
        buttonpanel.add(copybutton);
        buttonpanel.add(linkbutton);
        buttonpanel.add(openbutton);
        buttonpanel.add(talkbutton);
		getContentPane().add(BorderLayout.NORTH, profileicon);
	    getContentPane().add(BorderLayout.SOUTH, panel2);
	    getContentPane().add(BorderLayout.CENTER, scroll);
	    getContentPane().add(BorderLayout.EAST, buttonpanel);
		setBounds(400,100,550,660);
		setVisible(true);
    	pages = new Paging(1,100);
    	List<Status> statuses = null;
    	try {
			statuses = MeltonV7XMain.twitter.getUserTimeline(status.getUser().getId(),pages);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("0C0142");
		}
    	for (Status status2 : statuses) {
	    	String[] cliantname = new String[2];
			cliantname = status2.getSource().split(">");
			if ( !(cliantname[0].equals("web")) ) {
				cliantname = cliantname[1].split("<");
			}
	    	MeltonV7XMain.dataB[MeltonV7XMain.subTLMax] = new TimeLineData(status2, status2.getUser().getScreenName(), status2.getId(), status2.getText(), status2.getUser().getName(), status2.getCreatedAt(), status2.getUser().getProfileImageURL(),cliantname[0]);
	    	MeltonV7XMain.subTLMax++;
	    }
	    for ( int j = MeltonV7XMain.subTLMax - 1; j >= 0; j-- ) {
	    	model.add(0,MeltonV7XMain.dataB[j]);
	    }
	    MeltonV7XMain.subTLMax = 0;
	}
	
	class FollowButtonActionAdapter implements ActionListener {
		public void actionPerformed( ActionEvent ev ) {
			if ( ev.getActionCommand().equals("フォロー") ) {
				try {
					MeltonV7XMain.twitter.createFriendship(target);
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0C0167");
					e.printStackTrace();
				}
				try {
					if ( MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), targetstatus.getUser().getId()).isSourceFollowingTarget() ) {
						//ユーザーが対象をフォローしている
						following.setText("片思い");
						followbutton.setText("リムーブ");
						if ( MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), targetstatus.getUser().getId()).isSourceFollowedByTarget() ) {
							//相互フォロー
							following.setText("相互フォロー");
						}
					}
				} catch (IllegalStateException e) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0C0183");
					e.printStackTrace();
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0C0188");
					e.printStackTrace();
				}
			} else if ( ev.getActionCommand().equals("リムーブ") ) {
				try {
					MeltonV7XMain.twitter.destroyFriendship(target);
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0C0197");
					e.printStackTrace();
				}
				try {
					if ( !(MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), targetstatus.getUser().getId()).isSourceFollowingTarget()) ) {
						//ユーザーが対象をフォローしていない
						following.setText("未フォロー");
						followbutton.setText("フォロー");
					} else if ( MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), targetstatus.getUser().getId()).isSourceFollowedByTarget() ) {
						//ユーザーが対象にフォローされている
						following.setText("フォローされている");
						followbutton.setText("フォロー");
					} else if ( !(MeltonV7XMain.twitter.showFriendship(MeltonV7XMain.twitter.getId(), targetstatus.getUser().getId()).isSourceFollowedByTarget()) ) {
						//ユーザーが対象をフォローしていない
						following.setText("無関心");
						followbutton.setText("フォロー");
					}
				} catch (IllegalStateException e) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0C0217");
					e.printStackTrace();
				} catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("0C0222");
					e.printStackTrace();
				}
			}
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
				ErrorWindow error = new ErrorWindow("0C0242");
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
				ErrorWindow error = new ErrorWindow("0C0256");
			}
		}
	}
    
    class UfretweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			MeltonV7XMain.textArea.setText(" RT @"+ ((TimeLineData)list.getSelectedValue()).getScreenName() + " " + ((TimeLineData)list.getSelectedValue()).getTweetText());
			list.clearSelection();
			dispose();
		}
	}
    
    class OpenButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			//ツイートオープン
			try {
				@SuppressWarnings("unused")
				OpenedWindow openedwindow = new OpenedWindow("" + ((TimeLineData)list.getSelectedValue()).getScreenName() + " -" + ((TimeLineData)list.getSelectedValue()).getUserName() + "-\n" + ((TimeLineData)list.getSelectedValue()).getTweetText() + "\n" + ((TimeLineData)list.getSelectedValue()).getDate() + "\nvia " + ((TimeLineData)list.getSelectedValue()).getCliantname(), ((TimeLineData)list.getSelectedValue()).getStatus());
				list.clearSelection();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("0C0280");
			}
		}
	}

    class TalkButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		//会話表示
    		@SuppressWarnings("unused")
    		TalkView view = new TalkView(((TimeLineData)list.getSelectedValue()).getStatus());
        	list.clearSelection();
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
       		dispose();
    	}
    }
    
}