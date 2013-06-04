package melton;

import java.awt.*;
import javax.swing.*;
import melton.view.*;

@SuppressWarnings("serial")
public class View extends JFrame {
	private MeltonMenu menu;	// メニューバー
	private MeltonUser user;	// ユーザーアイコン、テキストフィールド、ツイートボタン
	private MeltonTL tl;		// タイムライン
	private MeltonButton button;// ボタン類
	private MeltonInfo info;	// インフォメーション
	
	// getterつくる
	public MeltonMenu getmenu() { return menu; }
	public MeltonUser getuser() { return user; }
	public MeltonTL gettl() { return tl; }
	public MeltonButton getbutton() { return button; }
	public MeltonInfo getinfo() { return info; }
	
	public View() {
		setLayout(new BorderLayout());
		
		menu = new MeltonMenu();	setJMenuBar(menu);
		user = new MeltonUser();	add("North", user);
		tl = new MeltonTL();		add("Center", tl);
		button = new MeltonButton();add("East", button);
		info = new MeltonInfo();	add("South", info);
		
		// ウィンドウ消去の時の処理
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// アプリケーション名
		setTitle("Melton  - Main Window -");
		
		// 表示
		setSize(1100,750);
		setVisible(true);
	}
}

@SuppressWarnings("serial")
// メニューバー
class MeltonMenu extends JMenuBar{
	JMenuItem addAccount, changeAccount, AllwaysNew, Invisible;
	
	public JMenuItem getaddAccount() { return addAccount; }
	public JMenuItem getchangeAccount() { return changeAccount; }
	public JMenuItem getAllwaysNew() { return AllwaysNew; }
	public JMenuItem getInvisible() { return Invisible; }
	
	public MeltonMenu() {
		JMenu jm = new JMenu("メニュー");
		jm.setMnemonic('M');
		addAccount = new JMenuItem("アカウント追加");
		addAccount.setMnemonic('A');
		changeAccount = new JMenuItem("アカウント切替");
		changeAccount.setMnemonic('C');
		AllwaysNew = new JMenuItem("常に最新ツイート");
		AllwaysNew.setMnemonic('N');
		Invisible = new JMenuItem("インビジ");
		Invisible.setMnemonic('I');
		jm.add(addAccount);
		jm.add(changeAccount);
		jm.add(AllwaysNew);
		jm.add(Invisible);
		add(jm);
	}
}

@SuppressWarnings("serial")
// 上段：アイコン、テキストフィールド、ツイートボタン
class MeltonUser extends JPanel{
	private JLabel iconlabel;	// アイコン用ラベル
	private JTextArea textarea;	// テキスト
	private JButton tweetbutton;
	
	public JLabel geticonlabel() { return iconlabel; }
	public JTextArea gettextarea() { return textarea; }
	public JButton gettweetbutton() { return tweetbutton; }
	
	MeltonUser() {
		setLayout(new BorderLayout());
		iconlabel = new JLabel();
		// アイコン取得
		add("West", iconlabel);
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		add("Center", textarea);
		tweetbutton = new JButton("つぶやく");
		// リスナーセット？
		add("South", tweetbutton);
	}
}

@SuppressWarnings("serial")
// 中段中央：タイムライン
class MeltonTL extends JTabbedPane {
	private JScrollPane MainScroll, MentionScroll, TweetScroll;		// 各スクロールペイン
	@SuppressWarnings("rawtypes")
	private JList MainList, MentionList, TweetList;					// 各リスト
	@SuppressWarnings("rawtypes")
	private DefaultListModel MainModel, MentionModel, TweetModel;	// 各リストモデル
	
