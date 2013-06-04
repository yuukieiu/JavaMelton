//アカウントを追加するために使う

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class AddAccount extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel labelpin;
	JTextField textpin;
	JButton okbutton;
	JButton cancelbutton;
	JDialog dialog;
	JLabel info;
	static String s;
	static TwitterFactory factory = new TwitterFactory();
	static Twitter twitter = factory.getInstance();
	static TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	
	public AddAccount ( String text ) {//コンストラクタ
		setIconImage(MeltonV7XMain.icon);
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		info = new JLabel("以下のURLをブラウザで開き、アカウント認証したあと表示されるPINコードを入力してください。");
		JTextArea adress = new JTextArea(text);
		adress.setLineWrap(true);//端で折り返す
		labelpin = new JLabel("PINコード:");
		textpin =new JTextField(15);
		panel0.add(info);
		panel1.add(adress);
		panel2.add(labelpin);
		panel3.add(textpin);
		okbutton = new JButton("OK");
		cancelbutton = new JButton("Cancel");
		okbutton.addActionListener(this);
		cancelbutton.addActionListener(this);
		panel3.add(okbutton);
		panel3.add(cancelbutton);
		dialog = new JDialog();
		dialog.setResizable(false);
		dialog.getContentPane().add(panel0);
		dialog.getContentPane().add(panel1);
		dialog.getContentPane().add(panel2);
		dialog.getContentPane().add(panel3);
		dialog.setTitle("Melton  - アカウント追加 -");
		dialog.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		dialog.setSize(600,200);
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public String getPinCode() {
		return textpin.getText();
	}
	
	public static void MyLoginDialog() {//アカウント認証のためのダイアログ
		twitter.setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
		twitterStream.setOAuthConsumer("eWgrWCzrtxRvyvP7MJUA", "6fovcZ4qMgv6LkUmReT82o5thWda8bsDRjD99QTaslo");
		RequestToken requestToken = null;
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("000087");
			e.printStackTrace();
		}
		AccessToken accessToken = null;
		while ( accessToken == null ) {
			MyLogin login = new MyLogin(requestToken.getAuthorizationURL());
			String pin = login.getPinCode();
			try {
				if ( pin.length() > 0 ) {
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
					twitterStream.setOAuthAccessToken(accessToken);
				} else {
					accessToken = twitter.getOAuthAccessToken();
					twitterStream.setOAuthAccessToken(accessToken);
				}
			} catch ( TwitterException te ) {
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("000104");
				if ( te.getStatusCode() == 401 ) {
					//エラー処理するの…？
				} else {
					te.printStackTrace();
				}
			}
			
			try {
				storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
			} catch (IllegalStateException e) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("000117");
				e.printStackTrace();
			} catch (TwitterException e) {
				// TODO 自動生成された catch ブロック
				@SuppressWarnings("unused")
				ErrorWindow error = new ErrorWindow("000122");
				e.printStackTrace();
			}
		}
		MeltonV7XMain.twitter = twitter;
		MeltonV7XMain.model0.clear();
		MeltonV7XMain.model1.clear();
		MeltonV7XMain.model2.clear();
		MeltonV7XMain.twitterAPITest();
		MeltonV7XMain.twitterStream.shutdown();
		MeltonV7XMain.twitterStream = twitterStream;
		MeltonV7XMain.twitterStream.addListener(new MyStatusAdapter());
		MeltonV7XMain.twitterStream.user();
		try {
			MeltonV7XMain.url = new URL(twitter.verifyCredentials().getProfileImageURL());
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090219");
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("090224");
			e.printStackTrace();
		}
		MeltonV7XMain.info.setIcon(new ImageIcon(MeltonV7XMain.url));
		return;
	}
	
	private static void storeAccessToken(long l, AccessToken accessToken) {
		//アカウント情報をファイルに保存する
		File f = null;
		try {
			f = createAccessTokenFileName();
		} catch (IllegalStateException e1) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("000146");
			e1.printStackTrace();
		}
		File d = f.getParentFile();
		if ( !d.exists() ) { d.mkdirs(); }
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(accessToken);
		} catch ( IOException e ) {
			e.printStackTrace();
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("000158");
		} finally {
			if ( os != null ) {
				try {
					os.close();
				} catch ( IOException e ) {
					e.printStackTrace();
					@SuppressWarnings("unused")
					ErrorWindow error = new ErrorWindow("000166");
				}
			}
		}
	}
	
	static File createAccessTokenFileName() {
		//アカウント情報を保存するファイルの名前を生成する
		s = System.getProperty("user.home") + "/Melton/";
		try {
			s = s + twitter.getScreenName();
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("000180");
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			@SuppressWarnings("unused")
			ErrorWindow error = new ErrorWindow("000185");
			e.printStackTrace();
		}
		s = s + "/property/accessToken.dat";
		return new File(s);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == okbutton ) {
			dialog.dispose();
		} else if ( e.getSource() == cancelbutton ) {
			dialog.dispose();
		}
	}
}