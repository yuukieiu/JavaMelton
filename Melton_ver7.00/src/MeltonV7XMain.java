import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserStreamAdapter;

public class MeltonV7XMain extends JFrame implements KeyListener,ListSelectionListener {
	private static final long serialVersionUID = 1L;
	static Twitter twitter;
	static Paging pages = null;
	public static int subTLMax = 0;
	public static TimeLineData data;
	public static TimeLineData[] dataB = new TimeLineData[100];
	@SuppressWarnings("rawtypes")
	static DefaultListModel model0 = new DefaultListModel();
	@SuppressWarnings("rawtypes")
	static DefaultListModel model1 = new DefaultListModel();
	@SuppressWarnings("rawtypes")
	static DefaultListModel model2 = new DefaultListModel();
    public static boolean replyflug = false;
    static boolean fileputflug = false;
    static boolean replypushed = true;
    static JTextArea textArea = new JTextArea();
    static JLabel puttingfile;
    static JLabel invisiinfo;
    @SuppressWarnings({ "rawtypes", "unchecked" })
	static JList list0 = new JList(model0);
    @SuppressWarnings({ "rawtypes", "unchecked" })
    static JList list1 = new JList(model1);
    @SuppressWarnings({ "rawtypes", "unchecked" })
    static JList list2 = new JList(model2);
    public static boolean accountchanged = false;
    static String scn;
    public static TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
    public static int tabnum = 0; 
    static JTabbedPane tab;
    static Image icon;
    static StartWindow start;
    static JLabel info;
    static URL url;
    static String[] loadedname = new String[100];
    static int loadednamenum = 0;
    static JPanel northpanel;
    static JPanel eastpanel;
    static JLabel apiinfo;
    static JScrollPane scroll0;
    static JScrollPane scroll1;
    static JScrollPane scroll2;
    static boolean scrollnew = false;
    JMenuItem menu2;
    public File fileput;
    static boolean selfinvisible = false;
    