	public JScrollPane getMainScroll() { return MainScroll; }
	public JScrollPane getMentionScroll() { return MentionScroll; }
	public JScrollPane getTweetScroll() { return TweetScroll; }
	@SuppressWarnings("rawtypes")
	public JList getMainList() { return MainList; }
	@SuppressWarnings("rawtypes")
	public JList getMentionList() { return MentionList; }
	@SuppressWarnings("rawtypes")
	public JList getTweetList() { return TweetList; }
	@SuppressWarnings("rawtypes")
	public DefaultListModel getMainModel() { return MainModel; }
	@SuppressWarnings("rawtypes")
	public DefaultListModel getMentionModel() { return MentionModel; }
	@SuppressWarnings("rawtypes")
	public DefaultListModel getTweetModel() { return TweetModel; }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	MeltonTL() {
		MainModel = new DefaultListModel();
		MentionModel = new DefaultListModel();
		TweetModel = new DefaultListModel();
		MainList = new JList(MainModel);
		MainList.setLayoutOrientation(JList.VERTICAL);
		MainList.setCellRenderer(new TextAreaRenderer());
		MentionList = new JList(MentionModel);
		MentionList.setLayoutOrientation(JList.VERTICAL);
		MentionList.setCellRenderer(new TextAreaRenderer());
		TweetList = new JList(TweetModel);
		TweetList.setLayoutOrientation(JList.VERTICAL);
		TweetList.setCellRenderer(new TextAreaRenderer());
		MainScroll = new JScrollPane(MainList);
		MainScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		MentionScroll = new JScrollPane(MentionList);
		MentionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		TweetScroll = new JScrollPane(TweetList);
		TweetScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(MainScroll, "タイムライン");
		add(MentionScroll, "メンション");
		add(TweetScroll, "ツイート");
		//addChangeListener(new TabChangeAdapter());
	}
}

@SuppressWarnings("serial")
// 中段右：ボタン類
class MeltonButton extends JPanel {
	private JButton ReplyButton, RetweetButton, UfretweetButton, FavoriteButton, 
					OpenButton, TalkButton, ClearButton, ProfileButton, LinkButton, 
					FileputButton, CopyButton, FavbombButton;
	
	public JButton getReplyButton() { return ReplyButton; }
	public JButton getRetweetButton() { return RetweetButton; }
	public JButton getUfretweetButton() { return UfretweetButton; }
	public JButton getFavoriteButton() { return FavoriteButton; }
	public JButton getOpenButton() { return OpenButton; }
	public JButton getTalkButton() { return TalkButton; }
	public JButton getClearButton() { return ClearButton; }
	public JButton getProfileButton() { return ProfileButton; }
	public JButton getLinkButton() { return LinkButton; }
	public JButton getFileputButton() { return FileputButton; }
	public JButton getCopyButton() { return CopyButton; }
	public JButton getFavbombButton() { return FavbombButton; }
	
	MeltonButton() {
		ReplyButton = new JButton("リプライ");
		RetweetButton = new JButton("リツイート");
		UfretweetButton = new JButton("非公式RT");
		FavoriteButton = new JButton("ふぁぼ");
		OpenButton = new JButton("ひらく");
		TalkButton = new JButton("会話");
		ClearButton = new JButton("クリア");
		ProfileButton = new JButton("ユーザー");
		LinkButton = new JButton("リンク");
		FileputButton = new JButton("画像添付");
		CopyButton = new JButton("パクる");
		FavbombButton = new JButton("ふぁぼ爆");
		// リスナー登録はコントローラーで！！
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(ReplyButton);
		add(RetweetButton);
		add(UfretweetButton);
		add(FavoriteButton);
		add(OpenButton);
		add(TalkButton);
		add(ClearButton);
		add(ProfileButton);
		add(LinkButton);
		add(FileputButton);
		add(CopyButton);
		add(FavbombButton);
	}
}

@SuppressWarnings("serial")
// 下段：インフォメーション
class MeltonInfo extends JPanel {
	private JLabel APIInfo, FileputInfo, InvisiInfo;
	
	public JLabel getAPIInfo() { return APIInfo; }
	public JLabel getFileputInfo() { return FileputInfo; }
	public JLabel getInvisiInfo() { return InvisiInfo; }
	
	MeltonInfo() {
		APIInfo = new JLabel("API残数：");
		FileputInfo = new JLabel("画像添付：off");
		InvisiInfo = new JLabel("インビジ：off");
		add(APIInfo);
		add(FileputInfo);
		add(InvisiInfo);
	}
}