    @SuppressWarnings("unchecked")
	public MeltonV7XMain() {
    	icon = getToolkit().getImage("Meltonicon.png");
    	setIconImage(icon);
    	try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090080");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090085");
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090090");
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090095");
			e.printStackTrace();
		}
    	JButton replybutton = new JButton(" リプライ   ");
        JButton retweetbutton = new JButton("リツイート");
        JButton ufretweetbutton = new JButton("  非公式RT ");
        JButton favorite = new JButton("   ふぁぼ     ");
        JButton copybutton = new JButton("   パクる     ");
        JButton favbombbutton = new JButton("  メルトン  ");
        JButton clearbutton = new JButton("   クリア     ");
        JButton linkbutton = new JButton("   リンク     ");
        JButton profilebutton = new JButton("  ユーザー  ");
        JButton fileputbutton = new JButton("  画像添付  ");
    	northpanel = new JPanel();
		northpanel.setLayout(new BorderLayout());
		//JPanel westpanel = new JPanel();
		//JPanel centerpanel = new JPanel();
		eastpanel = new JPanel();
		eastpanel.setLayout(new BoxLayout(eastpanel, BoxLayout.Y_AXIS));
		JPanel southpanel = new JPanel();
		apiinfo = new JLabel();
		southpanel.add(apiinfo);
		JButton tweetbutton = new JButton("つぶやく");
		tweetbutton.addActionListener(new TweetButtonActionAdapter());
		replybutton.addActionListener(new ReplyButtonActionAdapter());
		retweetbutton.addActionListener(new RetweetButtonActionAdapter());
		ufretweetbutton.addActionListener(new UfretweetButtonActionAdapter());
		favorite.addActionListener(new FavoriteButtonActionAdapter());
		copybutton.addActionListener(new CopyButtonActionAdapter());
		favbombbutton.addActionListener(new FavbombButtonActionAdapter());
		clearbutton.addActionListener(new ClearButtonActionAdapter());
		linkbutton.addActionListener(new LinkButtonActionAdapter());
		profilebutton.addActionListener(new ProfileButtonActionAdapter());
		fileputbutton.addActionListener(new FileputButtonActionAdapter());
		JButton openbutton = new JButton("   ひらく     ");
		openbutton.addActionListener(new OpenButtonActionAdapter());
		JButton talkbutton = new JButton("    かいわ    ");
		talkbutton.addActionListener(new TalkButtonActionAdapter());
		JMenuBar menubar = new JMenuBar();
		JMenu testmenu = new JMenu("メニュー");
		JMenuItem menu0 = new JMenuItem("アカウント追加");
		menu0.addActionListener(new Menu0ActionAdapter());
		JMenuItem menu1 = new JMenuItem("アカウント切替");
		menu1.addActionListener(new Menu1ActionAdapter());
		menu2 = new JMenuItem("常に最新のツイートを追う（調整中）");
		menu2.addActionListener(new Menu2ActionAdapter());
		JMenuItem menu3 = new JMenuItem("インビジ");
		menu3.addActionListener(new Menu3ActionAdapter());
		testmenu.add(menu0);
		testmenu.add(menu1);
		testmenu.add(menu2);
		testmenu.add(menu3);
		menubar.add(testmenu);
		setJMenuBar(menubar);
		textArea.setLineWrap(true);
		textArea.addKeyListener(this);
		addKeyListener(this);
		info = new JLabel();
		puttingfile = new JLabel("画像添付モード：off");
		invisiinfo = new JLabel("インビジ：off");
		northpanel.add(BorderLayout.CENTER, textArea);
		northpanel.add(BorderLayout.WEST, info);
		northpanel.add(BorderLayout.SOUTH, tweetbutton);
		eastpanel.add(replybutton);
		eastpanel.add(retweetbutton);
		eastpanel.add(favorite);
		eastpanel.add(ufretweetbutton);
		eastpanel.add(openbutton);
		eastpanel.add(talkbutton);
		eastpanel.add(linkbutton);
		eastpanel.add(profilebutton);
		eastpanel.add(clearbutton);
		eastpanel.add(copybutton);
		eastpanel.add(favbombbutton);
		eastpanel.add(fileputbutton);
		southpanel.add(puttingfile);
		southpanel.add(invisiinfo);
		list0.setCellRenderer(new TextAreaRenderer());
		list0.setFixedCellHeight(85);
		list0.setLayoutOrientation(JList.VERTICAL);
		scroll0 = new JScrollPane(list0);
		scroll0.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		list1.setCellRenderer(new TextAreaRenderer());
		list1.setFixedCellHeight(85);
		list1.setLayoutOrientation(JList.VERTICAL);
		scroll1 = new JScrollPane(list1);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		list2.setCellRenderer(new TextAreaRenderer());
		list2.setFixedCellHeight(85);
		list2.setLayoutOrientation(JList.VERTICAL);
		scroll2 = new JScrollPane(list2);
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tab = new  JTabbedPane();
		tab.add(scroll0,"タイムライン");
		tab.add(scroll1,"メンション");
		tab.add(scroll2,"ツイート");
		tab.addChangeListener(new TabChangeAdapter());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.NORTH, northpanel);
		getContentPane().add(BorderLayout.EAST, eastpanel);
		getContentPane().add(BorderLayout.SOUTH, southpanel);
		scroll0.getVerticalScrollBar().setUnitIncrement(7);
		scroll1.getVerticalScrollBar().setUnitIncrement(7);
		scroll2.getVerticalScrollBar().setUnitIncrement(7);
		add(tab, BorderLayout.CENTER);
		setTitle("Melton  - Main Window -");
		setSize(1100,750);
		this.getInstance();
		list0.addListSelectionListener(this);
		list1.addListSelectionListener(this);
		list2.addListSelectionListener(this);
    }
    
    private void getInstance() {
    	TwitterFactory factory = new TwitterFactory();
    	twitter = factory.getInstance();
    }
    
    public static void main (String[] args) {
    	MeltonV7XMain meltonmain = new MeltonV7XMain();
    	icon = meltonmain.getToolkit().getImage("Meltonicon.png");
    	start = new StartWindow();
    	start.start();
    	meltonmain.setVisible(true);
    	try {
    		MyLogin.MyLoginDialog();
    	} catch ( Exception e1 ) {
    		e1.printStackTrace();
    		@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090210");
    	}
    	try {
			url = new URL(twitter.verifyCredentials().getProfileImageURL());
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090218");
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090223");
			e.printStackTrace();
		}
    	try {
			apiinfo.setText("<html>API残数：" + twitter.getAccountSettings().getRateLimitStatus().getRemaining() + "/" + twitter.getAccountSettings().getRateLimitStatus().getLimit() + "</html>");
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	info.setIcon(new ImageIcon(url));
    	MeltonV7XMain.twitterAPITest();
    	twitterStream.addListener(new MyStatusAdapter());
		twitterStream.user();
		start.start.setVisible(false);
		scrollToMaximum(scroll0.getVerticalScrollBar());
		scrollToMaximum(scroll1.getVerticalScrollBar());
		scrollToMaximum(scroll2.getVerticalScrollBar());
    }
    
    public static void scrollToMaximum ( JScrollBar scroll ){
    	JScrollBar sb = scroll;
    	sb.setValue(sb.getMaximum());
    }
    
    public static void twitterAPITest() {
    	try {
    		getHomeTimeLine();
    	} catch(Exception e){
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090238");
    	}
    }
    
    @SuppressWarnings("unchecked")
	public static void getHomeTimeLine() throws TwitterException {
    	try {
    		Thread.sleep(1);
    	} catch ( InterruptedException e ) {
			System.out.println(e);
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090248");
		}
    	if ( pages == null ) {
    		pages = new Paging(1,50);
    	}
    	List<Status> statuses = null;
		try {
			statuses = twitter.getHomeTimeline(pages);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090260");
		}
	    for (Status status : statuses) {
	    	String[] cliantname = new String[2];
			cliantname = status.getSource().split(">");
			if ( !(cliantname[0].equals("web")) ) {
				cliantname = cliantname[1].split("<");
			}
	    	dataB[subTLMax] = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
	    	try {
				scn = twitter.getScreenName();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090275");
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090280");
			}
	    	/*if ( status.getText().matches(".*" + scn + ".*") ) {
	    		InfomationNotifier notifier1 = new InfomationNotifier(status,1);
				notifier1.start();
	    	}*/
	    	subTLMax++;
	    }
	    for ( int j = subTLMax - 1; j >= 0; j-- ) {
	    	model0.add(model0.getSize(),dataB[j]);
	    }
	    subTLMax = 0;
	    
	    try {
			statuses = twitter.getMentionsTimeline(pages);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090300");
		}
	    for (Status status : statuses) {
	    	String[] cliantname = new String[2];
			cliantname = status.getSource().split(">");
			if ( !(cliantname[0].equals("web")) ) {
				cliantname = cliantname[1].split("<");
			}
	    	dataB[subTLMax] = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
	    	subTLMax++;
	    }
	    for ( int j = subTLMax - 1; j >= 0; j-- ) {
	    	model1.add(model1.getSize(),dataB[j]);
	    }
	    subTLMax = 0;
	    
	    try {
			statuses = twitter.getUserTimeline(pages);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090322");
		}
	    for (Status status : statuses) {
	    	String[] cliantname = new String[2];
			cliantname = status.getSource().split(">");
			if ( !(cliantname[0].equals("web")) ) {
				cliantname = cliantname[1].split("<");
			}
	    	dataB[subTLMax] = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
	    	subTLMax++;
	    }
	    for ( int j = subTLMax - 1; j >= 0; j-- ) {
	    	model2.add(model2.getSize(),dataB[j]);
	    }
	    subTLMax = 0;
    }
    
    class TweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			StatusUpdate status;
			if(replyflug == false){
				String tweet = "";
				while(tweet.isEmpty()){
					tweet = textArea.getText();
					if(tweet.length() > 140) {
						tweet = "";
					}
				}
				try {
					status = new StatusUpdate(tweet);
					if ( fileputflug == true ){
						status.media(fileput);
						fileputflug = false;
						puttingfile.setText("画像添付モードoff");
					}
					MeltonV7XMain.twitter.updateStatus(status);
				} catch (TwitterException e1) {
					e1.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("090355");
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					System.out.println(e);
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("090362");
				}
		      
				tweet = "";
				textArea.setText("");
			} else {
	            try {
	            	if ( tabnum == 0 ) {
	            		if(list0.getSelectedIndex() != -1) {
	            			status = new StatusUpdate(textArea.getText()).inReplyToStatusId(((TimeLineData)list0.getSelectedValue()).getStatusID());
	            			if ( fileputflug == true ){
	    						status.media(fileput);
	    						fileputflug = false;
	    						puttingfile.setText("画像添付モードoff");
	    					}
	            			MeltonV7XMain.twitter.updateStatus(status);
	            			list0.clearSelection();
	            		}
	            	} else if ( tabnum == 1 ) {
	            		if(list1.getSelectedIndex() != -1) {
	            			status = new StatusUpdate(textArea.getText()).inReplyToStatusId(((TimeLineData)list1.getSelectedValue()).getStatusID());
	            			if ( fileputflug == true ){
	    						status.media(fileput);
	    						fileputflug = false;
	    						puttingfile.setText("画像添付モードoff");
	    					}
	            			MeltonV7XMain.twitter.updateStatus(status);
	            			list1.clearSelection();
	            		}
	            	} else if ( tabnum == 2 ) {
	            		if(list2.getSelectedIndex() != -1) {
	            			status = new StatusUpdate(textArea.getText()).inReplyToStatusId(((TimeLineData)list2.getSelectedValue()).getStatusID());
	            			if ( fileputflug == true ){
	    						status.media(fileput);
	    						fileputflug = false;
	    						puttingfile.setText("画像添付モードoff");
	    					}
	            			MeltonV7XMain.twitter.updateStatus(status);
	            			list2.clearSelection();
	            		}
	            	}
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("090389");
				}
	            textArea.setText("");
	            replypushed = true;
	            replyflug = false;
			}
		}
	}
    
    class ReplyButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (replypushed == true){
				if ( tabnum == 0 ) {
					textArea.setText("@" + ((TimeLineData)list0.getSelectedValue()).getScreenName() + " ");
				} else if ( tabnum == 1 ) {
					textArea.setText("@" + ((TimeLineData)list1.getSelectedValue()).getScreenName() + " ");
				} else if ( tabnum == 2 ) {
					textArea.setText("@" + ((TimeLineData)list2.getSelectedValue()).getScreenName() + " ");
				}
				replyflug = true;
				replypushed = false;
			}else{
				textArea.setText("");
				replyflug = false;
				replypushed = true;
			}
		}
	}
    
    class FavoriteButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				if ( !(((TimeLineData)list0.getSelectedValue()).getStatus().isFavorited()) ) {
					if ( tabnum == 0 ) {
						MeltonV7XMain.twitter.createFavorite(((TimeLineData)list0.getSelectedValue()).getStatusID());
						list0.clearSelection();
					} else if ( tabnum == 1 ) {
						MeltonV7XMain.twitter.createFavorite(((TimeLineData)list1.getSelectedValue()).getStatusID());
						list1.clearSelection();
					} else if ( tabnum == 2 ) {
						MeltonV7XMain.twitter.createFavorite(((TimeLineData)list2.getSelectedValue()).getStatusID());
						list2.clearSelection();
					}
				} else {
					if ( tabnum == 0 ) {
						MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)list0.getSelectedValue()).getStatusID());
						list0.clearSelection();
					} else if ( tabnum == 1 ) {
						MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)list1.getSelectedValue()).getStatusID());
						list1.clearSelection();
					} else if ( tabnum == 2 ) {
						MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)list2.getSelectedValue()).getStatusID());
						list2.clearSelection();
					}
				}
			} catch (TwitterException e) {
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090447");
			}
		}
    }

    class RetweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				if ( tabnum == 0 ) {
					MeltonV7XMain.twitter.retweetStatus(((TimeLineData)list0.getSelectedValue()).getStatusID());
					list0.clearSelection();
				} else if ( tabnum == 1 ) {
					MeltonV7XMain.twitter.retweetStatus(((TimeLineData)list1.getSelectedValue()).getStatusID());
					list1.clearSelection();
				} else if ( tabnum == 2 ) {
					MeltonV7XMain.twitter.retweetStatus(((TimeLineData)list2.getSelectedValue()).getStatusID());
					list2.clearSelection();
				}
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090469");
			}
		}
	}
    
    class UfretweetButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if ( tabnum == 0 ) {
				textArea.setText(" RT @"+ ((TimeLineData)list0.getSelectedValue()).getScreenName() + " " + ((TimeLineData)list0.getSelectedValue()).getTweetText());
				list0.clearSelection();
			} else if ( tabnum == 1 ) {
				textArea.setText(" RT @"+ ((TimeLineData)list1.getSelectedValue()).getScreenName() + " " + ((TimeLineData)list1.getSelectedValue()).getTweetText());
				list1.clearSelection();
			} else if ( tabnum == 2 ) {
				textArea.setText(" RT @"+ ((TimeLineData)list2.getSelectedValue()).getScreenName() + " " + ((TimeLineData)list2.getSelectedValue()).getTweetText());
				list2.clearSelection();
			}
		}
	}
    
    class OpenButtonActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			//ツイートオープン
			try {
				if ( tabnum == 0 ) {
					@SuppressWarnings("unused")
					OpenedWindow openedwindow = new OpenedWindow("" + ((TimeLineData)list0.getSelectedValue()).getScreenName() + " -" + ((TimeLineData)list0.getSelectedValue()).getUserName() + "-\n" + ((TimeLineData)list0.getSelectedValue()).getTweetText() + "\n" + ((TimeLineData)list0.getSelectedValue()).getDate() + "\nvia " + ((TimeLineData)list0.getSelectedValue()).getCliantname(), ((TimeLineData)list0.getSelectedValue()).getStatus());
					list0.clearSelection();
				} else if ( tabnum == 1 ) {
					@SuppressWarnings("unused")
					OpenedWindow openedwindow = new OpenedWindow("" + ((TimeLineData)list1.getSelectedValue()).getScreenName() + " -" + ((TimeLineData)list1.getSelectedValue()).getUserName() + "-\n" + ((TimeLineData)list1.getSelectedValue()).getTweetText() + "\n" + ((TimeLineData)list1.getSelectedValue()).getDate() + "\nvia " + ((TimeLineData)list1.getSelectedValue()).getCliantname(), ((TimeLineData)list1.getSelectedValue()).getStatus());
					list1.clearSelection();
				} else if ( tabnum == 1 ) {
					@SuppressWarnings("unused")
					OpenedWindow openedwindow = new OpenedWindow("" + ((TimeLineData)list2.getSelectedValue()).getScreenName() + " -" + ((TimeLineData)list2.getSelectedValue()).getUserName() + "-\n" + ((TimeLineData)list2.getSelectedValue()).getTweetText() + "\n" + ((TimeLineData)list2.getSelectedValue()).getDate() + "\nvia " + ((TimeLineData)list2.getSelectedValue()).getCliantname(), ((TimeLineData)list2.getSelectedValue()).getStatus());
					list2.clearSelection();
				}
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090510");
			}
		}
	}

    class TalkButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		//会話表示
    		if ( tabnum == 0 ) {
    			if ( ((TimeLineData)list0.getSelectedValue()).getStatus().isRetweet() ) {
    				@SuppressWarnings("unused")
    				TalkView view = new TalkView(((TimeLineData)list0.getSelectedValue()).getStatus().getRetweetedStatus());
    			} else {
    				@SuppressWarnings("unused")
					TalkView view = new TalkView(((TimeLineData)list0.getSelectedValue()).getStatus());
    			}
    			list0.clearSelection();
    		} else if ( tabnum == 1 ) {
    			if ( ((TimeLineData)list1.getSelectedValue()).getStatus().isRetweet() ) {
    				@SuppressWarnings("unused")
    				TalkView view = new TalkView(((TimeLineData)list1.getSelectedValue()).getStatus().getRetweetedStatus());
    			} else {
    				@SuppressWarnings("unused")
					TalkView view = new TalkView(((TimeLineData)list1.getSelectedValue()).getStatus());
    			}
        		list1.clearSelection();
    		} else if ( tabnum == 2 ) {
    			if ( ((TimeLineData)list2.getSelectedValue()).getStatus().isRetweet() ) {
    				@SuppressWarnings("unused")
    				TalkView view = new TalkView(((TimeLineData)list2.getSelectedValue()).getStatus().getRetweetedStatus());
    			} else {
    				@SuppressWarnings("unused")
					TalkView view = new TalkView(((TimeLineData)list2.getSelectedValue()).getStatus());
    			}
        		list2.clearSelection();
    		}
    	}
    }
    
    class ProfileButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		//プロフィール表示
    		if ( tabnum == 0 ) {
    			if (((TimeLineData)list0.getSelectedValue()).getStatus().isRetweet()) {
    				@SuppressWarnings("unused")
					ProfileWindow profile = new ProfileWindow(((TimeLineData)list0.getSelectedValue()).getStatus().getRetweetedStatus());
    			} else {
    				@SuppressWarnings("unused")
					ProfileWindow profile = new ProfileWindow(((TimeLineData)list0.getSelectedValue()).getStatus());
    			}
        		list0.clearSelection();
    		} else if ( tabnum == 1 ) {
    			if (((TimeLineData)list1.getSelectedValue()).getStatus().isRetweet()) {
    				@SuppressWarnings("unused")
					ProfileWindow profile = new ProfileWindow(((TimeLineData)list1.getSelectedValue()).getStatus().getRetweetedStatus());
    			} else {
    				@SuppressWarnings("unused")
					ProfileWindow profile = new ProfileWindow(((TimeLineData)list1.getSelectedValue()).getStatus());
    			}
        		list1.clearSelection();
    		} else if ( tabnum == 2 ) {
    			if (((TimeLineData)list2.getSelectedValue()).getStatus().isRetweet()) {
    				@SuppressWarnings("unused")
					ProfileWindow profile = new ProfileWindow(((TimeLineData)list2.getSelectedValue()).getStatus().getRetweetedStatus());
    			} else {
    				@SuppressWarnings("unused")
					ProfileWindow profile = new ProfileWindow(((TimeLineData)list2.getSelectedValue()).getStatus());
    			}
        		list2.clearSelection();
    		}
    	}
    }
    
    class ClearButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		list0.clearSelection();
    		list1.clearSelection();
    		list2.clearSelection();
    		textArea.setText("");
    		replyflug = false;
    		fileputflug = false;
    		replypushed = true;
    	}
    }
    
    class LinkButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		if ( tabnum == 0 ) {
    			@SuppressWarnings("unused")
    			//リンクウィンドウ
    			LinkWindow linkWindow = new LinkWindow(((TimeLineData)list0.getSelectedValue()).getStatus());
        		list0.clearSelection();
    		} else if ( tabnum == 1 ) {
    			@SuppressWarnings("unused")
    			//リンクウィンドウ
    			LinkWindow linkWindow = new LinkWindow(((TimeLineData)list1.getSelectedValue()).getStatus());
        		list1.clearSelection();
    		} else if ( tabnum == 2 ) {
    			@SuppressWarnings("unused")
    			//リンクウィンドウ
    			LinkWindow linkWindow = new LinkWindow(((TimeLineData)list2.getSelectedValue()).getStatus());
        		list2.clearSelection();
    		}
		}
    }
    
    class CopyButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		//パクる
    		if ( tabnum == 0 ) {
    			if( ((TimeLineData)list0.getSelectedValue()).getStatus().isRetweet() ) {
    				textArea.setText(((TimeLineData)list0.getSelectedValue()).getStatus().getRetweetedStatus().getText());
    			} else {
    				textArea.setText(((TimeLineData)list0.getSelectedValue()).getTweetText());
    			}
    			list0.clearSelection();
    		} else if ( tabnum == 1 ) {
    			if( ((TimeLineData)list1.getSelectedValue()).getStatus().isRetweet() ) {
    				textArea.setText(((TimeLineData)list1.getSelectedValue()).getStatus().getRetweetedStatus().getText());
    			} else {
    				textArea.setText(((TimeLineData)list1.getSelectedValue()).getTweetText());
    			}
        		list1.clearSelection();
    		} else if ( tabnum == 2 ) {
    			if( ((TimeLineData)list2.getSelectedValue()).getStatus().isRetweet() ) {
    				textArea.setText(((TimeLineData)list2.getSelectedValue()).getStatus().getRetweetedStatus().getText());
    			} else {
    				textArea.setText(((TimeLineData)list2.getSelectedValue()).getTweetText());
    			}
        		list2.clearSelection();
    		}
    	}
    }
    
    class FavbombButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		//ふぁぼ爆
    		FavBombThread thread3 = new FavBombThread();
    		thread3.start();
    	}
    }
    
    class FileputButtonActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		
    		if (fileputflug == true){
    			System.out.println("画像添付モードoff");
    			puttingfile.setText("画像添付モードoff");
    			fileputflug = false;
			}else{
				System.out.println("画像添付モードon");
				puttingfile.setText("画像添付モードon");
	    		JFileChooser filechooser = new JFileChooser();
	    		FileNameExtensionFilter filter1 = new FileNameExtensionFilter("画像ファイル(*.jpg,*.jpeg,*.png,*.gif)", "jpg", "jpeg","png","gif");
	    		filechooser.setAcceptAllFileFilterUsed(false);
	    		filechooser.addChoosableFileFilter(filter1);

	    	    int selected = filechooser.showOpenDialog(MeltonV7XMain.tab);
	    	    if (selected == JFileChooser.APPROVE_OPTION){
	    	      fileput = filechooser.getSelectedFile();
	    	      System.out.println(fileput.getName());
	    	      fileputflug = true;
	    	      /*StatusUpdate status = new StatusUpdate("画像投稿テスト");
	    	      status.media(fileput);
	    	      try {
					MeltonV5XMain.twitter.updateStatus(status);
				  } catch (TwitterException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				  }*/
	    	    }else if (selected == JFileChooser.CANCEL_OPTION){
	    	      System.out.println("キャンセルされました");
	    	      puttingfile.setText("画像添付モードoff");
	    	    }else if (selected == JFileChooser.ERROR_OPTION){
	    	      System.out.println("エラー又は取消しがありました");
	    	      puttingfile.setText("画像添付モードoff");
	    	    }
			}
    	}
    }
    
    class Menu0ActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		try {
				AddAccount.MyLoginDialog();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090626");
			}
    	}
    }
    
    class Menu1ActionAdapter implements ActionListener {
    	//アカウント切り替え
    	public void actionPerformed(ActionEvent ev) {
    		try {
    			loadedname[loadednamenum] = new String();
				loadedname[loadednamenum] = twitter.getScreenName();
			} catch (IllegalStateException e1) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090640");
				e1.printStackTrace();
			} catch (TwitterException e1) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090645");
				e1.printStackTrace();
			}
    		loadednamenum++;
    		try {
				@SuppressWarnings("unused")
				ChangeAccount ca = new ChangeAccount();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("090646");
			}
    	}
    }
    
    class Menu2ActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
    		if ( menu2.getText().equals("常に最新のツイートを追う（調整中）") ) {
    			scrollnew = true;
    			menu2.setText("スクロールを手動に");
    		} else {
    			scrollnew = false;
    			menu2.setText("常に最新のツイートを追う（調整中）");
    		}
		}
    }
    
    class Menu3ActionAdapter implements ActionListener {
    	public void actionPerformed(ActionEvent ev) {
			if ( selfinvisible ) {
				selfinvisible = false;
				System.out.println("インビジ：off");
				invisiinfo.setText("インビジ：off");
			} else {
				selfinvisible = true;
				System.out.println("インビジ：on");
				invisiinfo.setText("インビジ：on");
			}
		}
    }
    
    class TabChangeAdapter implements ChangeListener {
    	public void stateChanged(ChangeEvent ev) {
    		tabnum = tab.getSelectedIndex();
    		scrollToMaximum(scroll0.getVerticalScrollBar());
    		scrollToMaximum(scroll1.getVerticalScrollBar());
    		scrollToMaximum(scroll2.getVerticalScrollBar());
    	}
    }
    
    @Override
	public void keyPressed(KeyEvent ek) {
		// TODO Auto-generated method stub
		int keycode = ek.getKeyCode();
		int mod = ek.getModifiersEx();
		if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0) {
			if(keycode == KeyEvent.VK_ENTER){
				StatusUpdate status;
				if(replyflug == false){
					String tweet = "";
					while(tweet.isEmpty()){
						tweet = textArea.getText();
						if(tweet.length() > 140) {
							tweet = "";
						}
					}
					try {
						status = new StatusUpdate(tweet);
						if ( fileputflug ){
							status.media(fileput);
							fileputflug = false;
							puttingfile.setText("画像添付モードoff");
						}
						MeltonV7XMain.twitter.updateStatus(status);
					} catch (TwitterException e1) {
						e1.printStackTrace();
						@SuppressWarnings("unused")
						ErrorWindow error = new ErrorWindow("090355");
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						System.out.println(e);
						@SuppressWarnings("unused")
						ErrorWindow error = new ErrorWindow("090362");
					}
			      
					tweet = "";
					textArea.setText("");
				} else {
		            try {
		            	if ( tabnum == 0 ) {
		            		if(list0.getSelectedIndex() != -1) {
		            			status = new StatusUpdate(textArea.getText()).inReplyToStatusId(((TimeLineData)list0.getSelectedValue()).getStatusID());
		            			if ( fileputflug ){
		    						status.media(fileput);
		    						fileputflug = false;
		    						puttingfile.setText("画像添付モードoff");
		    					}
		            			MeltonV7XMain.twitter.updateStatus(status);
		            			list0.clearSelection();
		            		}
		            	} else if ( tabnum == 1 ) {
		            		if(list1.getSelectedIndex() != -1) {
		            			status = new StatusUpdate(textArea.getText()).inReplyToStatusId(((TimeLineData)list1.getSelectedValue()).getStatusID());
		            			if ( fileputflug ){
		    						status.media(fileput);
		    						fileputflug = false;
		    						puttingfile.setText("画像添付モードoff");
		    					}
		            			MeltonV7XMain.twitter.updateStatus(status);
		            			list1.clearSelection();
		            		}
		            	} else if ( tabnum == 2 ) {
		            		if(list2.getSelectedIndex() != -1) {
		            			status = new StatusUpdate(textArea.getText()).inReplyToStatusId(((TimeLineData)list2.getSelectedValue()).getStatusID());
		            			if ( fileputflug ){
		    						status.media(fileput);
		    						fileputflug = false;
		    						puttingfile.setText("画像添付モードoff");
		    					}
		            			MeltonV7XMain.twitter.updateStatus(status);
		            			list2.clearSelection();
		            		}
		            	}
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						@SuppressWarnings("unused")
						ErrorWindow error = new ErrorWindow("090389");
					}
		            textArea.setText("");
		            replypushed = true;
		            replyflug = false;
		            fileputflug = false;
				}
			} else if (keycode == KeyEvent.VK_F1) {
				try {
					if ( !(((TimeLineData)list0.getSelectedValue()).getStatus().isFavorited()) ) {
						if ( tabnum == 0 ) {
							MeltonV7XMain.twitter.createFavorite(((TimeLineData)list0.getSelectedValue()).getStatusID());
							list0.clearSelection();
						} else if ( tabnum == 1 ) {
							MeltonV7XMain.twitter.createFavorite(((TimeLineData)list1.getSelectedValue()).getStatusID());
							list1.clearSelection();
						} else if ( tabnum == 2 ) {
							MeltonV7XMain.twitter.createFavorite(((TimeLineData)list2.getSelectedValue()).getStatusID());
							list2.clearSelection();
						}
					} else {
						if ( tabnum == 0 ) {
							MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)list0.getSelectedValue()).getStatusID());
							list0.clearSelection();
						} else if ( tabnum == 1 ) {
							MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)list1.getSelectedValue()).getStatusID());
							list1.clearSelection();
						} else if ( tabnum == 2 ) {
							MeltonV7XMain.twitter.destroyFavorite(((TimeLineData)list2.getSelectedValue()).getStatusID());
							list2.clearSelection();
						}
					}
				} catch (TwitterException e) {
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("090747");
				}
			} else if (keycode == KeyEvent.VK_F2) {
				try {
					if ( tabnum == 0 ) {
						MeltonV7XMain.twitter.retweetStatus(((TimeLineData)list0.getSelectedValue()).getStatusID());
						list0.clearSelection();
					} else if ( tabnum == 1 ) {
						MeltonV7XMain.twitter.retweetStatus(((TimeLineData)list1.getSelectedValue()).getStatusID());
						list1.clearSelection();
					} else if ( tabnum == 2 ) {
						MeltonV7XMain.twitter.retweetStatus(((TimeLineData)list2.getSelectedValue()).getStatusID());
						list2.clearSelection();
					}
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("090765");
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent ek) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent ek) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class MyStatusAdapter extends UserStreamAdapter {
	TimeLineData spdata;
	boolean scrollflag = false;
	@SuppressWarnings("unchecked")
	public void onStatus(Status status) {
		String[] cliantname = new String[2];
		cliantname = status.getSource().split(">");
		if ( !(cliantname[0].equals("web")) ) {
			cliantname = cliantname[1].split("<");
		}
		if ( !MeltonV7XMain.selfinvisible ) {
			MeltonV7XMain.data = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
			MeltonV7XMain.model0.add(MeltonV7XMain.model0.getSize(),MeltonV7XMain.data);
		} else {
			if( !status.getUser().getScreenName().matches(".*" + MeltonV7XMain.scn + ".*") ) {
				MeltonV7XMain.data = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
				MeltonV7XMain.model0.add(MeltonV7XMain.model0.getSize(),MeltonV7XMain.data);
			}
		}
		if ( MeltonV7XMain.list0.getSelectedIndex() == 0 ) {
			MeltonV7XMain.list0.clearSelection();
			//MeltonV5XMain.list0.setSelectedIndex(1);
		}
		if ( status.getText().matches(".*" + MeltonV7XMain.scn + ".*") && status.isRetweet() ) {
			InfomationNotifier notifier3 = new InfomationNotifier(status,3);
			notifier3.start();
		} else if ( status.getText().matches(".*" + MeltonV7XMain.scn + ".*") ) {
			spdata = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
			MeltonV7XMain.model1.add(MeltonV7XMain.model1.getSize(),spdata);
			if ( MeltonV7XMain.list1.getSelectedIndex() == 0 ) {
				MeltonV7XMain.list1.clearSelection();
				//MeltonV5XMain.list1.setSelectedIndex(1);
			}
			InfomationNotifier notifier1 = new InfomationNotifier(status,1);
			notifier1.start();
    	} else if ( status.getUser().getScreenName().matches(".*" + MeltonV7XMain.scn + ".*") ) {
    		spdata = new TimeLineData(status, status.getUser().getScreenName(), status.getId(), status.getText(), status.getUser().getName(), status.getCreatedAt(), status.getUser().getProfileImageURL(),cliantname[0]);
			MeltonV7XMain.model2.add(MeltonV7XMain.model2.getSize(),spdata);
			if ( MeltonV7XMain.list2.getSelectedIndex() == 0 ) {
				MeltonV7XMain.list2.clearSelection();
				//MeltonV5XMain.list2.setSelectedIndex(1);
			}
    	}
		if ( MeltonV7XMain.scrollnew ) {
			MeltonV7XMain.scrollToMaximum(MeltonV7XMain.scroll0.getVerticalScrollBar());
			//MeltonV5XMain.scroll0.getVerticalScrollBar().setValue(MeltonV5XMain.scroll0.getVerticalScrollBar().getMaximum());
		}
	}
	
	public void onFavorite(User source, User target, Status favoritedStatus) {
		try {
			if ( target.getScreenName() == MeltonV7XMain.twitter.getScreenName()) {
				InfomationNotifier notifier2 = new InfomationNotifier(favoritedStatus,2);
				notifier2.start();
			}
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090836");
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090841");
			e.printStackTrace();
		}
	}
}
